import java.util.*;
import java.io.*;

/**
 * Mapper
 */
public class Mapper {
    public String sent;
    public ArrayList<WordCount> wList;
    public double sumSquare = 0.0;
    public HashMap<String, Integer> hMap;

    public Mapper(ReviewItem rev) {
        this.sent = rev.sent;
        this.wList = countList(rev.wordBag);
        this.sumSq();
    }

    public Mapper(String str) {
        wList = new ArrayList<>();
        Scanner sc = new Scanner(str);
        if (sc.hasNext()) {
            this.sent = sc.next();
            if (this.sent.equals("+1") || this.sent.equals("-1")) {
                if (sc.hasNext()) {
                    strToWordCount(sc.nextLine());
                }
            } else {
                wList.add(new WordCount(this.sent, sc.nextInt()));
                this.sent = "";
                if (sc.hasNext()) {
                    strToWordCount(sc.nextLine());
                }
            }
        }
        this.sumSquare = sumSq();
        sc.close();
    }

    public void strToWordCount(String str) {
        Scanner sc = new Scanner(str);
        String temp = "";
        int iTemp = 0;
        while (sc.hasNext()) {
            temp = sc.next();
            iTemp = sc.nextInt();
            this.wList.add(new WordCount(temp, iTemp));
        }
        sc.close();
    }

    public void mapMaker() {
        this.hMap = new HashMap<>();
        WordCount temp;
        for (int i = 0; i < this.wList.size(); i++) {
            temp = this.wList.get(i);
            hMap.put(temp.word, temp.count);
        }
    }

    public double sumSq() {
        double temp = 0.0;
        double sum = 0.0;
        for (int i = 0; i < wList.size(); i++) {
            temp = (double) wList.get(i).count;
            sum += Math.pow(temp, 2.0);
        }
        return sum;
    }

    public static ArrayList<WordCount> countList(ArrayList<String> aList) {
        Collections.sort(aList);
        ArrayList<WordCount> retList = new ArrayList<>();
        if (aList.size() == 0) {
            return retList;
        }
        String fStr = "";
        WordCount wc = new WordCount(aList.get(0));
        retList.add(wc);
        for (int i = 1; i < aList.size(); i++) {
            fStr = aList.get(i);
            wc = retList.get(retList.size() - 1);

            if (wc.equals(fStr)) {
                wc.count++;
            } else {
                retList.add(new WordCount(fStr));
            }
        }
        return retList;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!this.sent.equals("")) {
            sb.append(this.sent + " ");
        }
        for (int i = 0; i < wList.size(); i++) {
            sb.append(wList.get(i).toString() + " ");
        }
        return sb.toString();
    }

    public double distance(Mapper train) {
        double top = 0.0;
        double bottom = (Math.sqrt(this.sumSquare)) * (Math.sqrt(train.sumSquare));
        if (bottom == 0) {
            return 0;
        }
        for (int i = 0; i < train.wList.size(); i++) {
            String wrd = train.wList.get(i).word;
            if (this.hMap.containsKey(wrd)) {
                top += (train.wList.get(i).count) * (this.hMap.get(wrd));
            }
        }
        return (double) top / bottom;
    }

}