public class Timer {
    private long startTime;
    private long lapTime;
    private String method;

    public Timer() {
        this.startTime = System.currentTimeMillis();
        this.lapTime = this.startTime;
        this.method = "";
    }

    public Timer(String method) {
        this.startTime = System.currentTimeMillis();
        this.lapTime = this.startTime;
        this.method = method;
        System.out.print(this.method + " ...");
    }

    public void time() {
        long markTime = System.currentTimeMillis();
        double ellapseTime = markTime - this.startTime;
        ellapseTime /= 1000;
        String timeStr = (ellapseTime < 60) ? ellapseTime + "s" : (int)(ellapseTime / 60) + "m " + (int)ellapseTime % 60 + "s";
        if (this.method.equals("")) {
            System.out.println("Total Time: " + timeStr);
        } else {
            System.out.println("\r" + this.method + " took: " + timeStr);
        }
    }
    public void lap(String str)
    {
        long currentTime = System.currentTimeMillis();
        double ellapseTime = currentTime - this.lapTime;
        this.lapTime = currentTime;
        ellapseTime /= 1000;
        String timeStr = (ellapseTime < 60) ? ellapseTime + "s" : (int)(ellapseTime / 60) + "m " + ellapseTime % 60 + "s";
        if (this.method.equals("")) {
            System.out.println(str + " Lap: " + timeStr);
        } else {
            System.out.println("\r" + this.method + " took: " + timeStr);
        }
    }

}