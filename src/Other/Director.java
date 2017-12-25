
/**
 * Director
 */

import java.util.*;

public class Director {

    String directorId;
    HashSet<Integer> movieSet; // List of movies

    public Director(String directorId) {
        this.directorId = directorId;
    }

    public void addMovie(int movieId) {
        if (movieSet == null) {
            movieSet = new HashSet<>();
        }
        movieSet.add(movieId);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Miner.TOP);
        sb.append("DirectorID: " + this.directorId + "\n");
        sb.append("Movie List: " + this.movieSet.toString() + "\n");
        sb.append(Miner.BOTTOM);
        return sb.toString();
    }
}