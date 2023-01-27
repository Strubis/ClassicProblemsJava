package chapter02;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Algoritmos de busca generica, utilizando o objeto generico T
 * 
 * @author Emerson
 * @since 2023
 * @see David Kopec - Classic Computer Science Problems in Java
 * */
public class GenericSearch {
	
	// Busca Linear
	static <T extends Comparable<T>> boolean linearContains(List<T> list, T key) {
		for(T item : list) {
			if(item.compareTo(key) == 0) {
				// Encontrou nossa chave
				return true;
			}
		}
		
		return false;
	}
	
	// Classe Node que sera utilizada para melhorar os algoritmos de busca
	static class Node<T> implements Comparable<Node<T>>{
		final T state;
		
		// Caso um no nao tenha pai, utilizaremos um sentinela para indicar isso
		Node<T> parent;
		double cost, heuristic;
		
		// Para os algoritmos DFS e BFS nao usamos custo e heuristica
		Node(T state, Node<T> parent){
			this.state = state;
			this.parent = parent;
		}
		
		// Para o algoritmo de A*
		Node(T state, Node<T> parent, double cost, double heuristic){
			this.state = state;
			this.parent = parent;
			this.cost = cost;
			this.heuristic = heuristic;
		}

		@Override
		public int compareTo(Node<T> o) {
			Double mine = cost + heuristic;
			Double theirs = o.cost + o.heuristic;
			return mine.compareTo(theirs);
		}
	}
	
	// Busca em Profundidade -> DFS
	static <T> Node<T> dfs(T initial, Predicate<T> goalTest, Function<T, List<T>> successors){
		// frontier sao lugares nao visitados ainda
		Stack<Node<T>> frontier = new Stack<>();
		frontier.push(new Node<>(initial, null));
		
		// explored sao lugares ja visitados
		Set<T> explored = new HashSet();
		explored.add(initial);
		
		// continua procurando enquanto ha lugar
		while(!frontier.isEmpty()) {
			Node<T> currentNode = frontier.pop();
			T currentState = currentNode.state;
		
			// Se encontrarmos o objetivo, entao retornamos ele
			if(goalTest.test(currentState)) {
				return currentNode;
			}
			
			// Checa para onde podemos ir
			for(T child : successors.apply(currentState)) {
				if(explored.contains(child)) {
					continue; // pula o filho ja explorado
				}
				
				explored.add(child);
				frontier.push(new Node<>(child, currentNode));
			}
		}
		
		return null; // Procuramos e nao foi encontrado
	}
	
	// Caminho do no
	static <T> List<T> nodeToPath(Node<T> node){
		List<T> path = new ArrayList<>();
		path.add(node.state);
		
		// Faz o caminho do fim para o inicio
		while(node.parent != null) {
			node = node.parent;
			path.add(0, node.state); // add para o inicio
		}
		
		return path;
	}
	
	// Busca Binaria, assumindo que a lista esta ordenada
	static <T extends Comparable<T>> boolean binaryContains(List<T> list, T key) {
		int low = 0;
		int high = list.size() - 1;
		
		// Enquanto houver espaco para busca
		while(low <= high) {
			int middle = (low + high) / 2;
			int comparison = list.get(middle).compareTo(key);
			
			if(comparison < 0) {
				// O elemento do meio e menor que o procurado
				low = middle + 1;
			}else if(comparison > 0) {
				// O elemento do meio e maior que o procurado
				high = middle - 1;
			}else {
				// Encontrou a chave
				return true;
			}
		}
		
		return false;
	}

	public static void main(String[] args) {
		System.out.println("Busca Linear: "
				+ "\n\tElementos: 1, 4, 82, 20, 41, 18, 27 "
				+ "\n\tProcurado: 18 "
				+ "\n\tEncontrou? " + linearContains(List.of(1, 4, 82, 20, 41, 18, 27), 18));
		
		System.out.println("\nBusca Binaria: "
				+ "\n\tElementos: a, z, f, x, w, b, y "
				+ "\n\tProcurado: g "
				+ "\n\tEncontrou? " + binaryContains(List.of('a', 'z', 'f', 'x', 'w', 'b', 'y'), 'g'));
		
		System.out.println("\nBusca Binaria: "
				+ "\n\tElementos: \"cat\", \"dog\", \"cow\", \"duck\", \"horse\", \"bird\", \"turtle\" "
				+ "\n\tProcurado: \"fish\" "
				+ "\n\tEncontrou? " + binaryContains(List.of("cat", "dog", "cow", "duck", "horse", "bird", "turtle"), "fish"));
		
	}

}
