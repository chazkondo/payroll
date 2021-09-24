/**

   * Payroll --- software application
   * A money calculator for employee hours worked
   * Includes USD payments and overtime costs
   * @author Chuckee Kondo (Chaz)
   */

import java.util.Scanner; // used for reading user input
import java.lang.Math; // used for generating random number
import java.text.DecimalFormat; // used for formatting price

public class Payroll {

   private static Scanner scnr; // scanner to view user input
   private static boolean overtimeDetected = false; // static boolean used to track overtimeDetected state
  
   /** Driver Class -
      * Initializes the Payroll program
      * @params String[] args
      * includes the command line arguments
      * @exception throws Exception
      * @return No return value
      */
      
   public static void main(String[] args) throws Exception {
      int employeeHours[][]; // delcare 2d array to track employee hours
      int employeeHourTotals[]; // declare array to track employee hour totals
      double employeeWages[]; // declare array to track employee wages
   
      // display welcome message for the user and explain what the application does
      System.out.println("Welcome to the payroll calculator.");
      System.out.println("[ An application for generating the payroll for your employees ]\n");
      
      // ask user for number of employees
      System.out.print("How many employees does your company have? (Must be from 5 to 20 inclusive) \n");
      
      // validate the number of employees
      int employeeNum = validateEmployeeNumber();
      
      // if employeeNum equals negative 1, there is an issue with the user input -> end the program
      if (employeeNum != -1) {
         employeeHours = new int[employeeNum][5]; // set employeeHours 2d arr to user input length and 5 (mon - fri)
         employeeHourTotals = new int[employeeNum]; // set employeeHourTotal arr to user input length
         employeeWages = new double[employeeNum]; // set employeeWages arr to user input length
      
         // call calculatePayroll and pass variables to be mutated
         calculatePayroll(employeeHours, employeeHourTotals, employeeWages);
      }
      
      // end the program with a unnoticeable style
      endProgram(employeeNum);
   }
   
   
   
                  /*   Validation Methods   */
                  
                  
                  
   /**
      * validateEmployeeNumber ~
      * validates user inputted number of employees
      * @param No parameters
      * @exception No exceptions
      * @return int representing validated number of employees or -1 as invalid
      */
   
   private static int validateEmployeeNumber () {
      
      // declare new employeeNum to be tested
      int employeeNum;
      
       // prepare the scnr for user input
      scnr = new Scanner(System.in);
      
      // try to set barcode to next integer from user
      try { 
         employeeNum = scnr.nextInt();
      }
      // catch any type mismatch
      catch (Exception mismatch) {
         
         // let the user know the error and return -1, representing an error has occurred with validation
         System.out.println("Sorry, the program is expecting an integer between 5 - 20 inclusive.");
         return -1;
      }
      
      // throw error IF out of range
      if (employeeNum > 20 || employeeNum < 5) {
      
         // let the user know the error and return -1, representing an error has occurred with validation
         System.out.println("Sorry, the number is out of range.");
         return -1;
      }
      
      // if no error thrown, return validated employeeNum
      return employeeNum;
   }
   
   
   
                  /*   Main Methods   */
                  
                  
   
   /**
      * calculatePayroll ~
      * Runs the main Payroll logic
      * Prints the employee hour grid, displays total hours, display overtime, and calculates wage
      * @param int employeeHours[][] - 2d arr representing employee hours
      * @param int employeeHourTotals[] - array containing total hours worked per employee
      * @param double employeeWages[] - array containing total amount earned USD per employee
      * @return No return value
      */
      
   private static void calculatePayroll(int employeeHours[][], int employeeHourTotals[], double employeeWages[]) {
      
      // print the employee hour grid for mon-fri and track information so a second nested for loop is not required
      printEmployeeHourGrid(employeeHours, employeeHourTotals, employeeWages);
      
      // display total hours by reading from a mutated employeeHourTotals[] arr
      displayTotalHours(employeeHourTotals);
      
      // display overtime by reading from a mutated employeeHourTotals[] arr and check if overtimeDetected boolean has been altered 
      displayOvertime(overtimeDetected, employeeHourTotals);
      
      // display all employee wages by reading from a mutated employeeWages[] arr
      displayWage(employeeWages);
   }
   
