import java.sql.*;
import java.sql.Date;
import java.util.*;

public class MedicineCRUD {
    private static MedicineCRUD instance = null; // global access
    private static final int minStockFlag = 10;

    public static MedicineCRUD getInstance() {
        if(instance == null) return new MedicineCRUD();
        return instance;
    }

// -------------------------------------------------------------------------------
// priority queue -> medicines with stock less than 10
    private static final PriorityQueue<Medicine> lowStockQueue = new PriorityQueue<>(
            Comparator.comparingInt(Medicine::getStock)
                      .thenComparing(Medicine::getExpiryDate)
    );

    // avoids duplicate medicine ids on multiple calls
    private static final Set<Integer> seenMeds = new HashSet<>();

    private void checkForLowStock() {
        List<Medicine> allMeds = getAllMedicines();

        for (Medicine med : allMeds) {
            if (med.getStock() <= minStockFlag && !seenMeds.contains(med.getId())) {
                lowStockQueue.offer(med);
                seenMeds.add(med.getId());
            }
        }
    }

    public List<Medicine> getLowStockMedicines() {
        checkForLowStock();
        return new ArrayList<>(lowStockQueue);
    }

// ------------------------------------------------------------------------------
// Hashmap --> overall stock using medicine name.
    public void viewStockSummary(){
        List<Medicine> allMeds = getAllMedicines();
        if(allMeds.isEmpty()){
            System.out.println("Inventory is empty");
            return;
        }

        Map<String, Integer> map = new HashMap<>();
        for(Medicine med : allMeds){
            String name = med.getName();
            int stock = med.getStock();
            map.put(name, map.getOrDefault(name, 0) + stock);
        }

        System.out.println("\n=== Total Stock Available===");
        System.out.println("Name\t\tTotal Stock");
        for(Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.printf("%s\t\t%d\n", entry.getKey(), entry.getValue());
        }
    }

// -------------------------------------------------------------------------------
// add new medicine to database --------------------------------------------------
    public void addMedicine(Medicine med) throws SQLException {
        try (Connection conn = dbIntegration.connectToDB();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO medicines (name, stock, expiry_date, price) VALUES (?, ?, ?, ?)")) {

            ps.setString(1, med.getName());
            ps.setInt(2, med.getStock());
            ps.setDate(3, Date.valueOf(med.getExpiryDate()));
            ps.setDouble(4, med.getPrice());

            ps.executeUpdate();
            System.out.println("Medicine added :)");
        }catch(SQLException e){
            System.out.println("Database Error: " + e.getMessage());
        }
    }

// ---------------------------------------------------------------------------------
// update medicine -----------------------------------------------------------------
    public void updateMedicine(Medicine med) {
        try(Connection conn = dbIntegration.connectToDB();
            PreparedStatement ps = conn.prepareStatement("UPDATE medicines SET name=?, stock=?, expiry_date=?, price=? WHERE id=?")) {

            ps.setString(1, med.getName());
            ps.setInt(2, med.getStock());
            ps.setDate(3, Date.valueOf(med.getExpiryDate()));
            ps.setDouble(4, med.getPrice());
            ps.setInt(5, med.getId());

            ps.executeUpdate();
            System.out.println("Medicine updated :)");
        }catch(SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }
// ----------------------------------------------------------------------------
// delete medicine -----------------------------------------------------------
    public void deleteMedicine(int id) {
        try(Connection conn = dbIntegration.connectToDB();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM medicines WHERE id=?")) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Medicine deleted!");
        }catch(SQLException e){
            System.out.println("Database Error: " + e.getMessage());
        }
    }

// ----------------------------------------------------------------------------
// Get all medicines ----------------------------------------------------------
    public List<Medicine> getAllMedicines() {
        List<Medicine> medicines = new ArrayList<>();

        try(Connection conn = dbIntegration.connectToDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM medicines")) {

            while(rs.next()){
                Medicine med = new Medicine(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("stock"),
                        rs.getDate("expiry_date").toString(),
                        rs.getDouble("price")
                );
                medicines.add(med);
            }
        }catch(SQLException e){
            System.out.println("Database Error: " + e.getMessage());
        }
        return medicines;
    }

// ----------------------------------------------------------------------------
// Search ny Id ---------------------------------------------------------------
    public Medicine getMedicineById(int id) {
        try(Connection conn = dbIntegration.connectToDB();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM medicines WHERE id = ?")) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    return new Medicine(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("stock"),
                            rs.getDate("expiry_date").toString(),
                            rs.getDouble("price")
                    );
                }
            }
        }catch(SQLException e){
            System.out.println("Database Error: " + e.getMessage());
        }
        return null;
    }
}
// -------------------------------------------------------------------------------------------