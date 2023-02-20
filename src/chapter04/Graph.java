package chapter04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Grafo
 * 
 * V eh o tipo de vertices do grafo
 * E eh o tipo de arestas
 * 
 * @author Emerson
 * @since 2023
 * @see David Kopec - Classic Computer Science Problems in Java
 * */
public abstract class Graph<V, E extends Edge> {

	private ArrayList<V> vertices = new ArrayList<>();
	protected ArrayList<ArrayList<E>> edges = new ArrayList<>();
	
	Graph(){
		
	}
	
	Graph(List<V> vertices){
		this.vertices.addAll(vertices);
		
		for(V vertex : vertices) {
			edges.add(new ArrayList<>());
		}
	}
	
	// Numero de vertices
	public int getVertexCount() {
		return vertices.size();
	}
	
	// Numero de arestas (edges)
	public int getEdgeCount() {
		return edges.stream().mapToInt(ArrayList::size).sum();
	}
	
	// Adiciona um vertice para o grafo e retorna seu indice
	public int addVertex(V vertex) {
		vertices.add(vertex);
		edges.add(new ArrayList<>());
		
		return getVertexCount() - 1;
	}
	
	// Encontra o vertice de um dado indice
	public V vertexAt(int index) {
		return vertices.get(index);
	}
	
	// Encontra o indice de um dado vertice do grafo
	public int indexOf(V vertex) {
		return vertices.indexOf(vertex);
	}
	
	// Encontra os vizinhos de um dado vertice
	public List<V> neighborsOf(int index){
		return edges.get(index).stream().map(edge -> vertexAt(edge.v)).collect(Collectors.toList());
	}
	
	// Olha o indice de um vertice e encontra seus vizinhos
	public List<V> neighborsOf(V vertex){
		return neighborsOf(indexOf(vertex));
	}
	
	// Retorna todas as arestas associadas com o indice do vertice
	public List<E> edgesOf(int index){
		return edges.get(index);
	}
	
	// Olha o indice de um vertice e retorna suas arestas
	public List<E> edgesOf(V vertex){
		return edgesOf(indexOf(vertex));
	}
	
	// Imprime o Grafo
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < getVertexCount(); i++) {
			sb.append(vertexAt(i));
			sb.append(" -> ");
			sb.append(Arrays.toString(neighborsOf(i).toArray()));
			sb.append(System.lineSeparator());
		}
		
		return sb.toString();
	}
}
