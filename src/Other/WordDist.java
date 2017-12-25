/**
 * WordDist
 */
public class WordDist implements Comparable<WordDist>  {
    public double dist;
    public String sent;

    public WordDist (double dist, String sent) {
        this.dist = dist;
        this.sent = sent;        
    }

    public int compareTo(WordDist o)
    {
        Double dou = this.dist;
        Double that = o.dist;
        return -dou.compareTo(that);
    }

    public String toString()
    {
        return "" + this.dist + " " + this.sent;
    }
}