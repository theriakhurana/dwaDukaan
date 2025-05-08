import java.util.ArrayList;
import java.util.HashMap;

//for doing crud operations on medicines using id

public class MedicineManager {
  private HashMap<Integer, Medicine> medicines;

  public MedicineManager() {
    medicines = new HashMap<>();
  }
//----------------------------------------------------------------------------------
// add new medicine
  public void addNewMedicine(Medicine med){
    medicines.put(med.getId(), med);
  }
//----------------------------------------------------------------------------------
//read all medicines
  public void displayAllMedicines(){
    if(medicines.isEmpty()){
      System.out.println("Inventory is empty");
      return;
    }

    for(Medicine med : medicines.values()){
      System.out.println(med);
    }
    System.out.println();
  }
//----------------------------------------------------------------------------------
// read medicine by id
  public Medicine getMedicineById(int id){
    if(medicines.containsKey(id)) return medicines.get(id);
    return null;
  }

//----------------------------------------------------------------------------------
// update medicine by id
  public void updateMedicine(int id, String name, int stock, String expiryDate, double price){
    Medicine med = medicines.get(id);
    med.setName(name);
    med.setStock(stock);
    med.setExpiryDate(expiryDate);
    med.setPrice(price);
  }
//----------------------------------------------------------------------------------
// delete medicine by id
  public boolean deleteMedicine(int id){
    if(medicines.containsKey(id)){
      medicines.remove(id);
      return true;
    }
    return false;
  }
//----------------------------------------------------------------------------------
};