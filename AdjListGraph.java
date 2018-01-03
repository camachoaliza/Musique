/**
 * FILE NAME: AdjListGraph.java
 * WHO: @author Stella Kakavouli, CS230 Staff
 * ALTERED BY: Aliza Camacho, Liz Borecki, Emily Mattlin
 * WHEN: December 2017
 * WHAT: Creates a graph by using adjacent lists
 * KNOWN FEATURES/BUGS: 
  * 1. It handles unweighted graphs only, but it can be extended.
  * 2. It does not handle operations involving non-existing vertices
  * 3. getSuccessor() returns the linked list of the vertex, not a clone! 
  * 4. javadoc is not complete!
  * Implements the Graph.java interface
  * @version 2017.12.18.v1.2
 */

import java.util.*;
import java.io.*;

public class AdjListGraph<T> implements Graph<T>, Iterable<T>{
  protected final int NOT_FOUND = -1;
  
  protected Vector<LinkedList<T>> arcs;   // adjacency matrices of arcs
  protected Vector<T> vertices;   // values of vertices
  
  /******************************************************************
    * Constructor. Creates an empty graph.
    ******************************************************************/
  public AdjListGraph() {
    this.arcs = new Vector<LinkedList<T>>();
    this.vertices = new Vector<T>();
  }
  
  /******************************************************************
    * Constructor. Creates a new copy of a graph.
    ******************************************************************/
  public AdjListGraph(AdjListGraph original) {
    this.vertices = (Vector<T>)original.vertices.clone();
    this.arcs = new Vector<LinkedList<T>>();
    for(int i=0; i<original.vertices.size(); i++){
      this.arcs.addElement((LinkedList<T>)((LinkedList<T>)original.arcs.get(i)).clone());
    }
    
  }
  
  /*****************************************************************
    * Creates and returns a new graph using the data found in the input file.
    * If the file does not exist, a message is printed. 
    *****************************************************************/
  
  public static void loadTGF(String tgf_file_name, AdjListGraph<String> g) throws FileNotFoundException {
    
    if (!g.isEmpty()) throw new RuntimeException("Refusing to load TGF data into non-empty graph.");
    Scanner fileReader = new Scanner(new File(tgf_file_name));
    // Keep a mapping from TGF vertex ID to AdjMatGraph vertex ID.
    // This allows vertex IDs to be written out of order in TGF.
    // It also supports non-integer vertex IDs.
    HashMap<String,Integer> vidMap = new HashMap<String,Integer>();
    try {
      // Read vertices until #
      while (fileReader.hasNext()) {
        // Get TGF vertex ID
        String nextToken = fileReader.next();
        if (nextToken.equals("#")) {
          break;
        }
        vidMap.put(nextToken, g.getNumVertices());
        String label = fileReader.hasNextLine() ? fileReader.nextLine().trim() : fileReader.next();
        
        g.addVertex(label);
      }
      
      // Read edges until EOF
      while (fileReader.hasNext()) {
        // Get src and dest
        String src = fileReader.next();
        String dest = fileReader.next();
        // Discard label if any
        if (fileReader.hasNextLine()) {
          String label = fileReader.nextLine().trim();
          if (!label.isEmpty()) System.out.println("Discarded arc label: \"" + label + "\"");
        }
        g.addArc(Integer.parseInt(src), Integer.parseInt(dest));
      }
    } catch (RuntimeException e) {
      System.out.println("Error reading TGF");
      throw e;
    } finally {
      fileReader.close();
    }
  }
  
  
  /******************************************************************
    Returns the index value of the first occurrence of the vertex.
    Returns NOT_FOUND if the key is not found.
    ******************************************************************/
  protected int getIndex(T vertex) {
    for (int i = 0; i < vertices.size(); i++) {
      if (vertices.get(i).equals(vertex)) {
        return i;
      }
    }
    return NOT_FOUND;
  }
  
  
  /******************************************************************
    * Returns true if the graph is empty and false otherwise. 
    ******************************************************************/
  public boolean isEmpty() {
    return vertices.size() == 0;
  }
  
  /******************************************************************
    * Returns the number of vertices in the graph.
    ******************************************************************/
  public int getNumVertices() { 
    return vertices.size(); 
  }
  
  
  
  /******************************************************************
    * Returns the number of arcs in the graph by counting them.
    ******************************************************************/
  public int getNumArcs() {
    int totalArcs = 0;
    for (int i = 0; i < vertices.size(); i++) //for each vertex
      //add the number of its connections
      totalArcs = totalArcs + arcs.get(i).size(); 
    
    return totalArcs; 
  }
  
