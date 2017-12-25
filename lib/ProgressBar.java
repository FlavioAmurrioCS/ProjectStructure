public class ProgressBar {
    private long startTime;
    private int size;
    private int unit;
    private int lastPrint = 0;
    private char[] bar = "..................................................".toCharArray();

    public ProgressBar(int size) {
        this.size = size;
        this.unit = this.size / 100;
        this.startTime = System.currentTimeMillis();
    }

    public void update(int i) {
        int perc = i / this.unit;
        if (perc != this.lastPrint) {
            long remainingTime = remainingTime(perc);            
            lastPrint = perc;
            System.out.print("\r" + barMaker(perc) + " " + timeToString(remainingTime) + "      ");
            
        }
        if (i == this.size - 1)
            System.out.println();
    }

    private long remainingTime(int perc) {
        long elapseTime = System.currentTimeMillis() - this.startTime;
        long estimateTime = (100 * elapseTime) / perc;
        return (estimateTime - elapseTime);
    }

    private String barMaker(int perc) {
        for (int i = lastPrint/2; i < perc / 2; i++) {
            if (i < bar.length) {
                bar[i] = '#';
            }
        }
        perc = perc > 100 ? 100 : perc;
        return "Progress: [" + perc + "%][" + new String(bar) + "]";
    }

    private String timeToString(long time) {
        time /= 1000;
        if (time > 60)
            return "Time: " + (time / 60) + "m " + (time % 60) + "s";
        else
            return "Time: " + time + "s";
    }
}