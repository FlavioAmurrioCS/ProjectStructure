/**
 * GeneDist
 */
public class GeneDist implements Comparable<GeneDist> {

    public int dist;
    public int score;
    
    public GeneDist (int dist, int score) {
        this.dist = dist;
        this.score = score;
    }

    public int compareTo(GeneDist gd)
    {
        return -(this.dist - gd.dist);
    }

    public String toString()
    {
        return this.dist + " " + this.score; 
    }
}