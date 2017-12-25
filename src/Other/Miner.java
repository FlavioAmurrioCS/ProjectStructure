import java.util.*;
import java.io.*;

public class Miner {

    //Original Files
    public static final String ORIGINAL_TRAINSET_FILE = "ori/1504108575_8218148_train_file .data";
    public static final String ORIGINAL_TESTSET_FILE = "ori/1504108575_8450022_test.data";

    //Resource Files
    public static final String POS_WORD_FILE = "res/NEG_WORD_LIST.txt";
    public static final String NEG_WORD_FILE = "res/POS_WORD_LIST.txt";
    public static final String SENTIMENT_FILE = "res/SentimentWords.txt";
    public static final String STOP_WORDS_FILE = "res/STOP_WORDS.txt";

    //Generated Files
    public static final String BAG_OF_WORDS_FILE = "gen/BagOfWords.txt";
    public static final String POS_WORD_FREQ_FILE = "gen/WordFreqPos.txt";
    public static final String NEG_WORD_FREQ_FILE = "gen/WordFreqNeg.txt";
    public static final String WORD_FREQ_FILE = "gen/WordFreqAll.txt";
    public static final String TRAIN_MAP_FILE = "gen/TrainMap.txt";
    public static final String TEST_MAP_FILE = "gen/TestMap.txt";
    public static final String DISTANCE_FILE = "gen/Nearest.txt";

    public static final String TEST_SPLIT_FILE = "gen/TestSplit.txt";
    public static final String TRAIN_SPLIT_FILE = "gen/TrainSplit.txt";

    public static final String STAT_NOFILTER_FILE = "gen/StatNoFilter.txt";
    public static final String STAT_YESFILTER_FILE = "gen/StatYesFilter.txt";

    //Output Files
    public static final String K1_FILE = "out/K1_Term.txt";
    public static final String K3_FILE = "out/K3_Term.txt";
    public static final String K5_FILE = "out/K5_Term.txt";
    public static final String K7_FILE = "out/K7_Term.txt";
    public static final String K9_FILE = "out/K9_Term.txt";
    public static final String K11_FILE = "out/K11_Term.txt";

    public static boolean STEMMER = true;
    public static boolean PORTER = true;
    public static double MAXSCORE = 0;

    public static void main(String[] args) {
        FTimer ft = new FTimer();
        // combination(STAT_NOFILTER_FILE);

        originalRunner();

        ft.print();
    }

    public static void trainMapping(String trainFile, int commonCap, int lessThan) {
        FTimer ft = new FTimer();
        FTools.tittleMaker("Train Set Mapping");
        Scanner sc = FTools.fileOpener(trainFile);

        ArrayList<ReviewItem> rList = new ArrayList<>();
        ArrayList<Mapper> mList = new ArrayList<>();
        ArrayList<String> allWords = new ArrayList<>();
        ArrayList<String> posWords = new ArrayList<>();
        ArrayList<String> negWords = new ArrayList<>();

        HashSet<String> stopWords = FTools.fileToHashSet(STOP_WORDS_FILE);

        while (sc.hasNext()) {
            String str = sc.nextLine();
            ReviewItem rev = new ReviewItem(str);
            rev.stringCleaner();
            rev.listMaker(1);
            allWords.addAll(rev.wordBag);
            if (rev.sent.equals("+1")) {
                posWords.addAll(rev.wordBag);
            } else {
                negWords.addAll(rev.wordBag);
            }
            rList.add(rev);
        }
        sc.close();

        HashSet<String> common = commonWordSet(posWords, negWords, commonCap);

        HashSet<String> bSet = new HashSet<>();
        bSet.addAll(allWords);
        bSet.removeAll(stopWords);
        bSet.removeAll(common);

        freqRemover(bSet, allWords, lessThan);

        for (int i = 0; i < rList.size(); i++) {
            ReviewItem rev = rList.get(i);
            rev.setUnion(bSet);
            Mapper mp = new Mapper(rev);
            mList.add(mp);
        }
        FTools.listToFile(mList, TRAIN_MAP_FILE);
        FTools.hashSetToFile(bSet, BAG_OF_WORDS_FILE);
        ft.print();
    }

    public static void testMapping(String testFile) {
        FTimer sw = new FTimer();
        FTools.tittleMaker("Test Set Mapping");
        Scanner sc = FTools.fileOpener(testFile);
        ArrayList<ReviewItem> rList = new ArrayList<>();
        HashSet<String> bSet = FTools.fileToHashSet(BAG_OF_WORDS_FILE);

        ArrayList<Mapper> mList = new ArrayList<>();

        while (sc.hasNextLine()) {
            ReviewItem rev = new ReviewItem(sc.nextLine());
            rev.stringCleaner();
            rev.listMaker(1);
            rev.setUnion(bSet); //words only in the bSet
            Mapper mp = new Mapper(rev);
            mList.add(mp);
        }
        FTools.listToFile(mList, TEST_MAP_FILE);
        sw.print();
    }

