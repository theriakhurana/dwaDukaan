import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class OrderManager {
    private static OrderManager instance = null; // for global access
    private static Queue<Order> pendingOrders = new LinkedList<>();
    private static ArrayList<Order> processedOrders = new ArrayList<>();
    private static MedicineManager medicineManager = MedicineManager.getInstance();

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }
// -------------------------------------------------------------------------------------
// for user -> order place krne k liye..

    public void placeOrder(Order neworder){
        neworder.setStatus(0); // pending -> 0
        pendingOrders.offer(neworder);
        System.out.println("Order placed successfully");
        System.out.println(neworder);
    }

// -------------------------------------------------------------------------------------
// User -> to check status
    public void getOrderStatus(){
        if(pendingOrders.isEmpty()){
            System.out.println("Order toh place krlo uncle!!");
            return;
        }

        for(Order order : processedOrders){
            String status_str = (order.getStatus() == 1) ? "Processed" : "Failed";
            System.out.println(order + " | Status: " + status_str);
        }
        for(Order order : pendingOrders){
            System.out.println(order + " | Status: Pending");
        }
    }


// -------------------------------------------------------------------------------------
// Admin -> to see all the pending orders

    public void viewPendingOrders(){
        if (pendingOrders.isEmpty()) {
            System.out.println("No pending orders.");
            return;
        }
        System.out.println("Pending Orders:");
        for (Order order : pendingOrders) {
            System.out.println(order + " | Status: Pending");
        }
    }

// -------------------------------------------------------------------------------------
// Admin -> Process Order from the queue

    public void processNextOrder(){
        if (pendingOrders.isEmpty()) {
            System.out.println("No pending orders to process.");
            return;
        }

        Order order = pendingOrders.poll();
        Medicine med = medicineManager.getMedicineById(order.getMedicineId());
        if(med == null){
            System.out.println("Medicine ID " + order.getMedicineId() + " not found.");
            order.setStatus(-1); // Failed --> -1
            processedOrders.add(order);
            return;
        }

        if(med.getStock() >= order.getQuantity()){
            int updatedStock = med.getStock() - order.getQuantity();
            med.setStock(updatedStock);
            order.setStatus(1); // processed // stock updated
            processedOrders.add(order);
            System.out.println("Order processed successfully:");
            System.out.println(order);
        }
        else{
            System.out.println("Insufficient stock for medicine ID: " + order.getMedicineId());
            order.setStatus(-1); // failed
            processedOrders.add(order);
        }
    }
// -----------------------------------------------------------------------
// Admin -> View processed orders

    public void viewProcessedOrders(){
        if(processedOrders.isEmpty()){
            System.out.println("No processed orders.");
            return;
        }

        System.out.println("Processed Orders: ");
        System.out.println("------------------------------");
        for(Order order : processedOrders){
            String status_str = order.getStatus() == 1 ? "Processed" : "Failed";
            System.out.println(order + " | Status: " + status_str);
        }
    }

}
