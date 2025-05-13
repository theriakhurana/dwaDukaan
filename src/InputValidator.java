import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
// to prevent any invalid input from user 

public class InputValidator{

// -------------------------------------------------------------------------
// only int is given as input
  public static int getIntInput(Scanner sc, String msg){
    int input = -1;
    while(true){
      System.out.println(msg);
      try{
        input = Integer.parseInt(sc.nextLine());
        if(input >= -1) {
          break; // valid positive number
        } else {
          System.out.println("Please enter a positive number.");
        }
      }
      catch(NumberFormatException e){
        System.out.println("Invalid input. Please enter a number.");
      }
    }
    return input;
  }

// ----------------------------------------------------------------------------
  public static double getDoubleInput(Scanner sc, String msg){
    double input = -1;
    while(true){
      System.out.println(msg);
      try{
        input = Double.parseDouble(sc.nextLine());
        if(input > 0) {
          break; // valid positive number
        } else {
          System.out.println("Please enter a positive number.");
        }
        break; // exit loop
      }
      catch(NumberFormatException e){
        System.out.println("Invalid input. Please enter a number.");
      }
    }
    return input;
  }

// ------------------------------------------------------------------------------
  public static String getStringInput(Scanner sc, String msg){
    String input;
    while (true) {
      System.out.println(msg);
      input = sc.nextLine().trim();
      if (!input.isEmpty()) {
        return input;
      }
      System.out.println("Input cannot be empty. Please try again."); // empty inputs not allowed
    }
  }

// ----------------------------------------------------------------------------------
// validates correct input for expiry date
// proper format and expiry date to be after the present date

  public static String getValidExpiryDate(Scanner sc, String msg) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    while (true) {
      System.out.println(msg);
      String input = sc.nextLine().trim();
      try {
        LocalDate date = LocalDate.parse(input, formatter);
        if (date.isAfter(LocalDate.now())) { // avoid expiry date before the current date
          return input;
        } else {
          System.out.println("Expiry date must be in the future.");
        }
      } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Please use yyyy-MM-dd.");
      }
    }
  }

// ------------------------------------------------------------------------------------------
};
