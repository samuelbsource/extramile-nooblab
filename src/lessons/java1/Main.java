import java.util.Scanner;	  	    	     		 			
	  	    	     		 			
public class Main	  	    	     		 			
{	  	    	     		 			
    public static Scanner scanner;	  	    	     		 			
	  	    	     		 			
    public static void main(String[] args)	  	    	     		 			
    {	  	    	     		 			
        scanner = new Scanner(System.in);	  	    	     		 			
	  	    	     		 			
        while (true) {	  	    	     		 			
            printMenu();	  	    	     		 			
            String option = takeOption();	  	    	     		 			
            if (option.equalsIgnoreCase("1"))	  	    	     		 			
            {	  	    	     		 			
                inputStudent();	  	    	     		 			
            }	  	    	     		 			
            else if (option.equalsIgnoreCase("2"))	  	    	     		 			
            {	  	    	     		 			
                printStudents();	  	    	     		 			
            }	  	    	     		 			
            else if (option.equalsIgnoreCase("3"))	  	    	     		 			
            {	  	    	     		 			
                advanceYears();	  	    	     		 			
            }	  	    	     		 			
            else if (option.equalsIgnoreCase("4"))	  	    	     		 			
            {	  	    	     		 			
                break; // This "breaks" the loop, forcing it to stop	  	    	     		 			
            }	  	    	     		 			
        }	  	    	     		 			
    }	  	    	     		 			
	  	    	     		 			
    public static void printMenu()	  	    	     		 			
    {	  	    	     		 			
        System.out.println("== MENU ==");	  	    	     		 			
        System.out.println("1) Add student");	  	    	     		 			
        System.out.println("2) List students");	  	    	     		 			
        System.out.println("3) Advance year");	  	    	     		 			
        System.out.println("4) Exit");	  	    	     		 			
    }	  	    	     		 			
	  	    	     		 			
    /**	  	    	     		 			
     * Separate method in case we want to validate the input in the future	  	    	     		 			
     */	  	    	     		 			
    public static String takeOption()	  	    	     		 			
    {	  	    	     		 			
        return scanner.nextLine();	  	    	     		 			
    }	  	    	     		 			
	  	    	     		 			
    public static void inputStudent()	  	    	     		 			
    {	  	    	     		 			
        System.out.println("Enter name");	  	    	     		 			
        String name = scanner.nextLine();	  	    	     		 			
	  	    	     		 			
        System.out.println("Enter age");	  	    	     		 			
        String age = scanner.nextLine();	  	    	     		 			
	  	    	     		 			
        System.out.println("Enter course");	  	    	     		 			
        String course = scanner.nextLine();	  	    	     		 			
	  	    	     		 			
        System.out.println("Enter year");	  	    	     		 			
        String year = scanner.nextLine();	  	    	     		 			
    }	  	    	     		 			
	  	    	     		 			
    public static void printStudents()	  	    	     		 			
    {	  	    	     		 			
        System.out.println("Work in progress");	  	    	     		 			
    }	  	    	     		 			
	  	    	     		 			
    public static void advanceYears()	  	    	     		 			
    {}	  	    	     		 			
}