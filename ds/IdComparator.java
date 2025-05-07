import java.util.Comparator;

public class IdComparator implements Comparator<Medicine>{
    @Override
    public int compare(Medicine m1, Medicine m2){
        return Integer.compare(m1.getId(), m2.getId());
    }
}
