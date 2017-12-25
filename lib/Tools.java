import java.io.FileInputStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.util.Random;
import java.util.Collections;
import java.util.List;

/**
 * WorkEnv
 */
public class Tools {

    public static boolean SHOW_LOG = false;

    public static void tittleMaker(String str) {
        System.out.println("-----------------------------" + str + "-----------------------------");
    }

    public static PrintWriter fileWriter(String filename) {
        try {
            File file = new File(filename);
            PrintWriter pw = new PrintWriter(file);
            if (SHOW_LOG) {
                System.out.println("Writing " + filename + " ...");
            }
            return pw;
        } catch (Exception e) {
            System.out.println("ERROR: Can't access file " + filename);
            System.exit(1);
        }
        return null;
    }

    public static void slow(int milli){
        try {
            Thread.sleep(milli);
        } catch (Exception e) {
        }
    }

    public static void slowRandom(int milliMax){
        slow((new Random()).nextInt(milliMax));
    }

    public static Scanner fileReader(String filename) {
        try {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            Scanner sc = new Scanner(fis);
            if (SHOW_LOG) {
                System.out.println("Reading " + "...");
            }
            return sc;

        } catch (Exception e) {
            System.out.println("Error: Can't read " + filename);
            System.exit(1);
        }
        return null;
    }

    public static <T> void listToFile(List<T> outList, String filename) {
        PrintWriter pw = fileWriter(filename);
        for (T obj : outList) {
            pw.println(obj.toString());
        }
        pw.close();
    }

    public static <T> ArrayList<T> fileToObjList(String filename, Class<T> cls) {
        ArrayList<T> retList = new ArrayList<>();
        Constructor<T> constructor = null;
        Scanner sc = fileReader(filename);
        try {
            constructor = cls.getConstructor(String.class);
            retList.add(constructor.newInstance(sc.nextLine()));

        } catch (Exception e) {
            System.out.println("Error: Casting Error");
            System.exit(1);
        }
        return retList;
    }

    public static ArrayList<String> scannerToStrList(Scanner sc){
        ArrayList<String> strList = new ArrayList<>();
        while(sc.hasNextLine())
            strList.add(sc.nextLine());
        sc.close();
        return strList;
    }


    // public static ArrayList<String> fileToList(String filename) {
    //     Scanner sc = fileOpener(filename);
    //     ArrayList<String> arrList = new ArrayList<>();
    //     while (sc.hasNext()) {
    //         arrList.add(sc.nextLine());
    //     }
    //     return arrList;
    // }

    // public static HashSet<String> fileToHashSet(String filename) {
    //     HashSet<String> hSet = new HashSet<>();
    //     Scanner sc = FTools.fileOpener(filename);
    //     while (sc.hasNext()) {
    //         hSet.add(sc.next());
    //     }
    //     sc.close();
    //     return hSet;
    // }

    // public static void hashSetToFile(HashSet<String> hSet, String filename) {
    //     ArrayList<String> arrList = new ArrayList<>(hSet);
    //     Collections.sort(arrList);
    //     listToFile(arrList, filename);
    // }

    // public static ArrayList<String> tokenize(String str) {
    //     Scanner sc = new Scanner(str);
    //     ArrayList<String> retList = new ArrayList<>();
    //     while (sc.hasNext()) {
    //         retList.add(sc.next());
    //     }
    //     sc.close();
    //     return retList;
    // }

    // public static ArrayList<String> fileToScoreList(String filename) {
    //     Scanner sc = fileOpener(filename);
    //     ArrayList<String> retList = new ArrayList<>();
    //     while (sc.hasNext()) {
    //         retList.add(sc.next());
    //         sc.nextLine();
    //     }
    //     return retList;
    // }

    // public static void fileComparator(String actual, String prediction) {
    //     ArrayList<String> act = fileToScoreList(actual);
    //     ArrayList<String> pre = fileToScoreList(prediction);
    //     int correct = 0;
    //     if (act.size() != pre.size()) {
    //         System.exit(0);
    //     }
    //     for (int i = 0; i < act.size(); i++) {
    //         if (act.get(i).equals(pre.get(i))) {
    //             correct++;
    //         }
    //     }
    //     System.out.println(correct + " out of " + act.size());
    // }

    // public static void appendFile(String filename, String text) {
    //     try {

    //         FileWriter fw = new FileWriter(filename, true);
    //         fw.write("\n" + text);//appends the string to the file
    //         fw.close();
    //     } catch (Exception e) {

    //     }
    // }

    // public static ArrayList<String> getFileList(String directory) {
    //     ArrayList<String> fList = new ArrayList<>();
    //     File folder = new File(directory);
    //     File[] listOfFiles = folder.listFiles();
    //     for (int i = 0; i < listOfFiles.length; i++) {
    //         if (listOfFiles[i].isFile()) {
    //             fList.add(listOfFiles[i].getName());
    //         }
    //     }
    //     return fList;
    // }

    // public void textify(String input, String features, String output)
    // {
    //     ArrayList<String> strList = new ArrayList<>();
    //     Scanner sc = fileOpener(features);
    //     while(sc.hasNext()){
    //         strList.add(sc.nextLine());
    //     }
    //     sc.close();

    //     ArrayList<String> lines = new ArrayList<>();
    //     sc = fileOpener(input);


    // }

    // public static HashMap<String, Integer> fileToHashMap(String filename){
    //     ArrayList<String> sList = fileToList(filename);
    //     HashMap<String, Integer> fMap = new HashMap<>();
    //     for(int i = 0; i < sList.size(); i++)
    //     {
    //         fMap.put(sList.get(i), i);
    //     }
    //     return fMap;
    // }

}