import java.util.Scanner;

public class Main {
  private static Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    System.out.println("--- Welcome To Dwa Dukaan ---");
    System.out.println("-----------------------------");

    boolean exit = false;
    while(!exit){
      int prompt = InputValidator.getIntInput(sc, "Enter 0 for Admin || Enter 1 for User || Enter -1 to exit");

      switch(prompt) {
        case 0:
          // owner functions
          AdminFunctionality.function();
          break;
        case 1:
          // client functions
          System.out.println("User Functionality");
          UserFunctionality.function();
          break;
        case -1:
          exit = true;
          System.out.println("Thanks for using Dwa Dukaan!");
          break;
        default:
          System.out.println("Please enter valid login ID");
        }
    }
  }
};