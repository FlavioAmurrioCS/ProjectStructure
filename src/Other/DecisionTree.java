import java.util.*;

/**
 * DecisionTree
 */
public class DecisionTree {

    public static HashSet<Integer> remSplits = remSplits(MinerGene.GENE_LIST_FILE);
    public DecisionNode root;
    public static ArrayList<Integer> geneList = FTools.fileToIntList(MinerGene.GENE_LIST_FILE);

    public DecisionTree(String filename) {
        this.root = treeMaker(filename);
    }

    public static DecisionNode treeMaker(String filename) {
        FTools.tittleMaker("TreeMaker");
        ArrayList<Gene> gList = Gene.fileToGeneList(filename);
        DecisionNode root = new DecisionNode(gList, null);
        rNodeBuilder(root);
        return root;
    }

    public static void rNodeBuilder(DecisionNode dn) {
        if (dn.isLeafNode()) {
            return;
        }
        ArrayList<Gene> gList = dn.gList;
        int split = whereToSplitIter(gList);
        dn.setSplit(split);
        ArrayList<Gene> equal = new ArrayList<>();
        ArrayList<Gene> notEqual = new ArrayList<>();
        splitAt(gList, equal, notEqual, split);
        DecisionNode equalNode = new DecisionNode(equal, dn);
        DecisionNode notEqualNode = new DecisionNode(notEqual, dn);
        dn.setChild(equalNode, notEqualNode);
        rNodeBuilder(equalNode);
        rNodeBuilder(notEqualNode);
    }

    public static int whereToSplit(ArrayList<Gene> gList) {
        double lowestGini = 99999;
        int sp = 0;
        for (int i = 1; i <= 100000; i++) {
            if (remSplits.contains(new Integer(i))) {
                continue;
            }
            double result = giniIfSplitAt(gList, i);
            if (result < lowestGini) {
                sp = i;
                lowestGini = result;
            }
        }
        return sp;
    }

    public static int whereToSplitIter(ArrayList<Gene> gList)
    {
        Iterator<Integer> it = geneList.iterator();
        double lowestGini = 99999;
        int sp = 0;
        for (; it.hasNext(); ) {
            int split = it.next();
            double result = giniIfSplitAt(gList, split);
            if (result < lowestGini) {
                sp = split;
                lowestGini = result;
            }
        }
        return sp;
    }

    public static ArrayList<Integer> getIteratorList(ArrayList<Gene> gList) {
        HashSet<Integer> iSet = new HashSet<>();
        for (Gene gn : gList) {
            iSet.addAll(gn.hSet);
        }
        iSet.removeAll(remSplits);
        ArrayList<Integer> retList = new ArrayList<>(iSet);
        iSet.clear();
        return retList;
    }

    public static HashSet<Integer> remSplits(String filename) {
        HashSet<Integer> remSet = new HashSet<>();
        for (int i = 1; i <= 100000; i++) {
            remSet.add(i);
        }
        ArrayList<Integer> iList = FTools.fileToIntList(filename);
        remSet.removeAll(iList);
        System.out.println("RemSet Size: " + remSet.size());
        return remSet;
    }

    public static double giniIfSplitAt(ArrayList<Gene> gList, Integer split) {
        ArrayList<Gene> equal = new ArrayList<>();
        ArrayList<Gene> notEqual = new ArrayList<>();
        for (Gene gn : gList) {
            if (gn.contains(split)) {
                equal.add(gn);
            } else {
                notEqual.add(gn);
            }
        }
        return wGini(equal, notEqual);
    }

    public static Double wGini(ArrayList<Gene> equal, ArrayList<Gene> notEqual) {
        double eSize = (double) equal.size();
        double nSize = (double) notEqual.size();
        double totalSize = eSize + nSize;
        double eGini = gini(equal, totalSize);
        double nGini = gini(notEqual, totalSize);
        return (eGini + nGini);
    }

    public static Double gini(ArrayList<Gene> gList, double totalSize) {
        double size = (double) gList.size();
        double pos = (double) posCount(gList);
        double neg = size - pos;
        double left = Math.pow((pos / size), 2);
        double right = Math.pow((neg / size), 2);
        double one = (double) 1;
        double ret = one - (left + right);
        ret = ret * (size / totalSize);
        return ret;
    }

    public static int posCount(ArrayList<Gene> gList) {
        int count = 0;
        for (Gene gn : gList) {
            if (gn.score == 1) {
                count++;
            }
        }
        return count;
    }

    public static void splitAt(ArrayList<Gene> gList, ArrayList<Gene> equal, ArrayList<Gene> notEqual, Integer split) {
        equal.clear();
        notEqual.clear();
        for (Gene gn : gList) {
            if (gn.contains(split)) {
                equal.add(gn);
            } else {
                notEqual.add(gn);
            }
        }
        // remSplits.add(split);
    }

    public int decider(Gene gn) {
        return this.root.treeComparator(gn);
    }

}

/*

    public static int whereToSplit(ArrayList<Gene> gList) {
        double lowestGini = 99999;
        int sp = 0;
        ArrayList<Integer> intList = getIteratorList(gList);
        for (Iterator<Integer> it = intList.iterator(); it.hasNext();) {
            Integer i = it.next();
            double result = giniIfSplitAt(gList, i);
            if (result < lowestGini) {
                sp = i;
                lowestGini = result;
            }
        }
        intList.clear();
        return sp;
    }


*/