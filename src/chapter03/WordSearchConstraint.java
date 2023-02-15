package chapter03;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import chapter03.WordGrid.GridLocation;

/**
 * Constraint para o problema de caca-palavras.
 * 
 * @author Emerson
 * @since 2023
 * @see David Kopec - Classic Computer Science Problems in Java
 * */
public class WordSearchConstraint extends Constraint<String, List<GridLocation>>{
	
	WordSearchConstraint(List<String> words) {
		super(words);
	}

	@Override
	public boolean satisfied(Map<String, List<GridLocation>> assignment) {
		// Combina todas as grids em uma so
		List<GridLocation> allLocations = assignment.values().stream().
				flatMap(Collection::stream).collect(Collectors.toList());
		
		// Set ira eliminar os valores duplicados
		Set<GridLocation> allLocationsSet = new HashSet<>(allLocations);
		
		// Sobrescreve qualquer gridLocation que estiver duplicado
		return allLocationsSet.size() == allLocations.size();
	}
	
	public static void main(String[] args) {
		WordGrid grid = new WordGrid(9, 9);
		List<String> words = List.of("LARANJA", "KIWI", "COCO", "BANANA", "TOMATE");
		
		// Gera os dominios para todas as palavras
		Map<String, List<List<GridLocation>>> domains = new HashMap<>();
		for(String word : words) {
			domains.put(word, grid.generateDomain(word));
		}
		
		CSP<String, List<GridLocation>> csp = new CSP<>(words, domains);
		csp.addConstraint(new WordSearchConstraint(words));
		
		Map<String, List<GridLocation>> solution = csp.backtrackingSearch();
		
		if(solution == null) {
			System.out.println("Sem solucao possivel!");
		}else {
			Random random = new Random();
			
			for(Entry<String, List<GridLocation>> item : solution.entrySet()) {
				String word = item.getKey();
				List<GridLocation> locations = item.getValue();
				
				// Coloca uma palavra aleatoria ao contrario
				if(random.nextBoolean()) {
					Collections.reverse(locations);
				}
				
				grid.mark(word, locations);
			}
			
			System.out.println(grid);
		}
		
	}
}
