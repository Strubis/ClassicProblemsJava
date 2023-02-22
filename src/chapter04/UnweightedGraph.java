package chapter04;

import java.util.List;

import chapter02.GenericSearch;
import chapter02.GenericSearch.*;

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
	
	public static void main(String[] args) {
		// Representando as 15 maiores MSAs do USA
		UnweightedGraph<String> cityGraph = new UnweightedGraph<>(
				List.of("Seattle", "San Francisco", "Los Angeles", "Riverside", "Phoenix", 
						"Chicago", "Boston", "New York", "Atlanta","Miami", "Dallas", 
						"Houston", "Detroit", "Philadelphia", "Washington"));
		
		cityGraph.addEdge("Seattle", "Chicago");
		cityGraph.addEdge("Seattle", "San Francisco");
		cityGraph.addEdge("San Francisco", "Riverside");
		cityGraph.addEdge("San Francisco", "Los Angeles");
		cityGraph.addEdge("Los Angeles", "Riverside");
		cityGraph.addEdge("Los Angeles", "Phoenix");
		cityGraph.addEdge("Riverside", "Phoenix");
		cityGraph.addEdge("Riverside", "Chicago");
		cityGraph.addEdge("Phoenix", "Dallas");
		cityGraph.addEdge("Phoenix", "Houston");
		cityGraph.addEdge("Dallas", "Chicago");
		cityGraph.addEdge("Dallas", "Atlanta");
		cityGraph.addEdge("Dallas", "Houston");
		cityGraph.addEdge("Houston", "Atlanta");
		cityGraph.addEdge("Houston", "Miami");
		cityGraph.addEdge("Atlanta", "Chicago");
		cityGraph.addEdge("Atlanta", "Washington");
		cityGraph.addEdge("Atlanta", "Miami");
		cityGraph.addEdge("Miami", "Washington");
		cityGraph.addEdge("Chicago", "Detroit");
		cityGraph.addEdge("Detroit", "Boston");
		cityGraph.addEdge("Detroit", "Washington");
		cityGraph.addEdge("Detroit", "New York");
		cityGraph.addEdge("Boston", "New York");
		cityGraph.addEdge("New York", "Philadelphia");
		cityGraph.addEdge("Philadelphia", "Washington");
		
		System.out.println(cityGraph.toString());
		
		Node<String> bfsResult = GenericSearch.bfs("Boston", v -> v.equals("Miami"), cityGraph::neighborsOf);
		
		if(bfsResult == null) {
			System.out.println("Sem solucao possivel!");
		}else {
			List<String> path = GenericSearch.nodeToPath(bfsResult);
			System.out.println("Menor caminho de Boston para Miami:");
			System.out.println(path);
		}
	}
}
