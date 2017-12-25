/**
 * HardWorker
 *  
 */
public class Pair<V> implements Comparable<Pair<V>> {

    int index;
    V item;

    public Pair(int index, V item) {
        this.index = index;
        this.item = item;
    }

    public int compareTo(Pair<V> p) {
        return this.index - p.index;
    }

    public String toString() {
        return this.index + ": " + item.toString();
    }

}