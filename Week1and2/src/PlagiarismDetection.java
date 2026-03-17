import java.util.*;

public class PlagiarismDetection {

    public static void main(String[] args) {

        HashMap<String, Set<String>> ngramMap = new HashMap<>();

        String doc1 = "this is a simple plagiarism detection system example";
        String doc2 = "this is a simple plagiarism detection example code";
        String doc3 = "java programming language is powerful and simple";

        String[] docs = {doc1, doc2, doc3};
        String[] names = {"doc1", "doc2", "doc3"};

        int n = 3;

        for (int i = 0; i < docs.length; i++) {
            String[] words = docs[i].split(" ");
            for (int j = 0; j <= words.length - n; j++) {
                String ngram = "";
                for (int k = 0; k < n; k++) {
                    ngram += words[j + k] + " ";
                }
                ngram = ngram.trim();

                ngramMap.putIfAbsent(ngram, new HashSet<>());
                ngramMap.get(ngram).add(names[i]);
            }
        }

        String newDoc = "this is a simple plagiarism detection system";
        String[] words = newDoc.split(" ");

        HashMap<String, Integer> matchCount = new HashMap<>();

        int totalNgrams = 0;

        for (int i = 0; i <= words.length - n; i++) {
            String ngram = "";
            for (int k = 0; k < n; k++) {
                ngram += words[i + k] + " ";
            }
            ngram = ngram.trim();
            totalNgrams++;

            if (ngramMap.containsKey(ngram)) {
                for (String doc : ngramMap.get(ngram)) {
                    matchCount.put(doc, matchCount.getOrDefault(doc, 0) + 1);
                }
            }
        }

        for (String doc : matchCount.keySet()) {
            int matches = matchCount.get(doc);
            double similarity = (matches * 100.0) / totalNgrams;
            System.out.println(doc + " similarity: " + similarity + "%");
        }
    }
}