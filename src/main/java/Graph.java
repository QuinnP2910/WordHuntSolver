import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Graph {
    ArrayList<Vertex> vertices = new ArrayList<>();
    HashSet<String> dictionary;
    Trie trie;

    ArrayList<WordPath> validWords = new ArrayList<>();

    public Graph() {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("src\\main\\resources\\secondary_words_dictionary.json")) {
            JSONArray jsonObject = (JSONArray) parser.parse(reader);
            dictionary = new HashSet<String>(jsonObject);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        trie = new Trie(new TrieNode('!'));
        for (String word : dictionary) {
            trie.insertString(word.toLowerCase(), trie.root);
        }
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    public Vertex getVertex(int id) {
        for (Vertex vertex : vertices) {
            if (vertex.id == id) {
                return vertex;
            }
        }
        System.out.println("Vertex with id " + id + " not found");
        return null;
    }

    public void analyzePossibleWords(Vertex vertex) {
        System.out.println("Analyzing possible words...");
        for (Edge possibleEdge : vertex.connections) {
            analyzePossibleWords(possibleEdge, new ArrayList<>(List.of(vertex)), new ArrayList<>(List.of(new Edge('s', vertex))));
        }
    }

    private void analyzePossibleWords(Edge edge, ArrayList<Vertex> visitedNodes, ArrayList<Edge> path) {
        Vertex vertex = edge.endVertex;
        ArrayList<Vertex> visitedNodesCopy = new ArrayList<>(visitedNodes);
        ArrayList<Edge> pathCopy = new ArrayList<>(path);

        visitedNodesCopy.add(vertex);
        pathCopy.add(edge);
        String currentWord = visitedNodesToString(visitedNodesCopy).toLowerCase();
        if (!trie.startsWith(currentWord)) {
            return;
        }

        if (currentWord.length() > 3 && dictionary.contains(currentWord) && !validWords.contains(currentWord)) {
            validWords.add(new WordPath(currentWord, pathCopy));
        }
        for (Edge possibleEdge : vertex.connections) {
            if (!visitedNodes.contains(possibleEdge.endVertex)) {
                analyzePossibleWords(possibleEdge, visitedNodesCopy, pathCopy);
            }
        }
    }

    public String visitedNodesToString(ArrayList<Vertex> visitedNodes) {
        String wordString = "";
        for (Vertex vertex :
                visitedNodes) {
            wordString = wordString.concat(String.valueOf(vertex.key));
        }
        return wordString;
    }

    public void printValidWords() {
        validWords.sort((primaryWord, secondaryWord) -> {
            int firstComparator = primaryWord.word.length() - secondaryWord.word.length();
            return firstComparator == 0 ? secondaryWord.word.compareTo(primaryWord.word) : firstComparator;
        });
        for (WordPath wordPath :
                validWords) {
            System.out.println(wordPath.word + " length:" + wordPath.word.length());
            boolean firstCharacter = false;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    boolean foundCharacter = false;
                    for (Edge edge :
                            wordPath.path) {
                        if (edge.endVertex.id == Main.rowAndColumnToID(i, j)) {
                            int edgeIndex = wordPath.path.indexOf(edge);
                            System.out.print(edgeIndex != wordPath.path.size() - 1 ? wordPath.path.get(edgeIndex + 1).direction : "o");
                            foundCharacter = true;
                            if (edgeIndex == 0) {
                                firstCharacter = true;
                            }
                        }
                    }
                    if (!foundCharacter) {
                        System.out.print("+");
                    }
                    if (!firstCharacter) {
                        System.out.print(" ");
                    } else {
                        System.out.print("!");
                        firstCharacter = false;
                    }
                }
                System.out.println();
            }
        }
    }

    @Override
    public String toString() {
        String graphString = "";
        for (Vertex vertex : vertices) {
            graphString = graphString.concat(vertex.toString());
            if (vertices.indexOf(vertex) != vertices.size() - 1) {
                graphString = graphString.concat("\n");
            }
        }
        return graphString;
    }
}

class WordPath {
    String word;
    ArrayList<Edge> path;

    public WordPath(String word, ArrayList<Edge> path) {
        this.word = word;
        this.path = path;
    }
}