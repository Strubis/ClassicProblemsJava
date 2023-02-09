package chapter03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CSP - Constraint-satisfaction problems
 * 
 * Sao problemas compostos por variaveis, dominios e constraints
 * 
 * @author Emerson
 * @since 2023
 * @see David Kopec - Classic Computer Science Problems in Java
 * */
public class CSP<V, D> {

	private List<V> variables;
	private Map<V, List<D>> domains;
	private Map<V, List<Constraint<V, D>>> constraints = new HashMap<>();
	
	CSP(List<V> variables, Map<V, List<D>> domains){
		this.variables = variables;
		this.domains = domains;
		
		// Verificando se todas as variaveis possuem um dominio
		for(V variable : variables) {
			constraints.put(variable, new ArrayList<>());
			if(!domains.containsKey(variable)) {
				throw new IllegalArgumentException("Toda variavel deve conter um dominio.");
			}
		}
	}
	
	void addConstraint(Constraint<V, D> constraint) {
		for(V variable : constraint.variables) {
			if(!variables.contains(variable)) {
				throw new IllegalArgumentException("Variavel na constraint nao esta em CSP.");
			}
		}
	}
	
	// Checa se o valor de atribuicao eh consistente, checando todos
	// as constraints da variavel em questao
	boolean consistent(V variable, Map<V, D> assignment) {
		for(Constraint<V, D> constraint : constraints.get(variable)) {
			if(!constraint.satisfied(assignment)) {
				return false;
			}
		}
		
		return true;
	}
	
	Map<V, D> backtrackingSearch(Map<V, D> assignment){
		// Atribuicao esta completa se todas as variaveis estao atribuidas (caso base)
		if(assignment.size() == variables.size()) {
			return assignment;
		}
		
		// Pega a primeira variavel no CSP, mas nao esta atribuida
		V unassigned = variables.stream().filter((v) -> !assignment.containsKey(v)).findFirst().get();
		
		// Olhando todos os valores de dominio para a primeira variavel nao atribuida
		for(D value : domains.get(unassigned)) {
			// Copia simples da atribuicao que podemos mudar
			Map<V, D> localAssignemnt = new HashMap<>(assignment);
			localAssignemnt.put(unassigned, value);
			
			// Se ainda for consistente, entao a recursao eh invocada
			if(consistent(unassigned, localAssignemnt)) {
				Map<V, D> result = backtrackingSearch(localAssignemnt);
				
				// Se o resultado nao for encontrado, entao a recursao acaba
				if(result != null) {
					return result;
				}
			}
		}
		
		return null;
	}
	
	// Auxiliar quando o inicio nao eh conhecido
	Map<V, D> backtrackingSearch(){
		return backtrackingSearch(new HashMap<>());
	}
}
