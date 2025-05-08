public class Order {

    private final String orderId;
    private final int medicineId;
    private final int quantity;

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

    @Override
    public String toString() {
        return String.format("OrderID: %s, MedicineID: %d, Quantity: %d", orderId, medicineId, quantity);
    }
}
