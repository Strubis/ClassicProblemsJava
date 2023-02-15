package chapter03;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * SEND+MORE=MONEY
 * 
 * O problema consiste em substituir cada letra por um digito (0-9). As regras:
 * 	- A substituicao deve atender a logica algebrica;
 * 	- Uma letra nao pode ter dois numeros, mas se a letra se repetir entao o
 * 		numero tambem se repete.
 * 
 * @author Emerson
 * @since 2023
 * @see David Kopec - Classic Computer Science Problems in Java
 * */
public class SendMoreMoneyConstraint extends Constraint<Character, Integer> {

	private List<Character> letters;
	
	SendMoreMoneyConstraint(List<Character> letters){
		super(letters);
		this.letters = letters;
	}
	
	@Override
	public boolean satisfied(Map<Character, Integer> assignment) {
		// Se encontrar algum valor duplicado entao a solucao nao eh valida
		if((new HashSet<>(assignment.values())).size() < assignment.size()) {
			return false;
		}
		
		// Se todas as variaveis foram atribuidas, checa se estao corretas
		if(assignment.size() == letters.size()) {
			int s = assignment.get('S');
			int e = assignment.get('E');
			int n = assignment.get('N');
			int d = assignment.get('D');
			int m = assignment.get('M');
			int o = assignment.get('O');
			int r = assignment.get('R');
			int y = assignment.get('Y');
			
			int send = s * 1000 + e * 100 + n * 10 + d;
			int more = m * 1000 + o * 100 + r * 10 + e;
			int money = m * 10000 + o * 1000 + n * 100 + e * 10 + y;
			
			return send + more == money;
		}
		
		// Sem conflito
		return true;
	}
	
	public static void main(String[] args) {
		List<Character> letters = List.of('S', 'E', 'N', 'D', 'M', 'O', 'R', 'Y');
		Map<Character, List<Integer>> possibleDigits = new HashMap<>();
		
		for(Character c : letters) {
			possibleDigits.put(c, List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
		}
		
		// Como um numero nao pode comecar por 0, retiramos essa possibilidade
		possibleDigits.replace('M', List.of(1));
		
		CSP<Character, Integer> csp = new CSP<>(letters, possibleDigits);
		csp.addConstraint(new SendMoreMoneyConstraint(letters));
		
		Map<Character, Integer> result = csp.backtrackingSearch();
		if(result == null) {
			System.out.println("Sem solucao possivel!");
		}else {
			System.out.println(result);
		}
	}
}
