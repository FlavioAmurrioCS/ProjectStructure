import java.util.*;

/**
 * DecisionTree
 */
public class DecisionNode {

    public ArrayList<Gene> gList;
    public int score = -1;
    public DecisionNode equalNode; //True
    public DecisionNode notEqualNode; //False
    public DecisionNode parent;
    public int level;
    public int split;
    public String splitSeries;

    public static int nodeCount = 0;
    public static int maxLevel = 20;
    public static int minListCount = 1;
    public static int outlierDetection = 4;

    public DecisionNode(ArrayList<Gene> gList, DecisionNode parent) {
        this.gList = gList;
        this.equalNode = null;
        this.notEqualNode = null;
        this.parent = parent;
        if (parent == null) {
            this.level = 1;
            this.splitSeries = "";
        } else {
            this.level = this.parent.level + 1;
            this.splitSeries = this.parent.splitSeries;
        }
        // this.split = split;

        // nodeCount++;
        // System.out.println("Node Created with: " + gList.size());
    }

    public boolean isLeafNode() {
        int posCount = posCount();
        if (posCount == gList.size() || posCount == 0) {
            // System.out.println();
            terminateNode();
            this.print("HomoGeneous Node");
            return true;
        }

        if (gList.size() < minListCount) {
            // System.out.println();
            terminateNode();
            this.print("Min List Reached: " + gList.size());
            return true;
        }

        if (this.level > maxLevel) {
            // System.out.println();
            terminateNode();
            this.print("Max Level Reached");
            return true;
        }
        if(posCount <= outlierDetection)
        {
            // System.out.println();
            terminateNode();
            this.print("Outlier Detected");
            return true;
        }
        

        return false;
    }

    public boolean stopSplitting() {
        return this.score == 1 || this.score == 0;
    }

    public void terminateNode() {
        int posCount = posCount();
        int size = this.gList.size();
        int negCount = size - posCount;
        this.score = posCount >= negCount ? 1 : 0;
    }

    public Integer treeComparator(Gene gn) {
        // System.out.println("Nodes: " + nodeCount);
        if (score != -1) {
            return this.score;
        }
        if (gn.contains(this.split)) {
            return this.equalNode.treeComparator(gn);
        } else {
            if (this.notEqualNode == null) {
                return -1;
            }
            Integer t = this.notEqualNode.treeComparator(gn);
            return t;
        }
    }

    public int posCount() {
        int count = 0;
        for (Gene gn : gList) {
            if (gn.score == 1) {
                count++;
            }
        }
        return count;
    }

    public void setChild(DecisionNode equalN, DecisionNode notEqualN)
    {
        this.equalNode = equalN;
        this.notEqualNode = notEqualN;
    }

    public void setSplit(int sp)
    {
        this.split = sp;
        this.splitSeries = this.splitSeries + " " + sp;
    }

    public Double gini() {
        double size = (double) gList.size();
        double pos = (double) posCount();
        double neg = size - pos;
        double left = Math.pow((pos / size), 2);
        double right = Math.pow((neg / size), 2);
        double one = (double) 1;
        Double ret = one - left - right;
        return ret;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------------------------------------------------------------------\n");
        sb.append("Size: " + this.gList.size() + "\n");
        sb.append("PosCount: " + posCount() + "\n");
        sb.append("Level: " + this.level + "\n");
        sb.append("Split Series: " + this.splitSeries + "\n");
        sb.append("-----------------------------------------------------------------------------\n");
        return sb.toString();
    }

    public void print(String cat)
    {
        String filename = MinerGene.NODE_FILE + "-outlier-" + outlierDetection + "-minGene-" + minListCount + "-treeDepth_" + maxLevel +  ".txt";
        // System.out.println(this.toString());
        FTools.appendFile(filename, cat);
        FTools.appendFile(filename, this.toString());
    }

}
