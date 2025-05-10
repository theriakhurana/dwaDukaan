import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineCRUD {
    private static MedicineCRUD instance = null; // global access

    public static MedicineCRUD getInstance() {
        if(instance == null) return new MedicineCRUD();
        return instance;
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
            System.out.println("Failed to add medicine.");
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
            System.out.println("Failed to update medicine.");
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
            System.out.println("Failed to delete medicine.");
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
            System.out.println("Failed to fetch medicines.");
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
            System.out.println("Failed to get medicine by ID");
        }
        return null;
    }
}
// -------------------------------------------------------------------------------------------