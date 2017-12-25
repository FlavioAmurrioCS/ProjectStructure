/**
 * ReviewItem
 */
import java.io.PrintWriter;
import java.util.*;

public class ReviewItem {

    public String sent;
    public String revStr;
    public ArrayList<String> wordBag;

    public ReviewItem(String str) {
        Scanner sc = new Scanner(str);
        if (!sc.hasNext()) {
            this.sent = "";
            this.revStr = "";
        } else {
            this.sent = sc.next();
            if (this.sent.equals("+1") || this.sent.equals("-1")) {
                this.revStr = (sc.hasNext()) ? sc.nextLine() : "";
            } else {
                String temp = sc.hasNext() ? sc.nextLine() : "";
                this.revStr = this.sent + " " + temp;
                this.sent = "";
            }
        }
        sc.close();
    }

    public void stringCleaner() {
        this.revStr = this.revStr.replaceAll("[^a-zA-Z]", " ").toLowerCase();
        if(Miner.STEMMER)
        {
            this.revStr = StemTest.lucy(this.revStr);
        }
        
    }

    public void listMaker(int wTerm) {
        this.wordBag = phrases(FTools.stringToList(this.revStr), wTerm);
    }

    public static ArrayList<String> phrases(ArrayList<String> aList, int num) {
        ArrayList<String> retList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < aList.size(); i++) {
            for (int j = 0; j < num && i + j < aList.size(); j++) {
                sb.append(aList.get(j + i));
            }
            retList.add(sb.toString());
            sb = new StringBuilder();
        }
        return retList;
    }

    public int stopRemover(HashSet<String> stopSet) {
        int count = 0;
        if (this.wordBag == null || this.wordBag.size() < 1) {
            this.listMaker(1);
        }
        for (int i = 0; i < this.wordBag.size(); i++) {
            if (stopSet.contains(this.wordBag.get(i))) {
                this.wordBag.remove(i);
                i--;
                count++;
            }
        }
        return count;
    }

    public void setUnion(HashSet<String> hSet) {
        for (int i = 0; i < this.wordBag.size(); i++) {
            if (!hSet.contains(this.wordBag.get(i))) {
                this.wordBag.remove(i);
                i--;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!this.sent.equals("")) {
            sb.append(this.sent + " ");
        }
        for (int i = 0; i < this.wordBag.size(); i++) {
            sb.append(this.wordBag.get(i) + " ");
        }
        return sb.toString();
    }
}