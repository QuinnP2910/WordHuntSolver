import java.util.ArrayList;

public class TrieNode {
    public char character;
    public ArrayList<TrieNode> children = new ArrayList<>();
    public boolean isTerminal;

    public TrieNode(char character) {
        this.character = character;
    }

    public int getChildIndex(char child) {
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).character == child) {
                return i;
            }
        }
        return -1;
    }
}
