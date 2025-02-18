import java.util.Scanner;

public class Main
{
  public static void main(String[] args)
  {
    Scanner scanner = new Scanner(System.in);
    boolean isRunning = true;

    /* Student data */
    int numberOfSlots = 5;
    String[] names = new String[numberOfSlots];
    String[] universities = new String[numberOfSlots];
    String[] courses = new String[numberOfSlots];
    int[] years = new int[numberOfSlots];
    boolean[] areHomeStudents = new boolean[numberOfSlots];

    /* Start the main program loop */
    while (isRunning)
    {
      System.out.println("==== Edu Track - main menu ====");
      System.out.println("1. Add student");
      System.out.println("2. List students");
      System.out.println("3. Edit student");
      System.out.println("4. Remove student");
      System.out.println("5. Exit");

      String option = askInput(scanner, "Input: ");
      if (option.equals("1"))
      {
        System.out.println("==== Edu Track - add student ====");

        String name = askInput(scanner, "Name: ");
        String university = askInput(scanner, "University: ");
        String course = askInput(scanner, "Course: ");
        int year = Integer.parseInt(askInput(scanner, "Year: "));

        // capital Y means it's the default option, see https://ux.stackexchange.com/questions/40444/meaning-of-capitalization-in-y-n-prompts
        String isHomeStudent = askInput(scanner, "Is home student [Y/n]? ");
        if (isHomeStudent.isEmpty())
        {
          isHomeStudent = "y";
        }

        // Find next empty slot
        int emptySlot = findEmptySlot(names);
        if (emptySlot == -1)
        {
          System.out.println("Could not find an empty slot for the new user, please expel a student to make space for a new one.");
        }
        else
        {
          names[emptySlot]           = name;
          universities[emptySlot]    = university;
          courses[emptySlot]         = course;
          years[emptySlot]           = year;

          // This next, is called "ternary operator", it's a very short version of: if (something) {} else {}
          // See: https://www.w3schools.com/java/java_conditions_shorthand.asp
          areHomeStudents[emptySlot] = isHomeStudent.equalsIgnoreCase("y") ? true : false;

          System.out.println("Student " + name + " was added to the system");
        }
      }
      else if (option.equals("2"))
      {
        System.out.println("==== Edu Track - list students ====");
        for (int i = 0; i < numberOfSlots; i++)
        {
          if (names[i] != null)
          {
            System.out.println(
              i + ") " + names[i]
              + " (University: " + universities[i]
              + ", Course: " + courses[i]
              + ", year: " + years[i]
              + ", home? " + (areHomeStudents[i] ? "yes" : "no") + ")"
            );
          }
          else 
          {
            System.out.println(i + ") null");
          }
        }
      }
      else if (option.equals("3"))
      {
        // Print list of students
        System.out.println("==== Edu Track - edit student ====");
        for (int i = 0; i < numberOfSlots; i++)
        {
          if (names[i] != null)
          {
            System.out.println(
              i + ") " + names[i]
              + " (University: " + universities[i]
              + ", Course: " + courses[i]
              + ", year: " + years[i]
              + ", home? " + (areHomeStudents[i] ? "yes" : "no") + ")"
            );
          }
          else
          {
            System.out.println(i + ") null");
          }
        }

        // Ask for input
        int studentIndex = Integer.parseInt(askInput(scanner, "Student ID: "));

        // if less than 0, or more than number of slots, or the slot is empty
        if (studentIndex < 0 || studentIndex >= numberOfSlots || names[studentIndex] == null)
        {
          System.out.println("Invalid input, returning to main menu.");
        }
        else {
          String newName = askInputWithUnchanged(scanner, "Name: ");
          String newUniversity = askInputWithUnchanged(scanner, "University: ");
          String newCourse = askInputWithUnchanged(scanner, "Course: ");
          String newYear = askInputWithUnchanged(scanner, "Year: ");
          String newIsHomeStudent = askInputWithUnchanged(scanner, "Is home student [y/n]? ");

          // Check each value if it's not empty, if not empty then update it.
          // Note, we are not using ELSE IF here, we want to check every input individually,
          // even if the previous one was set already we still need to check others.
          if (!newName.isEmpty())
          {
            names[studentIndex] = newName;
          }

          if (!newUniversity.isEmpty())
          {
            universities[studentIndex] = newUniversity;
          }

          if (!newCourse.isEmpty())
          {
            courses[studentIndex] = newCourse;
          }

          if (!newYear.isEmpty())
          {
            years[studentIndex] = Integer.parseInt(newYear);
          }

          if (!newIsHomeStudent.isEmpty())
          {
            areHomeStudents[studentIndex] = newIsHomeStudent.equalsIgnoreCase("y") ? true : false;
          }

          System.out.println("Student " + names[studentIndex] + " was updated");
        }
      }
      else if (option.equals("4"))
      {
        // Print list of students
        System.out.println("==== Edu Track - edit student ====");
        for (int i = 0; i < numberOfSlots; i++)
        {
          if (names[i] != null)
          {
            System.out.println(
              i + ") " + names[i]
              + " (University: " + universities[i]
              + ", Course: " + courses[i]
              + ", year: " + years[i]
              + ", home? " + (areHomeStudents[i] ? "yes" : "no") + ")"
            );
          }
          else
          {
            System.out.println(i + ") null");
          }
        }

        // Ask for input
        int studentIndex = Integer.parseInt(askInput(scanner, "Student ID: "));

        // if less than 0, or more than number of slots, or the slot is empty
        if (studentIndex < 0 || studentIndex >= numberOfSlots || names[studentIndex] == null)
        {
          System.out.println("Invalid input, returning to main menu.");
        }
        else {
          String studentName = names[studentIndex];

          // Set student data to null, which means "nothing is here"
          names[studentIndex] = null;
          universities[studentIndex] = null;
          courses[studentIndex] = null;
          years[studentIndex] = 0; // Integer cannot be null, only object
          areHomeStudents[studentIndex] = false; // Same for boolean

          System.out.println("Student " + studentName + " was deleted from the system.");
        }
      }
      else if (option.equals("5"))
      {
        System.out.println("");
        System.out.println("==== Edu Track ====");
        System.out.println("Thank you for using Edu Track!");
        isRunning = false;
      }

      // Add space between the last output and main menu
      System.out.println("");
    }
  }