  /******************************************************************
    * Returns true if this graph contains the vertex, false otherwise. 
    ******************************************************************/
  public boolean containsVertex(T vertex){
    for (int i = 0; i < vertices.size(); i++){
      if(vertices.get(i).equals(vertex))
        return true;
    }
    return false;
    
  }
  
  /******************************************************************
    * Returns true iff a directed edge exists from v1 to v2.
    ******************************************************************/
  public boolean isArc (T vertex1, T vertex2){ 
    try {
      int index = vertices.indexOf(vertex1);
      LinkedList<T> l = arcs.get(index);
      return (l.indexOf(vertex2) != -1);
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println(vertex1 + " vertex does not belong in the graph");
      return false;
    }
  }
  
  /******************************************************************
    Returns true iff an edge exists between two given vertices
    which means that two corresponding arcs exist in the graph
    ******************************************************************/
  public boolean isEdge (T vertex1, T vertex2) {
    return (isArc(vertex1, vertex2) && isArc(vertex2, vertex1)); 
  }
  
  /******************************************************************
    * Returns true IFF the graph is undirected, that is, for every 
    *    pair of nodes i,j for which there is an arc, the opposite arc
    *    is also present in the graph.  
    * An empty graph is undirected ####is that all right?????
    ******************************************************************/
  public boolean isUndirected() {
    for (int i=0; i<arcs.size(); i++) {
      for (T vertex : arcs.get(i)) {
        int index = vertices.indexOf(vertex);
        LinkedList<T> l = arcs.get(index);
        if (l.indexOf(vertices.get(i)) == -1) {
          return false;
        }
      }
    }
    return true;
  }
  
  /******************************************************************
    Adds a vertex to the graph, expanding the capacity of the graph
    if necessary.  If the vertex already exists, it does not add it.
    ******************************************************************/
  public void addVertex (T vertex) {
    if (vertices.indexOf(vertex) == NOT_FOUND) { //the vertex is not already there
      // add it to the vertices vector
      vertices.add(vertex); 
      
      //indicate that the new vertex has no arcs to other vertices yet
      arcs.add(new LinkedList<T>()); 
    }
  }
  
  
  /******************************************************************
    * Removes a single vertex with the given value from the graph.  
    * Uses equals() for testing equality
    ******************************************************************/
  public void removeVertex (T vertex) {
    int index = vertices.indexOf(vertex);
    this.removeVertex(index);
  }
  
  
  /******************************************************************
    * Retrieves the object located in the vertice with a soecific index.  
    ******************************************************************/
  public T getVertex (int index) {
    return vertices.get(index);
  }
  
  /******************************************************************
    Helper. Removes a vertex at the given index from the graph.   
    Note that this may affect the index values of other vertices.
    ******************************************************************/
  private void removeVertex (int index) {
    T vertex = vertices.get(index);
    vertices.remove(index); //remove vertex from vertices vector
    arcs.remove(index); //remove its list of adjacent vertices vector
    //remove it from the other lists, wherever it was found
    for (int i = 0; i < arcs.size(); i++) {
      for (T otherVertex : arcs.get(i)) {
        if (otherVertex.equals(vertex))
          arcs.get(i).remove(vertex);
      }
    }
  }
  
  /******************************************************************
    * Inserts an edge between two vertices of the graph.
    * If one or both vertices do not exist, ignores the addition.
    ******************************************************************/
  public void addEdge (T vertex1, T vertex2) {
    // getIndex will return NOT_FOUND if a vertex does not exist,
    // and the addArc() will not insert it
    this.addArc (vertex1, vertex2);
    addArc (vertex2, vertex1);
  }
  
