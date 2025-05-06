import java.util.Scanner;

public class Main{

  private static MedicineManager medicineManager = new MedicineManager();
  private static Scanner sc = new Scanner(System.in);

  public static void main(String[] args){
    boolean exit = false;

    while(!exit){
      printMenu();
      int input = InputValidator.getIntInput(sc, "Enter your choice: ");

      switch(input){
        case 1 :
          addNewMedicine();
          break;
        case 2 :
          medicineManager.displayAllMedicines();
          break;
        case 3:
          updateMedicine();
          break;
        case 4:
          deleteMedicine();
          break;
        case 5: 
          getMedicineById();
          break;
        case 0:
          exit = true;
          System.out.println("Exiting the program. Thank you for using Dwa Dukaan!");
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
          break;
      }
    }
  }

  private static void printMenu(){
    System.out.println("--- Welcome To Dwa Dukaan ---");
    System.out.println("-----------------------------");
    System.out.println("1. Add New Medicine");
    System.out.println("2. List All Medicines");
    System.out.println("3. Update Medicine");
    System.out.println("4. Delete Medicine");
    System.out.println("5. Search Medicine by ID");
    System.out.println("0. Exit");
    System.out.println("-----------------------------");
    System.out.println();
  }

  private static void addNewMedicine(){
    System.out.println("Add new Medicine");
  }

  private static void updateMedicine(){
    System.out.println("Update Medicine");
  }

  private static void deleteMedicine(){
    System.out.println("Delete Medicine");
  }

  private static void getMedicineById(){
    System.out.println("Get Medicine by ID");
  }
}