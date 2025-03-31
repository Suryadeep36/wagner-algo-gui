package org.example.spellcheckalgorithm;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WagnerFischerAlgo {
    // Load dictionary from a file
    public static List<String> loadDictionary(String filePath) {
        List<String> dictionary = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dictionary.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println("Error reading the dictionary file: " + e.getMessage());
        }
        return dictionary;
    }

    // Wagner-Fischer algorithm for calculating edit distance
    public static int wagnerFischer(String s1, String s2) {
        int lenS1 = s1.length();
        int lenS2 = s2.length();

        if (lenS1 > lenS2) {
            String temp = s1;
            s1 = s2;
            s2 = temp;
            int tempLen = lenS1;
            lenS1 = lenS2;
            lenS2 = tempLen;
        }

        int[] currentRow = new int[lenS1 + 1];
        for (int i = 0; i <= lenS1; i++) {
            currentRow[i] = i; // Initialize base case
        }

        for (int i = 1; i <= lenS2; i++) {
            int[] previousRow = currentRow.clone();
            currentRow[0] = i;

            for (int j = 1; j <= lenS1; j++) {
                int add = previousRow[j] + 1;
                int delete = currentRow[j - 1] + 1;
                int change = previousRow[j - 1];
                if (s1.charAt(j - 1) != s2.charAt(i - 1)) {
                    change += 1;
                }
                currentRow[j] = Math.min(Math.min(add, delete), change);
            }
        }
        return currentRow[lenS1];
    }

    // Spell check method to find top 10 suggestions
    public static List<String> spellCheck(String misspelledWord) {
        List<WordDistance> suggestions = new ArrayList<>();
        String filePath = "/Users/gohilsuryadeepsinh/Desktop/Web Development /Java Serverlet/Spell-Check-Algorithm/src/main/java/org/example/spellcheckalgorithm/words.txt";
        List<String> dictionary = loadDictionary(filePath);
        for (String correctWord : dictionary) {
            correctWord = correctWord.toLowerCase();
            int distance = wagnerFischer(misspelledWord, correctWord);
            suggestions.add(new WordDistance(correctWord, distance));
        }

        // Sort the suggestions by edit distance
        Collections.sort(suggestions, Comparator.comparingInt(WordDistance::getDistance));

        // Collect the top 10 closest matches
        List<String> topSuggestions = new ArrayList<>();
        for (int i = 0; i < Math.min(10, suggestions.size()); i++) {
            topSuggestions.add(suggestions.get(i).getWord() + " (Distance: " + suggestions.get(i).getDistance() + ")");
        }
        return topSuggestions;
    }
}

// Helper class to store a word and its edit distance
class WordDistance {
    private final String word;
    private final int distance;

    public WordDistance(String word, int distance) {
        this.word = word;
        this.distance = distance;
    }

    public String getWord() {
        return word;
    }

    public int getDistance() {
        return distance;
    }
}