  /******************************************************************
    * Inserts an arc from v1 to v2.
    * If the vertices exist, else does not change the graph. 
    ******************************************************************/
  public void addArc (T source, T destination){
    int sourceIndex = vertices.indexOf(source);
    int destinationIndex = vertices.indexOf(destination);
    
    //if source and destination exist, add the arc. do nothing otherwise
    if ((sourceIndex != -1) && (destinationIndex != -1)){
      LinkedList<T> l = arcs.get(sourceIndex);
      l.add(destination);
    }
  }
  
//  /******************************************************************
//    Helper. Inserts an edge between two vertices of the graph.
//    ******************************************************************/
  protected void addArc (int index1, int index2) {
    //if (indexIsValid(index1) && indexIsValid(index2))
    //vertices.get(index1).add(v2);
    LinkedList<T> l = arcs.get(index1-1);
    T v = vertices.elementAt(index2-1);
    l.add(v);
  }
  
  
  /******************************************************************
    * Removes an edge between two vertices of the graph.
    * If one or both vertices do not exist, ignores the removal.
    ******************************************************************/
  public void removeEdge (T vertex1, T vertex2) {
    removeArc (vertex1, vertex2);
    removeArc (vertex2, vertex1);
  }
  
  
  /******************************************************************
    * Removes an arc from vertex v1 to vertex v2,
    * if the vertices exist, else does not change the graph. 
    ******************************************************************/
  public void removeArc (T vertex1, T vertex2) {
    int index1 = vertices.indexOf(vertex1);
    int index2 = vertices.indexOf(vertex2);
    removeArc (index1, index2);
  }
  
  /******************************************************************
    * Helper. Removes an arc from index v1 to index v2.
    ******************************************************************/
  private void removeArc (int index1, int index2) {
    //if (indexIsValid(index1) && indexIsValid(index2))
    T to = vertices.get(index2);
    LinkedList<T> connections = arcs.get(index1);
    connections.remove(to);
  }
  /******************************************************************
    * Retrieve from a graph the vertices x pointing to vertex v (x->v)
    * and returns them onto a linked list
    ******************************************************************/
  public LinkedList<T> getPredecessors(T vertex){
    LinkedList<T> previous = new LinkedList<T>(); // to be returned
    //for each linked list in the arcs vector
    for (int i = 0; i < arcs.size(); i++) {
      //search for input vertex
      int index = arcs.get(i).indexOf(vertex); 
      //if found, then add predecessor to the previous list
      if (index != -1)
        previous.add(vertices.get(i)); 
    }    
    return previous;    
  }
  
  /******************************************************************
    * Retrieve from a graph the vertices x following vertex v (v->x)
    * and returns them onto a linked list
    ******************************************************************/
  public LinkedList<T> getSuccessors(T vertex) {
    int index = vertices.indexOf(vertex);
    if (index != -1)
       return arcs.get(index);
    else return null;
  }
//  
//  /******************************************************************
//    Returns a string representation of the graph. 
//    ******************************************************************/
  public String toString() {
    if (vertices.size() == 0) return "Graph is empty";
    
    String result = "Vertices: \n";
    result = result + vertices;
    
    result = result + "\n\nEdges: \n";
    for (int i=0; i< vertices.size(); i++)
      result = result + "from " + vertices.get(i) + ": "  + arcs.get(i) + "\n";
    
    return result;
  }
  
  /******************************************************************
    * Saves the current graph into a .tgf file.
    * If it cannot save the file, a message is printed. 
    *****************************************************************/
  public void saveToTGF(String fName) {
    try {
      PrintWriter writer = new PrintWriter(new File(fName));
      
      //write vertices by iterating through vector "vertices"
      for (int i = 0; i < vertices.size(); i++) {
        writer.print((i+1) + " " + vertices.get(i));
        writer.println("");
      }
      writer.print("#"); // Prepare to print the edges
      writer.println("");
      
      //write arcs by iterating through arcs vector
      for (int i = 0; i < arcs.size(); i++){ //for each linked list in arcs
        for (T vertex :arcs.get(i)) {
          int index2 = vertices.indexOf(vertex);
          writer.print((i+1) + " " + (index2+1));
          writer.println("");
        }
      }
      writer.close();
    } catch (IOException ex) {
      System.out.println("***ERROR***" +  fName + " could not be written: " + ex);
    }
  }
  
  /**
   * An iterator that iterates over the vertices of an AdjMatGraph.
   */
  private class VerticesIterator implements Iterator<T> {
    private int cursor = 0;
    
    /** Check if the iterator has a next vertex */
    public boolean hasNext() {
      return cursor < vertices.size();
    }
    
    /** Get the next vertex. */
    public T next() {
      if (cursor >= vertices.size()) {
        throw new NoSuchElementException();
      } else {
        return vertices.get(cursor++);
      }
    }
    
    /** Remove is not supported in this iterator. */
    public void remove() {
      throw new UnsupportedOperationException();
    } 
  }
  /**
   * Create a new iterator that will iterate over the vertices of the array when asked.
   * @return the new iterator.
   */
  public Iterator<T> iterator() {
    return new VerticesIterator();
  }

}
