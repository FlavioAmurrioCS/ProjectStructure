import java.util.*;

/**
 * Tag
 */

public class Tag {

    int tagID;
    HashSet<Integer> movieSet;

    public Tag(int tagID) {
        this.tagID = tagID;
    }

    public void addMovie(int movieID) {
        if (movieSet == null)
            movieSet = new HashSet<>();
        movieSet.add(movieID);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Miner.TOP);
        sb.append("Tag: " + this.tagID + " " + Miner.tagList.get(this.tagID) + "\n");
        sb.append("Movie List: " + this.movieSet.toString() + "\n");
        sb.append(Miner.BOTTOM);
        return sb.toString();
    }
}