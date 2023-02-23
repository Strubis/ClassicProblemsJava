package chapter04;

/**
 * Edge com peso
 * 
 * Classe que sera utilizada para representar uma aresta/caminho
 * no grafo com peso, ela sera util para verificar qual caminho
 * eh o melhor, considerando o menor valor atribuido.
 * 
 * @author Emerson
 * @since 2023
 * @see David Kopec - Classic Computer Science Problems in Java
 * */
public class WeightedEdge extends Edge implements Comparable<WeightedEdge> {
	// Peso
	public final double weight;
	
	public WeightedEdge(int u, int v, double weight) {
		super(u, v);
		this.weight = weight;
	}
	
	@Override
	public WeightedEdge reversed() {
		return new WeightedEdge(v, u, weight);
	}
	
	// Para encontrar o melhor caminho verifica-se qual tem o menor peso
	@Override
	public int compareTo(WeightedEdge other) {
		Double mine = weight;
		Double theirs = other.weight;
		
		return mine.compareTo(theirs);
	}
	
	@Override
	public String toString() {
		return u + " " + weight + "> " + v;
	}
}
