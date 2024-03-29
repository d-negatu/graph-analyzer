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
}

enum VertexState {
    VISITED, UNVISITED, PROCESSING;
}

