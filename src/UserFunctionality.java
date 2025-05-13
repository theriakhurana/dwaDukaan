import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UserFunctionality {
    private static Scanner sc = new Scanner(System.in);
    private static MedicineCRUD medicineCRUD = new MedicineCRUD();
    private static OrderManager orderManager = OrderManager.getInstance();
    private static ArrayList<String> feedbackList = new ArrayList<>();

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
                case 4:
                    addFeedback();
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
        System.out.println("4. Add Feedback");
        System.out.println("0. Exit");
        System.out.println("-----------------------------");
        System.out.println();
    }
// -------------------------------------------------------------------------------------------
// view all medicines

    private static void viewAvailableMedicines() {
        List<Medicine> medicines = medicineCRUD.getAllMedicines();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        if (medicines.isEmpty()) {
            System.out.println("No medicines found.");
        } else {
            System.out.println("ID\tName\t\tPrice");
            for (Medicine med : medicines) {
                try {
                    Date expiry = sdf.parse(med.getExpiryDate());
                    // Show only medicines that are NOT expired
                    if (expiry.after(today)) {
                        System.out.printf("%d\t%s\t%.2f\n", med.getId(), med.getName(), med.getPrice());
                    }
                } catch (ParseException e) {
                    System.out.println("Error fetching medicines");
                }
            }
        }
    }

// -------------------------------------------------------------------------------------------
// add new order
    private static void placeOrder() {
        int medicineId = InputValidator.getIntInput(sc, "Enter Medicine ID: ");
        Medicine med = medicineCRUD.getMedicineById(medicineId);

        if(med == null){
            System.out.println("Medicine not found.");
            return;
        }

        // Check if the medicine is expired
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        try{
            Date expiry = sdf.parse(med.getExpiryDate());
            if(expiry.before(today)){
                System.out.println("This medicine is expired and cannot be ordered.");
                return;
            }
        }catch(Exception e){
            System.out.println("Error occured!");
            return;
        }

        int qty = InputValidator.getIntInput(sc, "Enter quantity: ");
        if (qty <= 0) {
            System.out.println("️Quantity should be greater than zero.");
            return;
        }

        if (qty > med.getStock()) {
            System.out.println("️Insufficient stock. Only " + med.getStock() + " units available.");
            return;
        }

        // Unique order ID generation
        String orderId = "OID" + System.currentTimeMillis();
        Order order = new Order(orderId, medicineId, qty);
        orderManager.placeOrder(order);

        // Reduce stock after successful order
        med.setStock(med.getStock() - qty);
        medicineCRUD.updateMedicine(med);

        // confirmation text
        System.out.println("Order placed successfully!");
        System.out.println("Order Summary:");
        System.out.println("Order ID     : " + orderId);
        System.out.println("Medicine Name: " + med.getName());
        System.out.println("Quantity     : " + qty);
        System.out.printf ("Unit Price   : Rs.%.2f\n", med.getPrice());
        System.out.printf ("Total Price  : Rs.%.2f\n", med.getPrice() * qty);
    }

//-----------------------------------------------------------------------------------------------------
// user k liye -> add feedback
    private static void addFeedback() {
        String feedback = InputValidator.getStringInput(sc,"Enter your feedback: ");
        feedbackList.add(feedback);
        System.out.println("Thank you for your feedback!");
    }

// admin k liye -> view all feedbacks
    public static void viewFeedbacks() {
        if (feedbackList.isEmpty()) {
            System.out.println("No feedback yet.");
        } else {
            System.out.println("Feedback from users:");
            for (String feedback : feedbackList) {
                System.out.println("- " + feedback);
            }
        }
    }
};
// -------------------------------------------------------------------------------------------