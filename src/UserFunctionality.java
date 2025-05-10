import java.util.List;
import java.util.Scanner;

public class UserFunctionality {
    private static Scanner sc = new Scanner(System.in);
    private static MedicineCRUD medicineCRUD = new MedicineCRUD();
    private static OrderManager orderManager = OrderManager.getInstance();

    public static void function() {
        boolean exit = false;
        while (!exit) {
            printMenu();
            int input = InputValidator.getIntInput(sc, "Enter your choice: ");
            switch (input) {
                case 1:
                    viewAvailableMedicines();
                    break;
                case 2:
                    placeOrder();
                    break;
                case 3:
                    orderManager.getOrderStatus();
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

// -------------------------------------------------------------------------------------------
    private static void printMenu() {
        System.out.println("-----------------------------");
        System.out.println("1. View Available Medicines");
        System.out.println("2. Order Medicines");
        System.out.println("3. View Order Status");
        System.out.println("0. Exit");
        System.out.println("-----------------------------");
        System.out.println();
    }
// -------------------------------------------------------------------------------------------
// view all medicines

    private static void viewAvailableMedicines() {
        List<Medicine> medicines = medicineCRUD.getAllMedicines();
        if (medicines.isEmpty()) {
            System.out.println("No medicines found.");
        } else {
            System.out.println("ID\tName\tStock\tExpiry\t\tPrice");
            for (Medicine med : medicines) {
                System.out.printf("%d\t%s\t%d\t%s\t%.2f\n", med.getId(), med.getName(), med.getStock(), med.getExpiryDate(), med.getPrice());
            }
        }
    }

// -------------------------------------------------------------------------------------------
// add new order
    private static void placeOrder() {
        int medicineId = InputValidator.getIntInput(sc, "Enter Medicine ID: ");
        Medicine med = medicineCRUD.getMedicineById(medicineId);
        if (med == null) {
            System.out.println("Medicine not found");
            return;
        }
        int qty = InputValidator.getIntInput(sc, "Enter quantity: ");
        if (qty <= 0) {
            System.out.println("Quantity should be valid");
            return;
        }

        // unique order ID
        String orderId = "OID" + System.currentTimeMillis();
        Order order = new Order(orderId, medicineId, qty);
        orderManager.placeOrder(order);
    }
}
// -------------------------------------------------------------------------------------------