   /**
      * printEmployeeHourGrid ~
      * Prints the hours per employee for mon-fri in grid form and mutates arguments and static variables
      * @param int employeeHours[][] - 2d arr representing employee hours
      * @param int employeeHourTotals[] - array containing total hours worked per employee
      * @param double employeeWages[] - array containing total amount earned USD per employee
      * @return No return value
      */
   
   private static void printEmployeeHourGrid(int employeeHours[][], int employeeHourTotals[], double employeeWages[]) {
      // tell the user what is being printed
      System.out.println("\nHours worked each day of the week per employee:\n");
      
      // print out day headers
      System.out.println("             Mon   Tue   Wed   Thu   Fri");
   
      // loop through each employee
      for (int i = 0; i < employeeHours.length; i++) {
      
         // set each employees total worked hours to 0 so we can add hours for each day
         int reducedHours = 0;
         
         // print out employee number using (i+1) to account for index 0 and
         // implement a ternary expression to keep consistent formatting regardless of if number of employees is double-digits
         System.out.print(i > 8 ? ("Employee " + (i+1) + "  ") : ("Employee  " + (i+1) + "  "));
         
         // implement a nested for loop to iterate through each day (mon-fri) corresponding to each employee (i)
         for (int j = 0; j < 5; j++) {
         
            // let each inner index be set to a random number between 0 and 12 inclusive, representing the amount of hours worked
            employeeHours[i][j] = generateHours(0, 12);
            
            // print the hours corresponding to each employee (i), for each day (j) using printf %3d for better alignment
            System.out.printf("%3d   ", employeeHours[i][j]);
            
            // let reducedHours equal the culminated hours for each day of the week per employee (i)
            reducedHours += employeeHours[i][j];
         }
         
         /* still in outer for loop */
         
         // set employeeHourTotals[i] equal to the total hours worked per employee (i) which was stored in reducedHours
         employeeHourTotals[i] = reducedHours;
         
         // set employeeWages[i] equal to the amount owed for each employee (i) based on their total hours stored in reducedHours
         employeeWages[i] = calculateWage(reducedHours);
         
         // check if overtimeDetected was toggled from false to true during loop progression
         if (!overtimeDetected) {
            
            // if overtimeDetected is still false, check if reducedHours for employee (i) is greater than 40
            if (reducedHours > 40) {
               
               // if reducedHours for employee (i) is greater than 40, toggle overtimeDetected to true
               overtimeDetected = true;
            }
         }
         
         System.out.println(); // skip to the next line 
      }
   }
   
   /**
      * displayTotalHours ~
      * Displays the total hours worked per employee
      * Reads from a mutated employeeHourTotals array
      * @param int employeeHourTotals[] - array containing total hours worked per employee
      * @return No return value
      */
   
   private static void displayTotalHours(int[] employeeHourTotals) {
   
      // tell the user what is being printed
      System.out.println("\n\nHours worked by each employee");
      
      // iterate through employeeHourTotals array to read the total number of hours worked for each employee (i)
      for (int i = 0; i < employeeHourTotals.length; i++) {
      
         // implement two ternary expressions to keep formatting consistent when displaying hours to the user
         System.out.print(i > 8 ? "Employee " + (i+1) + " worked " : "Employee  " + (i+1) + " worked ");
         System.out.print(employeeHourTotals[i] > 9 ? employeeHourTotals[i] + " hours.\n" : " " + employeeHourTotals[i] + " hours.\n");
      }
   }
   
   /**
      * displayOvertime ~
      * Displays the hours worked overtime per employee (if the employee has worked overtime)
      * Reads from a mutated totalHours[] array
      * @param boolean overtimeDetected - boolean referencing if any employee has worked overtime
      * @param int totalHours[] - array containing total hours worked per employee
      * @return No return value
      */
   
