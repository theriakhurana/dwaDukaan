import java.util.ArrayList;

//for doing crud operations on medicines

public class MedicineManager {
  private ArrayList<Medicine> medicines;

  public MedicineManager() {
    medicines = new ArrayList<>();
  }

  //create
  public boolean addNewMedicine(Medicine med){
    if(getMedicineById(med.getId()) != null) return false; // medicine with this id already exists

    medicines.add(med);
    return true; 
  }

  //read all medicines
  public void displayAllMedicines(){
    if(medicines.isEmpty()){
      System.out.println("No medicines found");
      return;
    }

    for(Medicine med : medicines){
      System.out.println(med.toString());
    }
  }
  
  // read medicine by id
  public Medicine getMedicineById(int id){
    for(Medicine med : medicines){
      if(med.getId() == id) return med;
    }
    return null;
  }

  // update
  public boolean updateMedicine(int id, String name, int stock, String expiryDate, double price){
    Medicine med = getMedicineById(id);
    if(med == null) return false; // medicine ni mili

    med.setName(name);
    med.setStock(stock);
    med.setExpiryDate(expiryDate);
    med.setPrice(price);

    return true; // medicine updated
  }

  // delete
  public boolean deleteMedicine(int id){
    Medicine med = getMedicineById(id);
    if(med == null) return false; // medicine ni mili

    medicines.remove(med);
    return true; // medicine deleted
  }
};