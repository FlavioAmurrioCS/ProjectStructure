import java.util.*;
import java.io.*;

/**
 * kTerm
 */
public class KTerm {
    public String str;
    public ArrayList<Integer> kList;
    public String result;

    public KTerm(String str) {
        this.str = str;
        kList = new ArrayList<>();
        Scanner sc = new Scanner(this.str);
        while (sc.hasNext()) {
            kList.add(Integer.parseInt(sc.next()));
        }
        sc.close();
    }

    public void polarity(int k) {
        int res = 0;
        for (int i = 0; i < k; i++) {
            res += this.kList.get(i);
        }
        this.result = res < 0 ? "-1" : "+1";
    }

    public String toString() {
        return this.result;
    }
}