    public static void nearestNeighbor() {
        FTimer ft = new FTimer();
        FTools.tittleMaker("Nearest Neighbor");
        ArrayList<String> result = new ArrayList<>();

        ArrayList<Mapper> trainList = mapFileList(TRAIN_MAP_FILE);
        ArrayList<Mapper> testList = mapFileList(TEST_MAP_FILE);
        ArrayList<WordDist> distList = new ArrayList<>();

        ProgressBar pg = new ProgressBar(testList.size(), 1);

        Mapper testM;
        Mapper trainM;
        double dist;
        WordDist wDist;

        for (int i = 0; i < testList.size(); i++) {
            testM = testList.get(i);
            testM.mapMaker();

            for (int j = 0; j < trainList.size(); j++) {
                trainM = trainList.get(j);
                dist = testM.distance(trainM);
                wDist = new WordDist(dist, trainM.sent);
                distList.add(wDist);
            }
            pg.update(i);

            result.add(listCutter(distList, 11));
        }
        System.out.println();

        FTools.listToFile(result, DISTANCE_FILE);
        ft.print();
    }

    public static void kGenerator() {
        FTools.tittleMaker("kGenerator");
        FTimer sw = new FTimer();
        ArrayList<KTerm> kList = new ArrayList<>();
        Scanner sc = FTools.fileOpener(DISTANCE_FILE);
        while (sc.hasNext()) {
            KTerm kt = new KTerm(sc.nextLine());
            kt.polarity(1);
            kList.add(kt);
        }

        FTools.listToFile(kList, K1_FILE);

        for (int i = 0; i < kList.size(); i++) {
            kList.get(i).polarity(3);
        }

        FTools.listToFile(kList, K3_FILE);

        for (int i = 0; i < kList.size(); i++) {
            kList.get(i).polarity(5);
        }
        FTools.listToFile(kList, K5_FILE);

        for (int i = 0; i < kList.size(); i++) {
            kList.get(i).polarity(7);
        }
        FTools.listToFile(kList, K7_FILE);

        for (int i = 0; i < kList.size(); i++) {
            kList.get(i).polarity(9);
        }
        FTools.listToFile(kList, K9_FILE);

        for (int i = 0; i < kList.size(); i++) {
            kList.get(i).polarity(11);
        }
        FTools.listToFile(kList, K11_FILE);
        sw.print();

    }

