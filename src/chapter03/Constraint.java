package chapter03;

import java.util.List;
import java.util.Map;

/**
 * CSP - Constraint-satisfaction problems
 * 
 * @author Emerson
 * @since 2023
 * @see David Kopec - Classic Computer Science Problems in Java
 * */
public abstract class Constraint<V, D> {// V eh o tipo de variavel, D eh o tipo de dominio

	// As variaveis onde a constraint estara
	protected List<V> variables;
	
	public Constraint(List<V> variables) {
		this.variables = variables;
	}
	
	// Deve ser sobreescrito
	public abstract boolean satisfied(Map<V, D> assignment);
}
