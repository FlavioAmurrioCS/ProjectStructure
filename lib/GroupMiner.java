import java.util.ArrayList;

/**
 * GroupMiner
 * 
 * This class will take in an arrayList and the range from which
 * data will process. This will then process the information in
 * a linear manner. Every intance of this class will run in a 
 * different thread. 
 */
public class GroupMiner<T extends MiningObj> implements Runnable {

    private int start;
    private int end;
    private ArrayList<T> sList;

    public GroupMiner(int start, int end, ArrayList<T> sList) {
        this.start = start;
        this.end = end;
        this.sList = sList;
    }

    public void run() {
        for (int i = start; i < end; i++)
            sList.get(i).doIt(i);
    }
}