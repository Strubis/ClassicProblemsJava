package chapter02;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Problemas de algoritmo de busca
 * 
 * Algoritmos de busca podem ser implementados para diversos objetivos,
 * aqui sera apresentado a aplicacao na busca de um gene. Um gene e 
 * composto por varios codons, um codon e um conjunto de tres 
 * nucleotideos, e um nucleotideo pode ser A, C, G ou T.
 * 
 * @author Emerson
 * @since 2023
 * @see David Kopec - Classic Computer Science Problems in Java
 * */
public class Gene {

	// Array para armazenar os codons que formam um gene
	private ArrayList<Codon> codons = new ArrayList<>();
	
	enum Nucleotide{
		A, C, G, T
	}
	
	static class Codon implements Comparable<Codon>{
		public final Nucleotide first, second, third;
		private final Comparator<Codon> comparator = 
				Comparator.comparing((Codon c) -> c.first).
				thenComparing((Codon c) -> c.second).
				thenComparing((Codon c) -> c.third);
		
		Codon(String codonStr){
			first = Nucleotide.valueOf( codonStr.substring(0, 1) );
			second = Nucleotide.valueOf( codonStr.substring(1, 2) );
			third = Nucleotide.valueOf( codonStr.substring(2, 3) );
		}
		
		@Override
		public int compareTo(Codon other){
			/**
			 * O primeiro e comparado, entao o segundo, etc.
			 * IOW primeiro tem precedencia sobre o segundo e
			 * o segundo sobre o terceiro
			 * */
			return comparator.compare(this, other);
		}
	}
	
	Gene(String geneStr){
		for(int i = 0; i < geneStr.length() - 3; i += 3) {
			// Pega todos os 3 caracteres na string para formar um codon
			codons.add( new Codon(geneStr.substring(i, i + 3)) );
		}
	}
	
	public static void main(String[] args) {
		String geneStr = "ACGTGGCTCTCTAACGTACGTACGTACGGGGTTTATATATACCCTAGGACTCCCTTT";
		
		Gene gene = new Gene(geneStr);
		gene.codons.forEach(
				(codon) -> System.out.println(codon.first + "" + codon.second + "" + codon.third));
	}
}
