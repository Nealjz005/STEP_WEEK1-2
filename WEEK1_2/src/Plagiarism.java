import java.util.*;

class PlagiarismDetector {

    // Generate n-grams
    public static Set<String> generateNGrams(String text, int n) {
        Set<String> grams = new HashSet<>();
        String[] words = text.split("\\s+");

        for (int i = 0; i <= words.length - n; i++) {
            StringBuilder gram = new StringBuilder();
            for (int j = 0; j < n; j++) {
                gram.append(words[i + j]).append(" ");
            }
            grams.add(gram.toString().trim());
        }
        return grams;
    }

    // Calculate similarity
    public static double calculateSimilarity(Set<String> set1, Set<String> set2) {
        int matchCount = 0;

        for (String gram : set1) {
            if (set2.contains(gram)) {
                matchCount++;
            }
        }

        return (double) matchCount / Math.max(set1.size(), set2.size());
    }

    public static void main(String[] args) {
        String doc1 = "this is a simple plagiarism detection example using java";
        String doc2 = "this is a plagiarism detection example written in java";

        int n = 3; // 3-grams

        Set<String> grams1 = generateNGrams(doc1, n);
        Set<String> grams2 = generateNGrams(doc2, n);

        double similarity = calculateSimilarity(grams1, grams2);

        System.out.println("Matching similarity: " + (similarity * 100) + "%");

        if (similarity > 0.6) {
            System.out.println("PLAGIARISM DETECTED");
        } else {
            System.out.println("No plagiarism");
        }
    }
}