  /**
   * Print question to the screen and ask for the input on the same line.
   * 
   * @param scanner scanner is necessary for input
   * @param question question to ask the user
   * @return The value that the user entered
   */
  public static String askInput(Scanner scanner, String question)
  {
    System.out.print(question);
    String inputValue = scanner.nextLine();
    return inputValue.trim();
  }

  /**
   * Works similarly as askInput, but if the input is empty, it will print "unchanged" to the screen.
   * 
   * This code uses "magic" ASCII codes, to tell the terminal to return to change the previous line.
   *  - \033[F   This magic code tells terminal to "go one line up"
   *  - \r       This is "carriage return", means go back to the beginning of the line
   * 
   * @param scanner scanner is necessary for input
   * @param question question to ask the user
   * @return The value that the user entered
   */
  public static String askInputWithUnchanged(Scanner scanner, String question)
  {
    System.out.print(question);
    String inputValue = scanner.nextLine();

    if (inputValue.trim().isEmpty())
    {
      System.out.print("\033[F\r"); // Go back to the previous line
      System.out.println(question + "(unchanged)"); // Print question again + add string: "(unchanged)"
    }

    return inputValue.trim();
  }

  /**
   * Find the first empty slot.
   * 
   * This function will return -1 if empty slot was not found.
   * -1 is a "magic" number because it's less than the first element of the array,
   * so in theory it should never happen, unless there are no empty slots!
   * 
   * @param array
   * @return the index of the first empty slot, or -1 if one was not found
   */
  public static int findEmptySlot(String[] array)
  {
    int emptySlot = -1; // -1 is our magic number to know if we've found an empty slot!
    for (int i = 0; i < array.length; i++)
    {
      if (array[i] == null) {
        emptySlot = i;
        break;
        // break means: stop loop here, no matter if (i < numberOfSlots) is true or false
        // it's useful to stop computation early, since we've already found empty slot, no need to keep searching for another.
      }
    }
    return emptySlot;
  }
}