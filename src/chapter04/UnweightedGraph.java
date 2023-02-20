package chapter04;

import java.util.List;

/**
 * Grafo sem peso
 * 
 * Grafos sem peso, sao grafos que nao possuem um valor associado
 * a sua aresta.
 * 
 * @author Emerson
 * @since 2023
 * @see David Kopec - Classic Computer Science Problems in Java
 * */
public class UnweightedGraph<V> extends Graph<V, Edge>{
	
	public UnweightedGraph(List<V> vertices) {
		super(vertices);
	}
	
	// Um grafo sem direcao, o caminho eh de ida e volta
	public void addEdge(Edge edge) {
		edges.get(edge.u).add(edge);
		edges.get(edge.v).add(edge.reversed());
	}
	
	// Add uma aresta usando o indice do vertice
	public void addEdge(int u, int v) {
		addEdge(new Edge(u, v));
	}
	
	// Add uma aresta olhando os indices dos vertices
	public void addEdge(V first, V second) {
		addEdge(new Edge(indexOf(first), indexOf(second)));
	}
}
