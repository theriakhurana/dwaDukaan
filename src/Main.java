import java.util.Scanner;

public class Main {
  private static Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    System.out.println("--- Welcome To Dwa Dukaan ---");
    System.out.println("-----------------------------");

    int prompt = InputValidator.getIntInput(sc, "Enter 0 for Admin || Enter 1 for User");
    switch(prompt) {
      case 0:
        AdminFunctionality.function();
        break;
      case 1:
        System.out.println("User Functionality");
        //UserFunctionality.function();
        break;
      default:
        System.out.println("Please enter valid login ID");
    }
  }

};