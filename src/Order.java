public class Order {

    private final String orderId;
    private final int medicineId;
    private final int quantity;
    private int status;
    // -1 --> failed
    // 0 --> pending
    // 1 --> processed

    public Order(String orderId, int medicineId, int quantity) {
        this.orderId = orderId;
        this.medicineId = medicineId;
        this.quantity = quantity;
    }

// getters
    public String getOrderId() {
        return orderId;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getStatus(){
        return status;
    }

// setters
    public void setStatus(int status){
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("OrderID: %s, MedicineID: %d, Quantity: %d", orderId, medicineId, quantity);
    }
};
