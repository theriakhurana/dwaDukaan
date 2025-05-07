import java.util.ArrayList;

public class SortData{

  public static void sort(ArrayList<Medicine> arr, Comparator<Medicine> comp){
    mergeSort(arr, 0, arr.size() - 1, comp);
  }

  private static void mergeSort(ArrayList<Medicine> arr, int low, int high, Comparator<Medicine> comp){
    if(low < high){
      int mid = (low + high) / 2;
      mergeSort(arr, low, mid, comp);
      mergeSort(arr, mid + 1, high, comp);
      merge(arr, low, mid, high, comp);
    }
  }

  private static void merge(ArrayList<Medicine> arr, int low, int mid, int high, Comparator<Medicine> comp){
    ArrayList<Medicine> temp = new ArrayList<>();
    int i = low, j = mid+1;

    while(i <= mid && j <= high){
      if(comp.compare(arr.get(i), arr.get(j)) <= 0){
        temp.add(arr.get(i));
        i++;
      }else{
        temp.add(arr.get(j));
        j++;
      }
    }

    // remaining left half 
    while(i <= mid){
      temp.add(arr.get(i));
      i++;
    }

    // remaining right half
    while(j <= high){
      temp.add(arr.get(j));
      j++;
    }

    // copy to original array
    for(int k = low; k <= high; k++){
      arr.set(k, temp.get(k - low));
    }
  }
}