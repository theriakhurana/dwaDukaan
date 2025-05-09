import java.util.Scanner;

public class AdminFunctionality {
    private static Scanner sc = new Scanner(System.in);
    private static MedicineManager medicineManager = MedicineManager.getInstance();
    private static OrderManager orderManager = OrderManager.getInstance();

    public static void function(){

        boolean exit = false;
        while(!exit){
            printMenu();
            int input = InputValidator.getIntInput(sc, "Enter your choice: ");

            switch(input){
                case 1 :
                    addNewMedicine();
                    break;
                case 2 :
                    medicineManager.displayAdminView();
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
                case 6:
                    orderManager.viewPendingOrders();
                    break;
                case 7:
                    orderManager.processNextOrder();
                    break;
                case 8:
                    orderManager.viewProcessedOrders();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

// ------------------------------------------------------------------------
    private static void printMenu(){
        System.out.println("-----------------------------");
        System.out.println("1. Add New Medicine");
        System.out.println("2. List All Medicines");
        System.out.println("3. Update Medicine");
        System.out.println("4. Delete Medicine");
        System.out.println("5. Search Medicine by ID");
        System.out.println("6. View Pending Orders");
        System.out.println("7. Process Next Order");
        System.out.println("8. View Processed Orders");
        System.out.println("0. Exit");
        System.out.println("-----------------------------");
        System.out.println();
    }

// ------------------------------------------------------------------------------
    private static void addNewMedicine(){
        System.out.println("Add new Medicine");
        int id = InputValidator.getIntInput(sc, "Enter medicine ID: ");
        if(medicineManager.getMedicineById(id) != null){
            System.out.println("ID already exists");
            return;
        }

        String name = InputValidator.getStringInput(sc, "Enter medicine name: ");
        int stock = InputValidator.getIntInput(sc, "Enter medicine stock: ");
        String expiryDate = InputValidator.getStringInput(sc, "Enter medicine expiry date (YYYY-MM-DD): ");
        double price = InputValidator.getDoubleInput(sc, "Enter medicine price: ");

        Medicine med = new Medicine(id, name, stock, expiryDate, price);

        medicineManager.addNewMedicine(med);
        System.out.println("Medicine added successfully!");
    }

//-----------------------------------------------------------------------------
    private static void updateMedicine(){
        System.out.println("Update Medicine");
        int id = InputValidator.getIntInput(sc, "Enter medicine ID: ");
        Medicine med = medicineManager.getMedicineById(id);

        if(med == null){
            System.out.println("Medicine not found..");
            return;
        }

        String name = InputValidator.getStringInput(sc, "Enter medicine name: ");
        int stock = InputValidator.getIntInput(sc, "Enter medicine stock: ");
        String expiryDate = InputValidator.getStringInput(sc, "Enter medicine expiry date (YYYY-MM-DD): ");
        double price = InputValidator.getDoubleInput(sc, "Enter medicine price: ");

        medicineManager.updateMedicine(id, name, stock, expiryDate, price);
        System.out.println("Medicine updated successfully!");
    }

// ------------------------------------------------------------------------------
    private static void deleteMedicine(){
        System.out.println("Delete Medicine");
        int id = InputValidator.getIntInput(sc, "Enter medicine ID: ");
        if(medicineManager.deleteMedicine(id)){
            System.out.println("Medicine deleted successfully!");
        } else {
            System.out.println("Medicine not found..");
        }
    }

// ------------------------------------------------------------------------------
    private static void getMedicineById(){
        System.out.println("Get Medicine by ID");
        int id = InputValidator.getIntInput(sc, "Enter medicine ID: ");

        Medicine med = medicineManager.getMedicineById(id);
        if(med != null){
            System.out.println(med.toString());
        } else {
            System.out.println("Medicine not found..");
        }
    }
// ------------------------------------------------------------------------------
};
