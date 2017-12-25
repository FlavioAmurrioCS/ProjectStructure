/**
 * WordCount
 */
public class WordCount implements Comparable<WordCount> {

    public final String word;
    public int count;

    public WordCount(String str) {
        this.word = str;
        this.count = 1;
    }

    public WordCount(String str, int count) {
        this.word = str;
        this.count = count;
    }

    public String toString() {
        return this.word + " " + this.count;
    }

    public boolean equals(String str) {
        return this.word.equals(str);
    }

    public int compareTo(WordCount wd) {
        return this.count - wd.count;
    }
}