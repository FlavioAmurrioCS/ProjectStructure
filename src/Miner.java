/**
 * Miner
 */
public class Miner {

    public static void main(String[] args) {
        ProgressBar pb = new ProgressBar(Integer.MAX_VALUE);
        for(int i = 0; i < Integer.MAX_VALUE; i++)
            pb.update(i);
    }
}

        