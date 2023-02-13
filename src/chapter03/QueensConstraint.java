package chapter03;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * O problema das oito rainhas
 * 
 * O problema consiste em organizar num tabuleiro de xadrez oito rainhas,
 * de modo que nenhuma interfira no caminho da outra, ou seja, uma outra
 * rainha nao pode ficar na mesma linha, coluna ou diagonal de outra.
 * 
 * @author Emerson
 * @since 2023
 * @see David Kopec - Classic Computer Science Problems in Java
 * */
public class QueensConstraint extends Constraint<Integer, Integer>{

	private List<Integer> columns;
	
	public QueensConstraint(List<Integer> columns) {
		super(columns);
		this.columns = columns;
	}
	
	@Override
	public boolean satisfied(Map<Integer, Integer> assignment) {
		for(Entry<Integer, Integer> item : assignment.entrySet()) {
			// q1c = rainha na coluna 1, q1r = rainha na linha 1
			int q1c = item.getKey();
			int q1r = item.getValue();
			
			// q2c = rainha na coluna 2
			for(int q2c = q1c + 1; q2c <= columns.size(); q2c++) {
				if(assignment.containsKey(q2c)) {
					// q2r = rainha na linha 2
					int q2r = assignment.get(q2c);
					
					// Mesma linha?
					if(q1r == q2r) {
						return false;
					}
					
					// Mesma diagonal?
					if(Math.abs(q1r - q2r) == Math.abs(q1c - q2c)) {
						return false;
					}
				}
			}
		}
		
		// Sem conflito
		return true;
	}
	
	// Mostra um simples tabuleiro com a solucao encontrada
	private static void showChessboard(Map<Integer, Integer> sol) {
		for(Integer i : sol.keySet()) {
			for(int j = 1; j <= 8; j++) {
				if( j == sol.get(i) ) 
					System.out.print("Q ");
				else 
					System.out.print("* ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		List<Integer> columns = List.of(1, 2, 3, 4, 5, 6, 7, 8);
		Map<Integer, List<Integer>> rows = new HashMap<>();
		
		for(int column : columns) {
			rows.put(column, List.of(1, 2, 3, 4, 5, 6, 7, 8));
		}
		
		CSP<Integer, Integer> csp = new CSP<>(columns, rows);
		csp.addConstraint(new QueensConstraint(columns));
		
		Map<Integer, Integer> solution = csp.backtrackingSearch();
		if(solution == null){
			System.out.println("Sem solucao possivel!");
		}else {
			showChessboard(solution);
			System.out.println(solution);
		}
	}

}
