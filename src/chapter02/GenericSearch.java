package chapter02;

import java.util.List;

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