    public static String listCutter(ArrayList<WordDist> distList, int k) {
        Collections.sort(distList);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < k; i++) {
            sb.append(distList.get(i).sent + " ");
        }
        distList.clear();
        return sb.toString();
    }

    public static ArrayList<Mapper> mapFileList(String filename) {
        Scanner sc = FTools.fileOpener(filename);
        ArrayList<Mapper> mList = new ArrayList<>();
        Mapper mp;
        while (sc.hasNextLine()) {
            mp = new Mapper(sc.nextLine());
            mList.add(mp);
        }
        return mList;
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
        Collections.sort(retList);
        return retList;
    }

    public static void freqRemover(HashSet<String> bow, ArrayList<String> sList, int num) {
        ArrayList<WordCount> wcList = countList(sList);
        FTools.listToFile(wcList, WORD_FREQ_FILE);
        int count = 0;
        HashSet<String> lowFreq = new HashSet<String>();
        for (int i = 0; i < wcList.size(); i++) {
            WordCount wc = wcList.get(i);
            if (wc.count <= num) {
                lowFreq.add(wc.word);
                count++;
            }
        }
        bow.removeAll(lowFreq);
        System.out.println("Removed: " + count);
    }

    public static HashSet<String> commonWordSet(ArrayList<String> pos, ArrayList<String> neg, int perc) {
        ArrayList<WordCount> negWC = countList(neg);
        ArrayList<WordCount> posWC = countList(pos);
        FTools.listToFile(negWC, NEG_WORD_FREQ_FILE);
        FTools.listToFile(posWC, POS_WORD_FREQ_FILE);

        ArrayList<String> posNeg = new ArrayList<>();
        posNeg.addAll(pos);
        posNeg.addAll(neg);
        ArrayList<WordCount> comPosNeg = countList(posNeg);
        double pc = (double) perc / 100;
        int size = comPosNeg.size();
        pc = pc * size;

        HashSet<String> retSet = new HashSet<>();

        for (int i = (int) pc; i < comPosNeg.size(); i++) {
            WordCount wc = comPosNeg.remove(i);
            retSet.add(wc.word);
            i--;
        }

        // HashSet<String> nSet = new HashSet<>();

        // for (int i = 0; i < negWC.size(); i++) {
        //     if (negWC.get(i).count > cap) {
        //         nSet.add(negWC.get(i).word);
        //     }
        // }
        // negWC.clear();

        // for (int i = 0; i < posWC.size(); i++) {
        //     WordCount wc = posWC.get(i);
        //     if (wc.count > cap && nSet.contains(wc.word)) {
        //         retSet.add(wc.word);
        //     }
        // }
        // posWC.clear();
        return retSet;
    }

    public static void fileSplit() {
        ArrayList<String> fList = FTools.fileToList(ORIGINAL_TRAINSET_FILE);
        ArrayList<String> testList = new ArrayList<>();
        ArrayList<String> trainList = new ArrayList<>();

        Random rand = new Random(System.currentTimeMillis());

        int random = rand.nextInt(4);

        for (int i = 0; i < fList.size(); i++) {
            random = rand.nextInt(4);
            if (random == 2) {
                testList.add(fList.get(i));
            } else {
                trainList.add(fList.get(i));
            }
        }

        FTools.listToFile(testList, TEST_SPLIT_FILE);
        FTools.listToFile(trainList, TRAIN_SPLIT_FILE);
    }

    public static double fileAcc(String actual, String test) {
        ArrayList<String> aList = FTools.fileToList(actual);
        ArrayList<String> tList = FTools.fileToList(test);
        int posCounter = 0;
        char aChar;
        char tChar;

        for (int i = 0; i < aList.size(); i++) {
            aChar = aList.get(i).charAt(0);
            tChar = tList.get(i).charAt(0);
            if (aChar == tChar) {
                posCounter++;
            }

        }
        return (double) posCounter / aList.size();
    }

    public static void combination(String logFile) {
        combHelp(logFile);
        PORTER = false;
        combHelp(logFile);
        STEMMER = false;
        combHelp(logFile);

        System.out.println("MAXSCORE: " + MAXSCORE);
    }

    public static void combHelp(String logFile) {
        for (int i = 0; i < 5; i++) {
            for (int j = 80; j <= 100; j += 2) {
                fileSplit();
                trainMapping(TRAIN_SPLIT_FILE, j, i);
                testMapping(TEST_SPLIT_FILE);
                nearestNeighbor();
                kGenerator();
                double score = fileAcc(TEST_SPLIT_FILE, K1_FILE);
                combLog(1, j, i, score, logFile);

                score = fileAcc(TEST_SPLIT_FILE, K3_FILE);
                combLog(3, j, i, score, logFile);

                score = fileAcc(TEST_SPLIT_FILE, K5_FILE);
                combLog(5, j, i, score, logFile);

                score = fileAcc(TEST_SPLIT_FILE, K7_FILE);
                combLog(7, j, i, score, logFile);

                score = fileAcc(TEST_SPLIT_FILE, K9_FILE);
                combLog(9, j, i, score, logFile);

                score = fileAcc(TEST_SPLIT_FILE, K11_FILE);
                combLog(11, j, i, score, logFile);
            }
        }
    }

    public static void combLog(int k, int commonCap, int lessThan, double score, String logFile) {
        MAXSCORE = score > MAXSCORE ? score : MAXSCORE;
        int size = FTools.fileToList(BAG_OF_WORDS_FILE).size();
        StringBuilder sb = new StringBuilder();
        sb.append("\n-------------------------------------------------------\n");
        sb.append("K Term:     " + k + "\n");
        sb.append("CommonCap:  " + commonCap + "\n");
        sb.append("Less Than:  " + lessThan + "\n");
        sb.append("BagOfWords: " + size + "\n");
        sb.append("Score:      " + score + "\n");
        sb.append("Porter:     " + PORTER + "\n");
        sb.append("STEMMER:    " + STEMMER + "\n");

        try {

            FileWriter fw = new FileWriter(logFile, true);
            fw.write(sb.toString());//appends the string to the file
            fw.close();
        } catch (Exception e) {

        }
        System.out.println(sb.toString());
    }

    public static void originalRunner() {
        trainMapping(ORIGINAL_TRAINSET_FILE, 100, 0);
        testMapping(TEST_SPLIT_FILE);
        nearestNeighbor();
        kGenerator();


        double score = 0.00000f;

        score = fileAcc(TEST_SPLIT_FILE, K3_FILE);
        combLog(3, 100, 0, score, STAT_NOFILTER_FILE);

        score = fileAcc(TEST_SPLIT_FILE, K5_FILE);
        combLog(5, 100, 0, score, STAT_NOFILTER_FILE);

        score = fileAcc(TEST_SPLIT_FILE, K7_FILE);
        combLog(7, 100, 0, score, STAT_NOFILTER_FILE);

        score = fileAcc(TEST_SPLIT_FILE, K9_FILE);
        combLog(9, 100, 0, score, STAT_NOFILTER_FILE);

        score = fileAcc(TEST_SPLIT_FILE, K11_FILE);
        combLog(11, 100, 0, score, STAT_NOFILTER_FILE);
    }

}