import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        System.out.println("Input all characters from left to right in one continuous string");
        Scanner input = new Scanner(System.in);
        String characters = input.nextLine();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                char letter = characters.charAt((i * 4) + j);
                graph.addVertex(new Vertex(letter, rowAndColumnToID(i, j)));
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Vertex vertex = graph.getVertex(rowAndColumnToID(i, j));
                addIfValid(i - 1, j - 1, '\\', vertex, graph);
                addIfValid(i - 1, j, '|', vertex, graph);
                addIfValid(i - 1, j + 1, '/', vertex, graph);
                addIfValid(i, j - 1, '<', vertex, graph);
                addIfValid(i, j + 1, '>', vertex, graph);
                addIfValid(i + 1, j - 1, '/', vertex, graph);
                addIfValid(i + 1, j, '|', vertex, graph);
                addIfValid(i + 1, j + 1, '\\', vertex, graph);
            }
        }

        for (Vertex vertex :
                graph.vertices) {
            graph.analyzePossibleWords(vertex);
        }

        graph.printValidWords();
    }

    public static void addIfValid(int r, int c, char direction, Vertex letter, Graph graph) {
        if (r <= 3 && r >= 0 && c <= 3 && c >= 0) {
            letter.addConnection(new Edge(direction, graph.getVertex(rowAndColumnToID(r, c))), false);
        }
    }

    public static int rowAndColumnToID(int r, int c) {
        return Integer.parseInt(Integer.toString(r).concat(Integer.toString(c)));
    }
}
