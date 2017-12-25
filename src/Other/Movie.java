import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * Movie
 */
public class Movie {

    int movieId;
    String directorId = ""; // Director director;
    HashSet<String> genreSet; // List of Genre(genre)
    HashSet<Integer> tagSet; // List of Movie Tags(tagID)
    // HashMap<String, Double> actorRank; // List of Actors (actor_id, rank) normalize the rank
    // ArrayList<ActorPair> actorList;
    TreeSet<ActorPair> actorTree;
    TreeSet<TagPair> tagTree;
    HashMap<Integer, Double> userRating; // List of user and their ratings
    HashMap<Integer, Double> userTag; // (tagID, weight) count and then normalize
    HashMap<Integer, Double> tagWeight;

    HashSet<String> actorSet;

    double ratingSum = 0;
    double averageRating = 0;

    public Movie(int movieId) {
        this.movieId = movieId;
        genreSet = new HashSet<>();
        actorTree = new TreeSet<>();
        tagTree = new TreeSet<>();
        userRating = new HashMap<>();
    }

    public void setDirector(String directorId) {
        this.directorId = directorId;
    }

    public void addGenre(String genre) {
        genreSet.add(genre);
    }

    public void addTag(int tagId) {
        tagSet.add(tagId);
    }

    public void addActor(String actorID, int rank) {
        actorTree.add(new ActorPair(actorID, rank));
    }

    public void addTag(int tagID, int weight) {
        tagTree.add(new TagPair(tagID, weight));
    }

    public void addUserRating(int userId, double rating) {
        this.ratingSum += rating;
        userRating.put(userId, rating);
        this.averageRating = this.ratingSum / (double) this.userRating.size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Miner.TOP);
        sb.append("MovieID: " + this.movieId + "\n");
        sb.append("DirectorID: " + this.directorId + "\n");
        sb.append("Genres: " + genreSet.toString() + "\n");
        sb.append("Actor List: " + actorTree.toString() + "\n");
        sb.append("Views: " + this.userRating.size() + "\n");
        sb.append("Average Rating: " + this.averageRating + "\n");
        sb.append("Tag List: " + tagTree.toString() + "\n");
        sb.append(Miner.BOTTOM);
        return sb.toString();
    }

    public boolean equals(Movie movie) {
        return this.movieId == movie.movieId;
    }

    public HashMap<Integer, Double> prepTagWeight() {
        tagWeight = new HashMap<>();
        for (TagPair tp : tagTree) {
            this.tagWeight.put(tp.tagID, tp.weight);
        }
        actorSet = new HashSet<>();
        int count = 0;
        for (ActorPair ap : actorTree) {
            actorSet.add(ap.actorID);
            count++;
            if (count > 6)
                break;
        }
        return this.tagWeight;
    }

    public void tfidf(HashMap<Integer, Double> idfMap) {
        HashMap<Integer, Double> hMap = HashTools.multiply(HashTools.tf(this.tagWeight), idfMap);
        this.tagWeight.clear();
        this.tagWeight.putAll(hMap);
    }

    private double genreSimilarity(Movie movie) {
        // return HashTools.jaccardSimilarity(this.genreSet, movie.genreSet);
        return (double)(HashTools.union(this.genreSet, movie.genreSet).size())/(double)20;
    }

    public boolean hasDirector() {
        return this.directorId.equals("");
    }

    private double directorSimilarity(Movie movie) {
        if (!this.hasDirector() && !movie.hasDirector())
            return -1;
        return this.directorId.equals(movie.directorId) ? 1 : 0;
    }

    private double tagSimilarity(Movie movie) {
        if (this.tagWeight.size() < 3 || movie.tagWeight.size() < 3)
            return -1;
        return HashTools.cosineSimilarity(this.tagWeight, movie.tagWeight);
    }

    private double actorSimilarity(Movie movie) {
        if (this.actorSet.size() < 5 || movie.actorSet.size() < 5)
            return -1;
        return HashTools.jaccardSimilarity(this.actorSet, movie.actorSet);
    }

    public double similarity(Movie movie) {
        if (this.equals(movie))
            return 1;
        return this.tagSimilarity(movie);
        // return this.genreSimilarity(movie);
        // return this.actorSimilarity(movie);
        // return this.directorSimilarity(movie);
        // double gScore = this.genreSimilarity(movie);
        // double dScore;
        // if (!this.hasDirector() && !movie.hasDirector()) {
        //     return gScore;
        // } else {
        //     dScore = directorSimilarity(movie);
        // }

        // // double tScore = this.tagSimilarity(movie);

        // return (dScore * 0.20) + (gScore * 0.80);
    }
}