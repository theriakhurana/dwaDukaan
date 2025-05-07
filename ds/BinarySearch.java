import java.util.ArrayList;

// data sorted according to id

public class BinarySearch{

  public static Medicine search(ArrayList<Medicine> arr, int id){

    int low = 0;
    int high = arr.size() -1;

    while(low <= high){
      int mid = low + (high - low)/2;
      Medicine midMedicine = arr.get(mid);
      if(midMedicine.getId() == id){
        return midMedicine;
      }else if(midMedicine.getId() < id){
        low = mid + 1;  
      }else{ 
        high = mid - 1;
      }
    }

    return null; // not found
  }
};