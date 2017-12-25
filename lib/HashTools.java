import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Calc
 */
public class HashTools {

    // ############################### HashMap Parsers ###############################
    public static HashMap<Integer, Double> arrToHashMap(double[] values, int startNum) {
        HashMap<Integer, Double> hMap = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            if (values[i] != 0)
                hMap.put(i + startNum, values[i]);
        }
        return hMap;
    } // Done

    public static HashMap<Integer, Double> listToHashMap(ArrayList<Double> vList, int startNum) {
        HashMap<Integer, Double> hMap = new HashMap<>();
        for (int i = 0; i < vList.size(); i++) {
            double value = vList.get(i);
            if (value != 0)
                hMap.put(i + startNum, value);
        }
        return hMap;
    } // Done

    public static <K> HashMap<K, Double> arrPairToHashMap(K[] keySet, double[] values) {
        if (keySet.length != values.length) {
            System.out.println("Mismatched Arrays!");
            System.exit(1);
        }
        HashMap<K, Double> hMap = new HashMap<>();
        for (int i = 0; i < keySet.length; i++) {
            if (hMap.containsKey(keySet[i])) {
                System.out.println("Duplicate Detected!!!");
                System.exit(1);
            }
            if (values[i] != 0)
                hMap.put(keySet[i], values[i]);
        }
        return hMap;
    }

    public static HashMap<Integer, Double> strToHashMap(String line, int startNum) {
        HashMap<Integer, Double> hMap = new HashMap<>();
        Scanner sc = new Scanner(line);
        int count = startNum;
        while (sc.hasNextDouble()) {
            double val = sc.nextDouble();
            if (val != 0)
                hMap.put(count, val);
            count++;
        }
        sc.close();
        return hMap;
    }

    public static HashMap<String, Double> StrPairtoHashMap(String line) {
        HashMap<String, Double> hMap = new HashMap<>();
        Scanner sc = new Scanner(line);
        while (sc.hasNext()) {
            String key = sc.next();
            double val = sc.nextDouble();
            if (hMap.containsKey(key)) {
                sc.close();
                System.out.println("Duplicate Key!!!");
                System.exit(0);
            }
            if (val != 0)
                hMap.put(key, val);
        }
        sc.close();
        return hMap;
    }

    // ############################### HashMap Math Functions ###############################

    public static <K> HashMap<K, Double> add(HashMap<K, Double> a, HashMap<K, Double> b) {
        HashSet<K> keySet = union(a, b);
        HashMap<K, Double> sum = new HashMap<>();
        for (K key : keySet) {
            double aV = a.containsKey(key) ? a.get(key) : 0;
            double bV = b.containsKey(key) ? b.get(key) : 0;
            double value = aV + bV;
            if (value != 0)
                sum.put(key, value);
        }
        return sum;
    }

    public static <K> HashMap<K, Double> minus(HashMap<K, Double> a, HashMap<K, Double> b) {
        HashSet<K> keySet = union(a, b);
        HashMap<K, Double> diff = new HashMap<>();
        for (K key : keySet) {
            double aV = a.containsKey(key) ? a.get(key) : 0;
            double bV = b.containsKey(key) ? b.get(key) : 0;
            double value = aV - bV;
            if (value != 0)
                diff.put(key, value);
        }
        return diff;
    }

    public static <K> HashMap<K, Double> multiply(HashMap<K, Double> a, HashMap<K, Double> b) {
        HashSet<K> keySet = intersection(a, b);
        HashMap<K, Double> sum = new HashMap<>();
        for (K key : keySet) {
            double aV = a.get(key);
            double bV = b.get(key);
            double value = aV * bV;
            if (value != 0)
                sum.put(key, value);
        }
        return sum;
    }

    public static <K> HashMap<K, Double> multiply(HashMap<K, Double> a, double mul) {
        HashMap<K, Double> map = new HashMap<>();
        if (mul != 0) {
            for (K key : a.keySet()) {
                double value = a.get(key);
                value *= mul;
                if (value != 0)
                    map.put(key, value);
            }
        }
        return map;
    }

    public static <K> HashMap<K, Double> divide(HashMap<K, Double> a, double div) {
        if (div != 0) {
            return multiply(a, 1 / div);
        }
        throw new ArithmeticException("Can't Divide by Zero!!!");
    }

    public static double sumValues(HashMap<?, Double> a) {
        double sum = 0;
        for (Double val : a.values()) {
            sum += val;
        }
        return sum;
    }

    // ############################### HashMap Set Functions ###############################

    public static <K> HashSet<K> union(Set<K> a, Set<K> b) {
        HashSet<K> keySet = new HashSet<>(a);
        keySet.addAll(b);
        return keySet;
    }

    public static <K> HashSet<K> union(HashMap<K, ?> a, HashMap<K, ?> b) {
        return union(a.keySet(), b.keySet());
    }

    public static <K> HashSet<K> intersection(Set<K> a, Set<K> b) {
        HashSet<K> keySet = new HashSet<>(a);
        keySet.retainAll(b);
        return keySet;
    }

    public static <K> HashSet<K> intersection(HashMap<K, ?> a, HashMap<K, ?> b) {
        return intersection(a.keySet(), b.keySet());
    }

    public static <K> HashSet<K> exclusiveOr(Set<K> a, Set<K> b) {
        HashSet<K> keySet = new HashSet<>(union(a, b));
        keySet.removeAll(intersection(a, b));
        return keySet;
    }

    public static <K> HashSet<K> exclusiveOr(HashMap<K, ?> a, HashMap<K, ?> b) {
        return exclusiveOr(a.keySet(), b.keySet());
    }

    public static <K> HashMap<K, ?> removeKeys(HashMap<K, ?> a, HashSet<K> keys) {
        HashMap<K, ?> map = new HashMap<>(a);
        map.keySet().removeAll(keys);
        return map;
    }

    // ############################### HashMap Distance Functions ###############################

    public static <K> double euclideanSquare(HashMap<K, Double> a, HashMap<K, Double> b) {
        double sum = 0;
        HashSet<K> keySet = union(a, b);
        for (K key : keySet) {
            double aV = a.containsKey(key) ? a.get(key) : 0;
            double bV = b.containsKey(key) ? b.get(key) : 0;
            double value = aV - bV;
            value = value * value;
            sum += value;
        }
        return sum;
    }

    public static <K> double eucleadianDistance(HashMap<K, Double> a, HashMap<K, Double> b) {
        return Math.sqrt(euclideanSquare(a, b));
    }

    public static <K> double manhattanDistance(HashMap<K, Double> a, HashMap<K, Double> b) {
        HashSet<K> keySet = union(a, b);
        double sum = 0;
        for (K key : keySet) {
            double aV = a.containsKey(key) ? a.get(key) : 0;
            double bV = b.containsKey(key) ? b.get(key) : 0;
            double value = aV - bV;
            value = value > 0 ? value : -value;
            sum += value;
        }
        return sum;
    }

    public static <K> double cosineSimilarity(HashMap<K, Double> a, HashMap<K, Double> b) {
        double aSumSquare = 0;
        double bSumSquare = 0;
        for (double d : a.values())
            aSumSquare += (d * d);
        for (double d : b.values())
            bSumSquare += (d * d);
        aSumSquare = Math.sqrt(aSumSquare);
        bSumSquare = Math.sqrt(bSumSquare);
        double proSum = 0;
        HashSet<K> union = union(a, b);
        for (K key : union)
            proSum += getValue(a, key)* getValue(b, key);
        return proSum / (aSumSquare * bSumSquare);
    }

    public static <K> double getValue(HashMap<K, Double> a, K key) {
        return a.containsKey(key) ? a.get(key) : 0;
    }

    public static <K> double jaccardSimilarity(Set<K> a, Set<K> b) {
        if (a.size() == 0 && b.size() == 0)
            return 1;
        double intersection = intersection(a, b).size();
        double union = union(a, b).size();
        return intersection / union;
    }

    public static <K> double jaccardSimilarity(HashMap<K, Double> a, HashMap<K, Double> b) {
        return jaccardSimilarity(a.keySet(), b.keySet());
    }

    public static <K> HashMap<K, Double> avgHash(ArrayList<HashMap<K, Double>> hList) {
        double size = hList.size();
        HashMap<K, Double> map = new HashMap<>();
        HashSet<K> keySet = getKeys(hList);
        for (K key : keySet) {
            double sum = 0;
            for (HashMap<K, Double> a : hList) {
                double aV = a.containsKey(key) ? a.get(key) : 0;
                sum += aV;
            }
            double value = sum / size;
            if (value != 0)
                map.put(key, sum / size);
        }
        return map;
    }

    public static double average(HashMap<?, Double> hMap) {
        double sum = 0;
        for (double d : hMap.values())
            sum += d;
        return sum / (double) hMap.size();
    }

    public static <K> HashMap<K, Double> meanHash(ArrayList<HashMap<K, Double>> hList) {
        double size = hList.size();
        HashMap<K, Double> hMap = new HashMap<>();
        for (HashMap<K, Double> map : hList) {
            for (K key : map.keySet()) {
                if (hMap.containsKey(key)) {
                    double value = hMap.get(key);
                    value += map.get(key);
                    hMap.put(key, value);
                } else {
                    hMap.put(key, map.get(key));
                }
            }
        }
        return divide(hMap, size);
    }

    public static <K> HashSet<K> getKeys(ArrayList<HashMap<K, Double>> hList) {
        HashSet<K> keySet = new HashSet<>();
        for (HashMap<K, Double> map : hList)
            keySet.addAll(map.keySet());
        return keySet;
    }

    public static <K> String toString(HashMap<K, Double> hMap, Collection<K> c) {
        // TOOD
        return "";
    }

    public static <K> HashMap<K, Double> tf(HashMap<K, Double> hMap) {
        HashMap<K, Double> ret = new HashMap<>();
        double sum = sumValues(hMap);
        if (sum == 0)
            return ret;
        return divide(hMap, sum);
    }

    public static <K> HashMap<K, Double> getIdf(ArrayList<HashMap<K, Double>> dList, HashSet<K> kSet) {
        HashMap<K, Double> idfMap = new HashMap<>();
        double totalLines = dList.size();
        for (K key : kSet) {
            int sum = 0;
            for (HashMap<K, Double> hMap : dList) {
                if (hMap.containsKey(key))
                    sum++;
            }
            double idf = totalLines / (double) sum;
            idf = Math.log(idf);
            idfMap.put(key, idf);
        }
        return idfMap;
    }
}