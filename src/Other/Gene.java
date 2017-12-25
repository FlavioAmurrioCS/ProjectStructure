import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.HashSet;

/**
 * Gene
 */
public class Gene {

    public ArrayList<Integer> nList;
    public HashSet<Integer> hSet;
    public int score;

    public Gene(String str) {
        Scanner sc = new Scanner(str);
        this.nList = new ArrayList<>();
        this.hSet = new HashSet<>();
        int temp = sc.nextInt();
        if (temp <= 1) {
            this.score = temp;
        } else {
            nList.add(temp);
        }
        while (sc.hasNext()) {
            String sTemp = sc.next();
            Integer iTemp = Integer.parseInt(sTemp);
            nList.add(iTemp);
        }
        hSet.addAll(this.nList);
        // Collections.sort(nList);
        sc.close();
    }

    // public int getMin()
    // {
    //     return nList.get(0);
    // }
    // public int getMax()
    // {
    //     return nList.get(nList.size()-1);
    // }
    // public int size()
    // {
    //     return nList.size();
    // }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (score == 1 || score == 0) {
            sb.append(score);
        }
        for (int i = 0; i < nList.size(); i++) {
            sb.append(" " + nList.get(i).toString());
        }
        return sb.toString();
    }

    public void hashMaker() {
        hSet.addAll(this.nList);
    }

    public int geneDist(Gene train) {
        int size = this.hSet.size();
        this.hSet.removeAll(train.hSet);
        int ret = size - this.hSet.size();
        this.hashMaker();
        return ret;
    }

    public static ArrayList<Gene> fileToGeneList(String filename) {
        ArrayList<String> lines = FTools.fileToList(filename);
        ArrayList<Gene> retList = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String str = lines.get(i);
            Gene gn = new Gene(str);
            retList.add(gn);
        }
        return retList;
    }

    public boolean contains(int i) {
        return hSet.contains(i);
    }
}