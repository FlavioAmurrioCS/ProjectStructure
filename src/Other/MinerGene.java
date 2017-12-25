import java.util.*;

/**
 * MinerGene
 */
public class MinerGene {

    public static final String ORIGINAL_TRAIN_FILE = "./res/ori/1505760800_9146957_train_drugs.data";
    public static final String ORIGINAL_TEST_FILE = "./res/ori/1505760800_9191542_test.data";
    public static final String SCRATCH_FILE = "./res/out/scratch.txt";
    public static final String RESULT_FILE = "./res/out/results/result";
    public static final String NODE_FILE = "./res/out/results/node";
    public static final String GENECOUNT_FILE = "./res/out/GeneCount.txt";
    public static final String GENE_LIST_FILE = "./res/out/GeneList.txt";
    public static final String RESULT_FILE_LIST = "./res/out/resultFileList.txt";
    public static final String UNDER_SAMPLE = "./res/out/underSampleTrain.txt";

    public static void main(String[] args) {
        FTimer ft = new FTimer();
        // FTools.underSampling(ORIGINAL_TRAIN_FILE);
        decisionTreeMethod(ORIGINAL_TRAIN_FILE, ORIGINAL_TEST_FILE, 0, 4, 15);
        // // simulation(10, 20, 15);
        // // shortSim(10);

        // kFileMethod();
        // FTools.fileComparator(actual, prediction);
        ft.print();
    }

    public static void simulation(int outlier, int mincount, int maxLevel) {
        for (int k = 0; k <= maxLevel; k++) {
            for (int i = 0; i <= outlier; i++) {
                for (int j = 0; j <= mincount; j++) {
                    String str = decisionTreeMethod(ORIGINAL_TRAIN_FILE, ORIGINAL_TEST_FILE, i, j, k);
                    FTools.appendFile(RESULT_FILE_LIST, str);
                }
            }
        }
    }

    public static void shortSim(int outlier) {
        for (int i = 0; i <= outlier; i++) {
            String str = decisionTreeMethod(ORIGINAL_TRAIN_FILE, ORIGINAL_TEST_FILE, i, 5, 15);
            // FTools.appendFile(RESULT_FILE_LIST, str);
        }
    }

    public static String decisionTreeMethod(String trainfile, String testfile, int outlier, int minList, int maxLevel) {
        DecisionNode.outlierDetection = outlier;
        DecisionNode.minListCount = minList;
        DecisionNode.maxLevel = maxLevel;
        DecisionTree tree = new DecisionTree(trainfile);
        ArrayList<Gene> testList = Gene.fileToGeneList(testfile);
        ArrayList<Integer> result = new ArrayList<>();
        // ProgressBar pb = new ProgressBar(testList.size(), 1);
        for (int i = 0; i < testList.size(); i++) {
            Gene gn = testList.get(i);
            Integer temp = tree.decider(gn);
            result.add(temp);
            // pb.update(i);
        }
        String filename = RESULT_FILE + "-outlier-" + DecisionNode.outlierDetection + "-minGene-"
                + DecisionNode.minListCount + "-treeDepth-" + DecisionNode.maxLevel + "-posCount-" + posCount(result)
                + ".txt";
        FTools.listToFile(result, filename);
        return filename;
        // System.out.println("PosCount: " + posCount(result));
    }

    public static int posCount(ArrayList<Integer> resList) {
        int count = 0;
        for (Integer num : resList) {
            if (num == 1) {
                count++;
            }
        }
        return count;
    }

    public void overSampling(String original, String generated) {
        ArrayList<Gene> gList = Gene.fileToGeneList(original);

    }

    public void underSampling(String original, String generated) {

    }

    public static void kFileMethod() {
        String directory = "./res/out/results/";
        ArrayList<String> fList = FTools.getFileList(directory);
        ArrayList<Integer> numList = FTools.fileToIntList(directory + fList.get(0));
        for (int i = 1; i < fList.size(); i++) {
            ArrayList<Integer> tList = FTools.fileToIntList(directory + fList.get(i));
            if (tList.size() != 350) {
                continue;
            }
            for (int j = 0; j < 350; j++) {
                int t = numList.get(j) + tList.get(j);
                numList.set(j, t);
            }
        }

        for (int i = 0; i < numList.size(); i++) {
            int num = numList.get(i) == (fList.size()) ? 1 : 0;
            numList.set(i, num);
        }
        FTools.listToFile(numList, SCRATCH_FILE);
    }
}