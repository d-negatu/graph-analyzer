import java.util.Objects;
public class Vertex<E> {
    private E id;
    private VertexState state;

    public Vertex(E id) {
        this.id = id;
        this.state = VertexState.UNVISITED;
    }

    // Getter for id
    public E getId() {
        return id;
    }

    // Setter for id
    public void setId(E id) {
        this.id = id;
    }

    // Getter for state
    public VertexState getState() {
        return state;
    }

    // Setter for state
    public void setState(VertexState state) {
        this.state = state;
    }
    
    @Override
    public String toString() {
        return (id.toString() + "\n");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vertex<?> vertex = (Vertex<?>) obj;
        return Objects.equals(id, vertex.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

enum VertexState {
    VISITED, UNVISITED, PROCESSING;
}

