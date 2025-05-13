import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AdminFunctionality {
    private static final Scanner sc = new Scanner(System.in);
    private static MedicineCRUD medicineCRUD = new MedicineCRUD();
    private static OrderManager orderManager = OrderManager.getInstance();

    public static void function() {

        boolean exit = false;
        while (!exit) {
            printMenu();
            int input = InputValidator.getIntInput(sc, "Enter your choice: ");

            switch (input) {
                case 1:
                    addNewMedicine();
                    break;
                case 2:
                    listAllMedicines();
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
                case 9:
                    checkLowStockMedicines();
                    break;
                case 10:
                    medicineCRUD.viewStockSummary();
                    break;
                case 11:
                    UserFunctionality.viewFeedbacks();
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
    private static void printMenu() {
        System.out.println("-----------------------------");
        System.out.println("1. Add New Medicine");
        System.out.println("2. List All Medicines");
        System.out.println("3. Update Medicine");
        System.out.println("4. Delete Medicine");
        System.out.println("5. Search Medicine by ID");
        System.out.println("6. View Pending Orders");
        System.out.println("7. Process Next Order");
        System.out.println("8. View Processed Orders");
        System.out.println("9. Check low stock Meds");
        System.out.println("10. View Stock Summary");
        System.out.println("11. View Feedbacks");
        System.out.println("0. Exit");
        System.out.println("-----------------------------");
        System.out.println();
    }

    // ------------------------------------------------------------------------------
// Add medicine
    private static void addNewMedicine() {
        try {
            System.out.print("Enter Medicine Name: ");
            String name = sc.nextLine();
            int stock = InputValidator.getIntInput(sc, "Enter Stock: ");
            String expiry = InputValidator.getValidExpiryDate(sc, "Enter Expiry Date (yyyy-MM-dd): ");
            double price = InputValidator.getDoubleInput(sc, "Enter Price: ");

            Medicine med = new Medicine(0, name, stock, expiry, price);
            medicineCRUD.addMedicine(med);
        } catch (Exception e) {
            System.out.println("Error!");
        }
    }

    // List All Medicines ------------------------------------------------------------------
    private static void listAllMedicines() {
        List<Medicine> medicines = medicineCRUD.getAllMedicines();
        if (medicines.isEmpty()) {
            System.out.println("No medicines found.");
        } else {
            System.out.println("ID\tName\t\tStock\tExpiry\t\tPrice\t\tStatus");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();

            for (Medicine med : medicines) {
                try {
                    Date expiry = sdf.parse(med.getExpiryDate());
                    String status = expiry.before(today) ? "EXPIRED" : "VALID";

                    System.out.printf("%d\t%-15s%d\t%s\t%.2f\t\t%s\n",
                            med.getId(), med.getName(), med.getStock(),
                            med.getExpiryDate(), med.getPrice(), status);
                } catch (Exception e) {
                    System.out.println("Error parsing date for medicine ID: " + med.getId());
                }
            }
        }
    }

    // ------------------------------------------------------------------------------------
// Update Medicine --------------------------------------------------------------------
    private static void updateMedicine() {
        try {
            int id = InputValidator.getIntInput(sc, "Enter Medicine ID to update: ");
            System.out.print("Enter New Name: ");
            String name = sc.nextLine();
            int stock = InputValidator.getIntInput(sc, "Enter New Stock: ");
            String expiry = InputValidator.getValidExpiryDate(sc, "Enter Expiry Date (yyyy-MM-dd): ");
            double price = InputValidator.getDoubleInput(sc, "Enter New Price: ");

            Medicine med = new Medicine(id, name, stock, expiry, price);
            medicineCRUD.updateMedicine(med);
        } catch (Exception e) {
            System.out.println("Error updating medicine: " + e.getMessage());
        }
    }

// -----------------------------------------------------------------------------------
// Delete Medicine -------------------------------------------------------------------

    private static void deleteMedicine() {
        try {
            int id = InputValidator.getIntInput(sc, "Enter Medicine ID to delete: ");
            medicineCRUD.deleteMedicine(id);
        } catch (Exception e) {
            System.out.println("Error deleting medicine: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------------------------
// Search Medicine by ID --------------------------------------------------------------
    private static void getMedicineById() {
        try {
            int id = InputValidator.getIntInput(sc, "Enter Medicine ID to search: ");
            Medicine med = medicineCRUD.getMedicineById(id);
            if (med != null) {
                System.out.println("ID: " + med.getId());
                System.out.println("Name: " + med.getName());
                System.out.println("Stock: " + med.getStock());
                System.out.println("Expiry: " + med.getExpiryDate());
                System.out.println("Price: " + med.getPrice());
            } else {
                System.out.println("Medicine not found.");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving medicine: " + e.getMessage());
        }
    }

// -------------------------------------------------------------------------------------------
// medicines with stock < 10.
    private static void checkLowStockMedicines() {
        List<Medicine> lowStockMeds = medicineCRUD.getLowStockMedicines();

        if (lowStockMeds.isEmpty()) {
            System.out.println("No Alerts");
            System.out.println("Take a chill pill :)");
            return;
        }

        System.out.println("==== LOW STOCK ALERTS ====");
        System.out.println("Stock\t\tExpiry\t\tName");

        lowStockMeds.sort(Comparator.comparingInt(Medicine::getStock)
                .thenComparing(Medicine::getExpiryDate));

        for (Medicine med : lowStockMeds){
            System.out.printf("%d\t\t%s\t\t%s\n", med.getStock(), med.getExpiryDate(), med.getName());
        }
    }

};
// -------------------------------------------------------------------------------------------
