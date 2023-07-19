import java.util.ArrayList;
import java.util.Collections;
public class Vertex implements Comparable {
    char key;
    int id;
    ArrayList<Edge> connections = new ArrayList<>();
    public Vertex(char key) {
        this(key, 0);
    }
    public Vertex(char key, int id) {
        this.key = key;
        this.id = id;
    }
    public void addConnection(Vertex vertex) {
        addConnection(new Edge(vertex));
    }
    public void addConnection(Vertex vertex, boolean addCounterpart) {
        addConnection(new Edge(vertex), addCounterpart);
    }
    public void addConnection(Edge edge) {
        addConnection(edge, true);
    }
    public void addConnection(Edge edge, boolean addCounterpart) {
        connections.add(edge);
        Collections.sort(connections);
        if (addCounterpart) {
            edge.endVertex.addConnection(new Edge(edge.direction, this), false);
        }
    }

    @Override
    public String toString() {
        String vertexString = String.valueOf(key) + id;
        if (connections.size() != 0) {
            vertexString = vertexString.concat(" with connections: ");
            for (Edge edge :
                    connections) {
                vertexString =
                        vertexString.concat(String.valueOf(edge.endVertex.key));
            }
        }
        return vertexString;
    }

    @Override
    public int compareTo(Object o) {
        Vertex vertexToCompare = (Vertex) o;
        return Character.compare(key, vertexToCompare.key);
    }
}
