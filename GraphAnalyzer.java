// List at each vertex corresponds to the vertices adjacent to vertex
private List<some type> adjList;

// Value of true represents an edge between two vertices.
private boolean[][] adjMatrix;

/*** Methods for the Graph class ***/

// Entry point to run operations specified in the handout.
go()

/**
 * Finds the source vertex within the graph.
 * @throws IllegalArgumentException if the source vertex is not found in the graph.
 */
findSource() throws IllegalArgumentException

/**
 * Finds the destination vertex within the graph.
 * @throws IllegalArgumentException if the destination vertex is not found in the graph.
 */
findDestination() throws IllegalArgumentException

/**
 * Performs a depth-first search a source to destination or until the search is exhausted.
 * Details not specified as that is TBD by each team
 */
depthFirstSearch()

/**
 * Performs an exhaustive breadth-first search starting from a source vertex.
 * Details not specified as that is TBD by each team
 */
breadthFirstSearch()

/**
 * Performs a search for cycles. This only needs to work on directed graphs.
 * Details not specified as that is TBD by each team
 */
cycleSearch()

// Performs the transitive closure algorithm using an adjacency matrix
transitiveClosure()

// Constructs information as specified in the handout.
public String getGraphStats(List<???> dfs, List<???> bfs, List<???> tClosure)
