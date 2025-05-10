import java.sql.*;
import java.util.*;

public class OrderCRUD {

// add new order
    public void addOrder(Order order) {
        String sql = "INSERT INTO orders (order_id, medicine_id, quantity, status) VALUES (?, ?, ?, ?)";
        try(Connection conn = dbIntegration.connectToDB();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, order.getOrderId());
            ps.setInt(2, order.getMedicineId());
            ps.setInt(3, order.getQuantity());
            ps.setInt(4, order.getStatus());
            ps.executeUpdate();

        }catch(SQLException e){
            System.out.println("Order Unsuccessful :(");
        }
    }

// -------------------------------------------------------------------------------------------
// view orders with their status
    public List<Order> getOrdersByStatus(int status) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE status = ?";
        try(Connection conn = dbIntegration.connectToDB();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, status);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Order order = new Order(
                            rs.getString("order_id"),
                            rs.getInt("medicine_id"),
                            rs.getInt("quantity")
                    );
                    order.setStatus(rs.getInt("status"));
                    orders.add(order);
                }
            }
        }catch(SQLException e){
            System.out.println("Failed to fetch orders :(");
        }
        return orders;
    }

// -------------------------------------------------------------------------------------------
// update status
    public void updateOrderStatus(String orderId, int status) {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        try(Connection conn = dbIntegration.connectToDB();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, status);
            ps.setString(2, orderId);
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println("Failed to update order status :(");
        }
    }

// -------------------------------------------------------------------------------------------
// view all orders
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try(Connection conn = dbIntegration.connectToDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
             while(rs.next()){
                Order order = new Order(
                        rs.getString("order_id"),
                        rs.getInt("medicine_id"),
                        rs.getInt("quantity")
                );
                order.setStatus(rs.getInt("status"));
                orders.add(order);
             }
        }catch(SQLException e){
            System.out.println("Failed to fetch all orders :(");
        }
        return orders;
    }
};
// -------------------------------------------------------------------------------------------
