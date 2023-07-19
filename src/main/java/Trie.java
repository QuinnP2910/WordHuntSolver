public class Trie {
    public TrieNode root;

    public Trie(TrieNode root) {
        this.root = root;
    }

    public TrieNode insertString(String toInsert, TrieNode node) {
        if (toInsert.length() == 0) {
            TrieNode terminal = new TrieNode('#');
            terminal.isTerminal = true;
            node.children.add(terminal);
            return node;
        }
        if (!node.children.isEmpty()) {
            for (int i = 0; i < node.children.size(); i++) {
                if (toInsert.charAt(0) == node.children.get(i).character) {
                    node.children.set(i, insertString(toInsert.substring(1),
                            node.children.get(i)));
                    return node;
                }
            }
        }

        node.children.add(insertString(toInsert.substring(1), new
                TrieNode(toInsert.charAt(0))));
        return node;
    }

    public boolean startsWith(String prefix) {
        return startsWith(prefix, root);
    }

    public boolean startsWith(String prefix, TrieNode node) {
        if (prefix.length() == 0) {
            return true;
        }
        int characterIndex = node.getChildIndex(prefix.charAt(0));
        if (characterIndex == -1) {
            return false;
        } else {
            return startsWith(prefix.substring(1), node.children.get(characterIndex));
        }
    }
}
