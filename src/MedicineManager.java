import java.util.HashMap;

//for doing crud operations on medicines using id

public class MedicineManager {
  private static MedicineManager instance = null; // for global access
  private HashMap<Integer, Medicine> medicines;

  public MedicineManager() {
    medicines = new HashMap<>();
  }

  public static MedicineManager getInstance() {
    if (instance == null) {
      instance = new MedicineManager();
    }
    return instance;
  }

//----------------------------------------------------------------------------------
// add new medicine
  public void addNewMedicine(Medicine med){
    medicines.put(med.getId(), med);
  }

//----------------------------------------------------------------------------------
//read all medicines
  public void displayAdminView(){
    if(medicines.isEmpty()){
      System.out.println("Inventory is empty");
      return;
    }

    for(Medicine med : medicines.values()){
      System.out.println(med);
    }
    System.out.println();
  }

  public void displayUserView(){
    if(medicines.isEmpty()){
      System.out.println("Sorry!! Medicines out of stock");
      return;
    }

    for(Medicine med : medicines.values()){
      System.out.printf("ID: %d | Name: %s | Price: %.2f\n", med.getId(), med.getName(), med.getPrice());
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