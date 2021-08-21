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
        int max = 0;
        for (String verse : verses) {
            Integer pairKey = hash(verse, firstPrime);
            Integer pairValue = hash(verse, secondPrime);
            if (result.isEmpty()) {
                result.put(new Pair<>(pairKey, pairValue), 1);
            } else {
                boolean updated = false;
                for (Pair<Integer, Integer> pair : result.keySet()) {
                    if (pair.getKey().equals(pairKey) || pair.getValue().equals(pairValue)) {
                        int incrementedValue = result.get(pair) + 1;
                        result.put(pair, incrementedValue);
                        max = Math.max(incrementedValue, max);
                        updated = true;
                    }
                }
                // Daca nu exista in tot keySet ul atunci adaug
                // NU: Daca nu exista o valoare o adaug, din cauza asta nu e if else
                if (!updated) {
                    result.put(new Pair<>(pairKey, pairValue), 1);
                }
            }
        }
        return new Pair<>(result, max);
    }

    // cred ca e mai eficient daca key e numarul de aparitii si valoarea o lista de perechi
    private static void getMaxNumberOfOccurrences(List<String> verses, Integer firstPrime, Integer secondPrime) {
        Pair<Map<Pair<Integer, Integer>, Integer>, Integer> mapAndMax =
                listToMap(verses, firstPrime, secondPrime);
        Map<Pair<Integer, Integer>, Integer> map = mapAndMax.getKey();
        Integer maxNumberOfOccurrences = mapAndMax.getValue();
        List<Pair<Integer, Integer>> maxesPairs = new ArrayList<>();
        map.forEach((k, v) -> {
            if (v.equals(maxNumberOfOccurrences)) {
                maxesPairs.add(k);
            }
        });
        for (Pair<Integer, Integer> pair : maxesPairs) {
            for (String verse : verses) {
                Integer firstHash = hash(verse, firstPrime);
                Integer secondHash = hash(verse, secondPrime);
                if (pair.getKey().equals(firstHash) || pair.getValue().equals(secondHash)) {
                    System.out.println(verse);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(pow(2, 3, 1));
        System.out.println(hash("alan da", 3));
        System.out.println(hash("alan da", 2));
        System.out.println(listToMap(Arrays.asList("salut",
                "ce faci", "salut", "ana", "ce faci", "salut", "hello", "ce faci"), 2, 3));
        getMaxNumberOfOccurrences(Arrays.asList("salut",
                "ce faci", "salut", "ana", "ce faci", "salut", "hello", "ce faci"), 2, 3);
    }
}
