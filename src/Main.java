import javafx.util.Pair;

import java.util.*;

class Main {
    private static int hash(String lyric, Integer prime) {
        int sum = 0;
        char[] lyricAsChar = lyric.toCharArray();
        for (int i = 0; i < lyric.length(); i++) {
            sum += (lyricAsChar[i] % 64 * (prime * (i + 1))) % Integer.MAX_VALUE;
            sum %= Integer.MAX_VALUE;
        }
        return sum;
    }
    private static Pair<Map<Pair<Integer, Integer>, Integer>, Integer> listToMap(List<String> lyrics,
                                                                                 Integer firstPrime,
                                                                                 Integer secondPrime) {
        Map<Pair<Integer, Integer>, Integer> result = new HashMap<>();
        int maxNumberOfOccurrences = 0;
        for (String lyric : lyrics) {
            Pair<Integer, Integer> pair = new Pair<>(hash(lyric, firstPrime), hash(lyric, secondPrime));
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
    private static List<String> getMaxNumberOfOccurrences(List<String> lyrics, Integer firstPrime, Integer secondPrime) {
        Pair<Map<Pair<Integer, Integer>, Integer>, Integer> mapMaxPair = listToMap(lyrics, firstPrime, secondPrime);
        Map<Pair<Integer, Integer>, Integer> map = mapMaxPair.getKey();
        Integer maxNumberOfOccurrences = mapMaxPair.getValue();

        List<Pair<Integer, Integer>> maxNumberOfOccurrenceHashes = new ArrayList<>();
        List<String> result = new ArrayList<>();
        map.forEach((k, v) -> {
            if (v.equals(maxNumberOfOccurrences)) {
                maxNumberOfOccurrenceHashes.add(k);
            }
        });
        lyrics.forEach(lyric -> {
            Pair<Integer, Integer> pair = new Pair<>(hash(lyric, firstPrime), hash(lyric, secondPrime));
            if (maxNumberOfOccurrenceHashes.contains(pair)) {
                result.add(lyric);
                maxNumberOfOccurrenceHashes.remove(pair);
            }
        });
        return result;
    }

    private static String maxLength(List<String> lyrics) {
        List<String> results = getMaxNumberOfOccurrences(lyrics, 2, 3);
        int max = -1, index = 0;
        for (int i = 0; i < results.size(); i++) {
            String result = results.get(i);
            if (result.length() >= max) {
                max = result.length();
                index = i;
            }
        }
        return results.get(index);
    }

    public static void main(String[] args) {
        System.out.println(maxLength(Arrays.asList("A vacation in the foreign land",
                "Uncle Sam does the best he can",
                "You're in the army now",
                "Oh, oh you're in the army, now",
                "Now you remember what the draft man said",
                "Nothing to do all day but stay in bed",
                "You're in the army now",
                "Oh, oh you're in the army, now",
                "You'll be a hero of the neighborhood",
                "Nobody knows that you left for good",
                "You're in the army now",
                "Oh, oh you're in the army, now",
                "Smiling faces on the way to 'Nam",
                "But once you get there no one gives a damn",
                "You're in the army now",
                "Oh, oh you're in the army, now",
                "Hand grenades flying over your head",
                "The sun's flying over your head",
                "If you want to survive you're out of bet",
                "You're in the army now",
                "Oh, oh you're in the army, now",
                "Shots ring out in the dead of night",
                "The sergeant calls : ",
                "Stand up and fight!",
                "You're in the army now",
                "Oh, oh you're in the army, now",
                "You've got your orders to shoot on sight",
                "your finger's on the trigger but it don't seem right",
                "You're in the army now",
                "Oh, oh you're in the army, now",
                "Night is falling and you just can't see",
                "Is this illusion or reality",
                "You're in the army now",
                "Oh, oh you")));
    }
}
