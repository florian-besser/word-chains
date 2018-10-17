import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class WordChain {

    private Map<Integer, Map<String, Integer>> dictionaries = new HashMap<>();

    public WordChain() throws IOException {
        File f = new File("src/main/resources/words_alpha.txt");
        List<String> lines = FileUtils.readLines(f, "UTF-8");
        for (String line : lines) {
            int length = line.length();
            dictionaries.computeIfAbsent(length, k -> new HashMap<>());
            dictionaries.get(length).put(line, Integer.MAX_VALUE);
        }
    }

    public String[] findSteps(String from, String to) {

        Stack<String> strings = new Stack<>();
        Map<String, Integer> dictionary = dictionaries.get(from.length());
        if (dictionary.get(from) == Integer.MAX_VALUE) {
            long startBuilding = System.currentTimeMillis();
            dictionary.put(from, 0);
            strings.push(from);
            while (!strings.empty()) {
                String s = strings.pop();
                char[] c1 = s.toCharArray();
                for (Map.Entry<String, Integer> entry : dictionary.entrySet()) {
                    char[] c2 = entry.getKey().toCharArray();
                    int diffCount = getDiffCount(c1, c2);
                    Integer value = entry.getValue();
                    if (diffCount == 1 && dictionary.get(s) + 1 < value) {
                        entry.setValue(dictionary.get(s) + 1);
                        // dictionary.put(entry.getKey(), Math.min(dictionary.get(s) + 1, entry.getValue()));
                        strings.push(entry.getKey());
                    }
                }
            }
            System.out.println("Building data structure took " + (System.currentTimeMillis() - startBuilding) + "ms");
        }

        long reverseSearch = System.currentTimeMillis();

        boolean reversed = dictionary.get(to) < dictionary.get(from);
        if (reversed) {
            strings.push(from);
        } else {
            strings.push(to);
        }
        ArrayList<String> result = new ArrayList<>();
        while (!strings.empty()) {
            String s = strings.pop();
            result.add(s);
            if (reversed && s.equals(to) || !reversed && s.equals(from)) {
                break;
            }
            Integer currentValue = dictionary.get(s);
            char[] c1 = s.toCharArray();
            for (Map.Entry<String, Integer> entry : dictionary.entrySet()) {
                String key = entry.getKey();
                if (entry.getValue() == currentValue - 1) {
                    int diffCount = getDiffCount(c1, key.toCharArray());
                    if (diffCount == 1) {
                        // Reachable
                        strings.push(key);
                        break;
                    }
                }
            }
        }

        System.out.println("Searching data structure took " + (System.currentTimeMillis() - reverseSearch) + "ms");

        if (!reversed) {
            Collections.reverse(result);
        }
        return result.toArray(new String[]{});
    }

    private int getDiffCount(char[] c1, char[] c2) {
        if (c1.length != c2.length) {
            return Integer.MAX_VALUE;
        }
        int diffCount = 0;
        for (int i = 0; i < c1.length; i++) {
            if (c1[i] != c2[i]) {
                diffCount++;
            }
        }
        return diffCount;
    }


}
