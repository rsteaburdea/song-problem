import javafx.util.Pair;

import java.util.*;

class Main {
    private static int pow(int n, int p, int acc) {
        return p == 0 ? acc : pow(n, --p, acc * n);
    }
    private static int hash(String verse, Integer prime) {
        int sum = 0;
        char[] verseAsChar = verse.toCharArray();
        for (int i = 0; i < verse.length(); i++) {
            sum += verseAsChar[i] % 65 * pow(prime, i + 1, 1);
        }
        return sum % Integer.MAX_VALUE;
    }
    private static Pair<Map<Pair<Integer, Integer>, Integer>, Integer> listToMap(List<String> verses,
                                                                                 Integer firstPrime,
                                                                                 Integer secondPrime) {
        Map<Pair<Integer, Integer>, Integer> result = new HashMap<>();
        int maxNumberOfOccurrences = 0;
        for (String verse : verses) {
            Pair<Integer, Integer> pair = new Pair<>(hash(verse, firstPrime), hash(verse, secondPrime));
            if (!result.containsKey(pair)) {
                result.put(pair, 1);
            } else {
                int incrementedValue = result.get(pair) + 1;
                result.put(pair, incrementedValue);
                maxNumberOfOccurrences = Math.max(incrementedValue, maxNumberOfOccurrences);
            }
        }
        return new Pair<>(result, maxNumberOfOccurrences);
    }

    // cred ca e mai eficient daca key e numarul de aparitii si valoarea o lista de perechi
    private static void printMaxNumberOfOccurrences(List<String> verses, Integer firstPrime, Integer secondPrime) {
        Pair<Map<Pair<Integer, Integer>, Integer>, Integer> mapMaxPair = listToMap(verses, firstPrime, secondPrime);
        Map<Pair<Integer, Integer>, Integer> map = mapMaxPair.getKey();
        Integer maxNumberOfOccurrences = mapMaxPair.getValue();

        List<Pair<Integer, Integer>> maxNumberOfOccurrenceHashes = new ArrayList<>();

        map.forEach((k, v) -> {
            if (v.equals(maxNumberOfOccurrences)) {
                maxNumberOfOccurrenceHashes.add(k);
            }
        });
        verses.forEach(verse -> {
            Pair<Integer, Integer> pair = new Pair<>(hash(verse, firstPrime), hash(verse, secondPrime));
            if (maxNumberOfOccurrenceHashes.contains(pair)) {
                System.out.println(verse);
                maxNumberOfOccurrenceHashes.remove(pair);
            }
        });
    }

    public static void main(String[] args) {
        System.out.println(pow(2, 3, 1));
        System.out.println(hash("alan da", 3));
        System.out.println(hash("alan da", 2));
        System.out.println(listToMap(Arrays.asList("salut",
                "ce faci", "salut", "ana", "ce faci", "salut", "hello", "ce faci", "ana"), 2, 3));
        printMaxNumberOfOccurrences(Arrays.asList("salut",
                "ce faci", "salut", "ana", "ce faci", "salut", "hello", "ce faci", "ana"), 2, 3);
    }
}
