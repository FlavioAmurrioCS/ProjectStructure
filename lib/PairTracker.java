import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PairTracker
 */
public class PairTracker<T> {

    private List<Pair<T>> pList;

    public PairTracker() {
        this.pList = Collections.synchronizedList(new ArrayList<Pair<T>>());
    }

    public synchronized void add(int index, T item) {
        pList.add(new Pair<>(index, item));
    }

    public synchronized void add(Pair<T> pair) {
        pList.add(pair);
    }

    private List<Pair<T>> getOrderList() {
        if (Thread.activeCount() > 1)
            System.out.print("Waiting for threads to end....");
        while (Thread.activeCount() > 1) {
            System.out.print("\b");
            Tools.slow(500);
            System.out.print(".");
        }
        Collections.sort(pList);
        return pList;
    }

    public void toFile(String filename) {
        Tools.listToFile(getOrderList(), filename);
    }
}