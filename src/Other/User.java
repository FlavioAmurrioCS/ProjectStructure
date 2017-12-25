
/**
 * User
 */
import java.util.*;

public class User {
    int userID;
    HashMap<Integer, Double> movieMap;
    double sumRating = 0;
    double avgRating = 0;

    public User(int userID) {
        this.userID = userID;
        movieMap = new HashMap<>();
    }

    public void addMovie(int movieID, double rating) {
        movieMap.put(movieID, rating);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Miner.TOP);
        sb.append("UserID: " + this.userID + "\n");
        sb.append("Movie List: " + this.movieMap.keySet().toString() + "\n");
        sb.append(Miner.BOTTOM);
        return sb.toString();
    }

    public double predictRating(int movieID) {
        double maxScore = 0;
        int simMovie = -1;
        Movie pMovie = Miner.movieMap.get(movieID);
        for (Integer key : movieMap.keySet()) {
            Movie mov = Miner.movieMap.get(key);
            double score = pMovie.similarity(mov);
            if (score > maxScore) {
                maxScore = score;
                simMovie = key;
            }
        }
        // if (simMovie == -1)
        //     System.out.println("movie not found");
        // Movie m = Miner.movieMap.get(simMovie);
        return simMovie != -1 ? movieMap.get(simMovie) : this.avgRating;
    }

    // public double kRating(int movieID) {
    //     Movie pMovie = Miner.movieMap.get(movieID);
    //     for (Integer key : movieMap.keySet()) {
    //         Movie mov = Miner.movieMap.get(key);
    //         double score = pMovie.similarity(mov);
    //         if (score > maxScore) {
    //             maxScore = score;
    //             simMovie = key;
    //         }
    //     }
    //     // if (simMovie == -1)
    //     //     System.out.println("movie not found");
    //     // Movie m = Miner.movieMap.get(simMovie);
    //     return simMovie != -1 ? movieMap.get(simMovie) : this.avgRating;
    // }
}