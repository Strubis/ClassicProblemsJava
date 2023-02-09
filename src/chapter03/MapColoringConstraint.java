package chapter03;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utilizando o CSP criado, o problema abaixo consiste em colorir uma
 * determinada regiao, como estados, paises, cidades etc., com tres cores.
 * Porém, regioes que ficam lado a lado nao podem conter as mesmas cores.
 * 
 * Trataremos as nossas regioes como variaveis e as tres cores como os
 * dominios, lembrando que a nossa condicao para pintar sera a constraint.
 * 
 * @author Emerson
 * @since 2023
 * @see David Kopec - Classic Computer Science Problems in Java
 * */
public class MapColoringConstraint extends Constraint<String, String>{

	private String place1, place2;
	
	public MapColoringConstraint(String place1, String place2) {
		super(List.of(place1, place2));
		this.place1 = place1;
		this.place2 = place2;
	}
	
	@Override
	public boolean satisfied(Map<String, String> assignment) {
		// Se um dos lugares nao esta na atribuicao, entao
		// nao eh possivel que as cores sejam conflitantes
		if(!assignment.containsKey(place1) || !assignment.containsKey(place2)) {
			return true;
		}
		
		// Checa se a cor atribuida para place1 nao eh a mesma atribuida para place2
		return !assignment.get(place1).equals(assignment.get(place2));
	}

	public static void main(String[] args) {
		List<String> variables = List.of(
				"Western Australia", "Northern Territory", "South Australia", 
				"Queensland", "New South Wales", "Victoria", "Tasmania");
		Map<String, List<String>> domains = new HashMap<>();
		
		variables.forEach((v) -> domains.put(v, List.of("red", "green", "blue")));
		
		CSP<String, String> csp = new CSP<>(variables, domains);
		csp.addConstraint(new MapColoringConstraint("Western Australia", "Northern Territory"));
		csp.addConstraint(new MapColoringConstraint("Western Australia", "South Australia"));
		csp.addConstraint(new MapColoringConstraint("South Australia", "Northern Territory"));
		csp.addConstraint(new MapColoringConstraint("Queensland", "Northern Territory"));
		csp.addConstraint(new MapColoringConstraint("Queensland", "South Australia"));
		csp.addConstraint(new MapColoringConstraint("Queensland", "New South Wales"));
		csp.addConstraint(new MapColoringConstraint("New South Wales", "South Australia"));
		csp.addConstraint(new MapColoringConstraint("Victoria", "South Australia"));
		csp.addConstraint(new MapColoringConstraint("Victoria", "New South Wales"));
		csp.addConstraint(new MapColoringConstraint("Victoria", "Tasmania"));
		
		Map<String, String> solution = csp.backtrackingSearch();
		if(solution == null) {
			System.out.println("Sem solucao possivel!");
		}else {
			System.out.println(solution);
		}
	}
}
