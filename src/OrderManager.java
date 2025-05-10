import java.util.Queue;
import java.util.List;
import java.util.LinkedList;

public class OrderManager {

    //global access
    private static OrderManager instance = null;
    private static OrderCRUD orderCRUD = new OrderCRUD();
    private static MedicineCRUD medicineCRUD = MedicineCRUD.getInstance();

    // for pending orders (FIFO order mei)
    private final Queue<Order> pendingOrderQueue = new LinkedList<>();

    public static OrderManager getInstance() {
        if(instance == null){
            instance = new OrderManager();
        }
        return instance;
    }

// -------------------------------------------------------------------------------------------
// Place order (User)
    public void placeOrder(Order newOrder) {
        newOrder.setStatus(0); // pending
        orderCRUD.addOrder(newOrder);
        System.out.println("Order placed successfully");
        System.out.println(newOrder);
    }

// -------------------------------------------------------------------------------------------
// sare pending orders db se queue mei load krne k liye
    public void loadPendingOrdersIntoQueue() {
        List<Order> pendingOrders = orderCRUD.getOrdersByStatus(0); // 0-> pending state
        pendingOrderQueue.clear();
        pendingOrderQueue.addAll(pendingOrders);
    }

// -------------------------------------------------------------------------------------------
// db se just fetch orders with status 0.
    public void viewPendingOrders() {
        List<Order> pendingOrders = orderCRUD.getOrdersByStatus(0);
        if(pendingOrders.isEmpty()){
            System.out.println("No pending orders.");
            return;
        }
        System.out.println("Pending Orders:");
        for(Order order : pendingOrders){
            System.out.println(order + " | Status: Pending");
        }
    }
// -------------------------------------------------------------------------------------------
// Admin --> Process next order from the queue
    public void processNextOrder() {
        // If queue is empty, load from db
        if(pendingOrderQueue.isEmpty()){
            loadPendingOrdersIntoQueue();
        }
        if(pendingOrderQueue.isEmpty()){
            System.out.println("No pending orders to process.");
            return;
        }

        Order order = pendingOrderQueue.poll();
        Medicine med = medicineCRUD.getMedicineById(order.getMedicineId());
        if(med == null){
            System.out.println("Medicine ID " + order.getMedicineId() + " not found.");
            orderCRUD.updateOrderStatus(order.getOrderId(), -1); // Failed
            return;
        }

        if(med.getStock() >= order.getQuantity()){
            med.setStock(med.getStock() - order.getQuantity());
            medicineCRUD.updateMedicine(med);
            orderCRUD.updateOrderStatus(order.getOrderId(), 1); // Processed
            System.out.println("Order processed successfully:");
            System.out.println(order);
        }else{
            System.out.println("Insufficient stock for medicine ID: " + order.getMedicineId());
            orderCRUD.updateOrderStatus(order.getOrderId(), -1); // Failed
        }
    }
// -------------------------------------------------------------------------------------------
// Admin: View processed and failed orders
    public void viewProcessedOrders() {
        List<Order> processedOrders = orderCRUD.getOrdersByStatus(1);
        if(processedOrders.isEmpty()){
            System.out.println("No processed orders.");
        }
        else{
            System.out.println("Processed Orders:");
            for(Order order : processedOrders){
                System.out.println(order + " | Status: Processed");
            }
        }
        List<Order> failedOrders = orderCRUD.getOrdersByStatus(-1);
        if(!failedOrders.isEmpty()){
            System.out.println("Failed Orders:");
            for(Order order : failedOrders){
                System.out.println(order + " | Status: Failed");
            }
        }
    }

// -------------------------------------------------------------------------------------------
// User --> Check status of all orders
    public void getOrderStatus() {
        List<Order> allOrders = orderCRUD.getAllOrders();
        if(allOrders.isEmpty()){
            System.out.println("No orders found.");
            return;
        }
        for(Order order : allOrders){
            String statusStr;
            switch(order.getStatus()){
                case 1:
                    statusStr = "Processed";
                    break;
                case -1:
                    statusStr = "Failed";
                    break;
                default:
                    statusStr = "Pending";
            }
            System.out.println(order + " | Status: " + statusStr);
        }
    }
}
// -------------------------------------------------------------------------------------------