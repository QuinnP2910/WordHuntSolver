public class Edge implements Comparable {
    char direction;
    Vertex endVertex;
    public Edge(Vertex endVertex) {
        this('!', endVertex);
    }
    public Edge(char direction, Vertex endVertex) {
        this.direction = direction;
        this.endVertex = endVertex;
    }
    @Override
    public int compareTo(Object o) {
        Edge edgeToCompare = (Edge) o;
        return Integer.compare(endVertex.key, edgeToCompare.endVertex.key);
    }
}
