import java.util.*;

/**
 * GeneCount
 */
public class GeneCount implements Comparable<GeneCount> {
    public int gene;
    public int count;

    public GeneCount(int gene) {
        this.gene = gene;
        this.count = 1;
    }

    public GeneCount(int gene, int count) {
        this.gene = gene;
        this.count = count;
    }

    public boolean equals(GeneCount o) {
        return this.gene == o.gene;
    }

    public boolean equals(Integer i) {
        return this.gene == i;
    }

    public String toString() {
        return "Gene: " + gene + "     Count: " + count;
    }

    public int compareTo(GeneCount o) {
        return o.count - this.count;
    }

    public static ArrayList<GeneCount> geneCountList(ArrayList<Gene> gList) {
        ArrayList<GeneCount> gcList = new ArrayList<>();
        ArrayList<Integer> iList = new ArrayList<>();
        for (Gene gn : gList) {
            iList.addAll(gn.nList);
        }
        Collections.sort(iList);
        gcList.add(new GeneCount(iList.get(0)));
        for (int i = 1; i < iList.size(); i++) {
            GeneCount last = gcList.get(gcList.size() - 1);
            Integer num = iList.get(i);
            if (last.equals(num)) {
                last.count++;
            } else {
                gcList.add(new GeneCount(num));
            }
        }
        Collections.sort(gcList);
        return gcList;
    }
}