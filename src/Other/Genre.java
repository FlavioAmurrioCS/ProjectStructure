
/**
 * Genre
 */

import java.util.*;

public class Genre {

    String genreID;
    HashSet<Integer> movieSet; //List of Movies

    public Genre(String genreID) {
        this.genreID = genreID;
    }

    public void addMovie(int movieID) {
        if (movieSet == null) {
            movieSet = new HashSet<>();
        }
        movieSet.add(movieID);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Miner.TOP);
        sb.append("Genre: " + this.genreID + "\n");
        sb.append("Movie List: " + this.movieSet.toString() + "\n");
        sb.append(Miner.BOTTOM);
        return sb.toString();
    }

}