import fs from 'node:fs/promises';
import { join as joinPath } from 'node:path';
import Handlebars from 'handlebars';
import { parse as parseYAML } from 'yaml';
import { cwd } from 'node:process';

const LESSONS_DIR = joinPath(cwd(), 'src', "lessons");
const DISTRIBUTION_DIR = joinPath(cwd(), 'dist');
const COMMON_PARTIALS_DIR = joinPath(cwd(), 'src', "common");

// Register global helpers
Handlebars.registerHelper('getJsonContext', function(data, options) {
  return options.fn(JSON.parse(data));
});

// Load common partials
console.log(`Registering common partials ...`);
for (const partial of await fs.readdir(COMMON_PARTIALS_DIR, 'utf-8')) {
  console.log(`  > Registering common partial ${partial} ...`);

  const partialData = await fs.readFile(joinPath(COMMON_PARTIALS_DIR, partial), "utf-8");
  const partialTemplate = Handlebars.compile(partialData, { strict: true, assumeObjects: true, preventIndent: true });
  Handlebars.registerPartial(partial.slice(0, -4), partialTemplate);
}
console.log("");

// Read through each lesson inside the /src directory and produce output inside /dist
for (const lessonDir of await fs.readdir(LESSONS_DIR, 'utf-8')) {
  if (lessonDir === "unit3-edu-track") {
    console.log(`Generating lesson ${lessonDir} ...`);

    // Read lesson config file
    const configRaw = await fs.readFile(joinPath(LESSONS_DIR, lessonDir, "lesson.yaml"), "utf-8");
    const configData = parseYAML(configRaw);

    // Find what sections to include in the lesson
    const sections = await fs.readdir(joinPath(LESSONS_DIR, lessonDir, "sections"), 'utf-8').then(x => x.map(y => ({ name: y.slice(0, -4), filename: y })));

    // Read bundles
    const bundles = await fs.readdir(joinPath(LESSONS_DIR, lessonDir, "bundles"), 'utf-8').then(async bundleDirs => {
      const bundles = [];
      for (const bundleDir of bundleDirs) {
        bundles.push({
          name: bundleDir,
          files: await fs.readdir(joinPath(LESSONS_DIR, lessonDir, "bundles", bundleDir), 'utf-8').then(async bundleFiles => {
            const files = [];
            for (const bundleFile of bundleFiles) {
              files.push({
                filename: bundleFile,
                content: await fs.readFile(joinPath(LESSONS_DIR, lessonDir, "bundles", bundleDir, bundleFile), "utf-8")
              })
            }
            return files;
          })
        });
      }
      return bundles;
    });

    // Register local helpers
    Handlebars.registerHelper('insertBundle', function(bundleName, options) {
      // find bundle
      const bundle = bundles.find(x => x.name === bundleName)
      if (!bundle) {
        throw new Error(`Bundle ${bundleName} does not exist`);
      }

      // Assumes partial is precompiled
      return Handlebars.partials["bundle"](bundle);
    });

    // Read and register partials
    for (const section of sections) {
      console.log(`  > Registering section ${section.name} ...`);

      const sectionData = await fs.readFile(joinPath(LESSONS_DIR, lessonDir, "sections", `${section.filename}`), "utf-8");
      Handlebars.registerPartial(section.name, sectionData);
    }

    // Read index.hbs and generate HTML
    console.log(`  * Building lesson ...`);
    const templateRaw = await fs.readFile(joinPath(LESSONS_DIR, lessonDir, "index.hbs"), "utf-8");
    const template = Handlebars.compile(templateRaw, { strict: true, assumeObjects: true, preventIndent: true });
    const html = template({ sections, ...configData });

    // Unregister partials for this lesson
    for (const section of sections) {
      console.log(`  < Unregistering section ${section.name} ...`);
      Handlebars.unregisterPartial(section);
    }

    // Write to /dist
    console.log(`  * Writing to ${joinPath(DISTRIBUTION_DIR, lessonDir, "index.html")} ...`);
    await fs.mkdir(joinPath(DISTRIBUTION_DIR, lessonDir), { recursive: true });
    await fs.writeFile(joinPath(DISTRIBUTION_DIR, lessonDir, "index.html"), html, "utf-8");
  }
}

// const config = yaml.load(fs.readFileSync('config.yaml', 'utf8'));

// const rootPages = ['unit3-edu-track'];

// rootPages.forEach(page => {
//   const mainTemplate = handlebars.compile(fs.readFileSync(`${page}/index.html`, 'utf8'));

//   const sections = {};
//   fs.readdirSync('sections').forEach(file => {
//     const sectionName = file.replace('.html', '');
//     const sectionTemplate = handlebars.compile(fs.readFileSync(`sections/${file}`, 'utf8'));
//     sections[sectionName] = sectionTemplate(config);
//   });

//   const outputHtml = mainTemplate({ sections });
//   const outputPath = path.join('dist', page, 'index.html');

//   fs.ensureDirSync(path.dirname(outputPath));
//   fs.writeFileSync(outputPath, outputHtml);
// });

// // Copy the root index.html if needed
// const rootIndex = handlebars.compile(fs.readFileSync('index.html', 'utf8'));
// const rootOutputHtml = rootIndex({ sections });
// fs.writeFileSync('dist/index.html', rootOutputHtml);
