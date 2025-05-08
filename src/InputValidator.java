import java.util.Scanner;
// to prevent any invalid input from user 

public class InputValidator{

  public static int getIntInput(Scanner sc, String msg){
    int input = -1;
    while(true){
      System.out.println(msg);
      try{
        input = Integer.parseInt(sc.nextLine());
        break; // exit loop
      }
      catch(NumberFormatException e){
        System.out.println("Invalid input. Please enter a number.");
      }
    }
    return input;
  }

  public static double getDoubleInput(Scanner sc, String msg){
    double input = -1;
    while(true){
      System.out.println(msg);
      try{
        input = Double.parseDouble(sc.nextLine());
        break; // exit loop
      }
      catch(NumberFormatException e){
        System.out.println("Invalid input. Please enter a number.");
      }
    }
    return input;
  }

  public static String getStringInput(Scanner sc, String msg){
    String input;
    while (true) {
      System.out.println(msg);
      input = sc.nextLine().trim();
      if (!input.isEmpty()) {
        return input;
      }
      System.out.println("Input cannot be empty. Please try again.");
    }
  }
};