   private static void displayOvertime(boolean overtimeDetected, int totalHours[]) {
   
      // check if overtimeDetected is true
      if (overtimeDetected) {
      
         // display a header for the user for overtime worked
         System.out.println("\n\nEmployees that worked overtime");
     
         // iterate through totalHours array and calculate if an employee (i) has worked overtime and by how much (hours over 40)
         for (int i = 0; i < totalHours.length; i++) {
            if (totalHours[i] > 40) {
               int difference = totalHours[i] - 40; // 40 hours is the overtime threshold
               
               // implement a ternary expression to keep formatting consistent while displaying overtime hours
               System.out.println(i > 8 ? "Employee " + (i+1) + "  overtime = " + difference + " hours." : "Employee  " + (i+1) + "  overtime = " + difference + " hours.");
            }
         }
      }
      
      // if overtimeDetected isn't true, print that no employees worked overtime for the user
      else {
         System.out.println("\n\nNo employees worked overtime.");
      }
      
      System.out.println(); // skip to the next line 
   }
   
   /**
      * displayWage ~
      * Displays the earned wages for each employee in USD
      * Reads from a mutated wages array
      * @param double wages[] - array containing the calculated wage per employee
      * @return No return value
      */
   
   private static void displayWage(double wages[]) {
      
      // declare and intialize minimalFormat to utilize the DecimalFormat for prettier formatting
      DecimalFormat minimalFormat = new DecimalFormat("###0.00");
      
      // tell the user what is being printed
      System.out.println("\nEMPLOYEE PAYROLL");
      
      // iterate through the wages[] arr and and print USD amount employee (i) earned utilizing a ternary for consistent formatting
      for (int i = 0; i < wages.length; i++) {
         System.out.println(i > 8 ? "Employee " + (i+1) + "  $" + minimalFormat.format(wages[i]) : "Employee  " + (i+1) + "  $" + minimalFormat.format(wages[i]));
      }
   }
   
   /**
      * endProgram ~
      * Ends the program in style (or lackthereof) 
      * @param int uxHang - the number representing a pause (in ms) to wait for the program to finish running
      * @exception Throws Exception
      * @return No return value
      */
   
   private static void endProgram(int uxHang) throws Exception {
      
      // implement try/catch block to utilize Thread.sleep for a more interesting program end
      try {
      
         // declare and initialize i to 0 to be used by a while-loop
         int i = 0;
      
         // let the user know the program is ending
         System.out.print("\nProgram is ending");
         
         // if statement to make sure no error occurred during user validation
         if (uxHang != -1) {
            // implement Thread.sleep to hang the program according to the employee number considering the program ran normally
            Thread.sleep(uxHang*100); // added for better user experience
         }
         
         // implement a while loop to add a bare-minimum CLI animation using "."
         while (i <= 2) {
            Thread.sleep(250); // added for better user experience
            System.out.print(".");
            i++; // increment "i" to break out of the loop
         }
      }
      
      // catch any issues that occur with Thread.sleep
      catch(Exception e) {
         
         // let the user know an error occurred during the application-end process
         System.out.println("Oops an error occurred while shutting down... \n");
      }
      
      // print the final message to the user for program termination
      System.out.println("\n\n\nProgram completed.");
   }
   
   
   
                  /*   Helper Methods   */



   /**
      * calculateWage ~
      * Calculates the wage based on the standard rate and takes overtime into consideration
      * Reads from a mutated totalHours[] array
      * @param int totalHours[] - array containing total hours worked per employee
      * @return double representing the wage for an employee
      */
      
   private static double calculateWage(int totalHours) {

      // check if total hours exceeds 40
      if (totalHours > 40) {
         // calculate overtime rate for hours exceeding 40
         int overtime = totalHours - 40;
         return (40 * 14.50) + (overtime * 21.75);
      }
      else {
         // return the standard hourly rate
         return totalHours * 14.50;
      }
   }

   /**
      * generateHours ~
      * Randomly generates hours between the parameter range
      * @param int min - an int that represents the minimum cap
      * @param int max - an int that represents the maximum cap
      * @return int representing the generated hour(s) for a user
      */
   
   private static int generateHours(int min, int max) {
   
      // declare and initialize range to the top range minus the min plus 1
		int range = (max - min) + 1;
      
      // return a random int utilizing Math.random() within the provided range, inclusive
		return (int)(Math.random() * range) + min;
	}
}
                                                                          