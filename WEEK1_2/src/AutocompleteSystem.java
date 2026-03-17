import java.util.*;

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEnd = false;
    int frequency = 0;
}

class AutocompleteSystem {
    private TrieNode root = new TrieNode();
    private Map<String, Integer> freqMap = new HashMap<>();

    // Insert word into Trie
    public void insert(String word, int freq) {
        TrieNode node = root;

        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }

        node.isEnd = true;
        node.frequency = freq;
        freqMap.put(word, freq);
    }

    // DFS to collect words
    private void dfs(TrieNode node, String prefix, List<String> results) {
        if (node.isEnd) {
            results.add(prefix);
        }

        for (char c : node.children.keySet()) {
            dfs(node.children.get(c), prefix + c, results);
        }
    }

    // Search suggestions
    public List<String> search(String prefix) {
        TrieNode node = root;

        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return new ArrayList<>();
            }
            node = node.children.get(c);
        }

        List<String> results = new ArrayList<>();
        dfs(node, prefix, results);

        // Sort by frequency (descending)
        results.sort((a, b) -> freqMap.get(b) - freqMap.get(a));

        return results.size() > 5 ? results.subList(0, 5) : results;
    }

    public static void main(String[] args) {
        AutocompleteSystem system = new AutocompleteSystem();

        system.insert("tutorial", 1234);
        system.insert("top", 900);
        system.insert("topic", 800);
        system.insert("tool", 700);
        system.insert("tournament", 600);
        system.insert("today", 500);

        System.out.println("Suggestions for 'to': " + system.search("to"));
    }
}