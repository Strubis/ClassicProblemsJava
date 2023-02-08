package chapter02;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import chapter02.GenericSearch.Node;

/**
 * Missionarios e Canibais
 * O problema consiste em conter um certo numero de missionarios e canibais num lado
 * da ilha, precisando atravessar para o outro lado utilizando um barco. As regras sao:
 * 	- Em qualquer lado da ilha o numero de canibais nao pode ser maior que o de missionarios;
 * 	- No minimo uma pessoa e no maximo N - 1 precisam estar no barco para que ele atravesse;
 * 	- Todas devem estar do outro lado no final do problema.
 * 
 * *Obs.: algumas mudancas foram realizadas para que o algoritmo funcionasse com N missionarios
 * 			e canibais.
 * 
 * @author Emerson
 * @since 2023
 * @see David Kopec - Classic Computer Science Problems in Java
 * */
public class MCState {
	private static final int MAX_NUM = 5;
	private final int wm; // missionarios no lado oeste
	private final int wc; // canibais no lado oeste
	private final int em; // missionarios no lado leste
	private final int ec; // canibais no lado leste
	private final boolean boat; // o barco esta no lado oeste?
	
	MCState(int missionaries, int cannibals, boolean boat){
		wm = missionaries;
		wc = cannibals;
		em = MAX_NUM - wm;
		ec = MAX_NUM - wc;
		this.boat = boat;
	}
	
  // Verifica se chegamos no objetivo final
	boolean goalTest() {
		return isLegal() && em == MAX_NUM && ec == MAX_NUM;
	}
	
  // Verifica se um movimento e legal ou nao
	boolean isLegal() {
		if(wm < wc && wm > 0) {
			return false;
		}
		if(em < ec && em > 0) {
			return false;
		}
		
		return true;
	}
	
  // Lista contendo as possibilidades de movimentos
	static List<MCState> successors(MCState mcs){
		List<MCState> sucs = new ArrayList<>();
		
		// Barco no lado Oeste
		if(mcs.boat) {
			if(mcs.wm > 1){
				sucs.add(new MCState(mcs.wm - 2, mcs.wc, !mcs.boat));
			}
			if(mcs.wm > 0) {
				sucs.add(new MCState(mcs.wm - 1, mcs.wc, !mcs.boat));
			}
			if(mcs.wc > 1) {
				sucs.add(new MCState(mcs.wm, mcs.wc - 2, !mcs.boat));
			}
			if(mcs.wc > 0) {
				sucs.add(new MCState(mcs.wm, mcs.wc - 1, !mcs.boat));
			}
			if(mcs.wc > 0 && mcs.wm > 0) {
				sucs.add(new MCState(mcs.wm - 1, mcs.wc - 1, !mcs.boat));
			}
		}else {// Barco no lado Leste
			if(mcs.em > 1){
				sucs.add(new MCState(mcs.wm + 2, mcs.wc, !mcs.boat));
			}
			if(mcs.em > 0) {
				sucs.add(new MCState(mcs.wm + 1, mcs.wc, !mcs.boat));
			}
			if(mcs.ec > 1) {
				sucs.add(new MCState(mcs.wm, mcs.wc + 2, !mcs.boat));
			}
			if(mcs.ec > 0) {
				sucs.add(new MCState(mcs.wm, mcs.wc + 1, !mcs.boat));
			}
			if(mcs.ec > 0 && mcs.em > 0) {
				sucs.add(new MCState(mcs.wm + 1, mcs.wc + 1, !mcs.boat));
			}
		}
		
		sucs.removeIf(Predicate.not(MCState::isLegal));
		return sucs;
	}
	
	// Metodo para resolucao do algoritmo com N missionarios e canibais
	static List<MCState> successorsToN(MCState mcs){
		List<MCState> sucs = new ArrayList<>();
		
		if(mcs.boat) {
			int i = MAX_NUM - 2;
			while(i >= 0) {
				if(mcs.wm > i) {
					sucs.add(new MCState(mcs.wm - i - 1, mcs.wc, !mcs.boat));
				}
				
				if(mcs.wc > i) {
					sucs.add(new MCState(mcs.wm, mcs.wc - i - 1, !mcs.boat));
				}
				
				i--;
			}
			
			if(mcs.wc > 0 && mcs.wm > 0) {
				sucs.add(new MCState(mcs.wm - 1, mcs.wc - 1, !mcs.boat));
			}
		}else {
			int i = MAX_NUM - 2;
			while(i >= 0) {
				if(mcs.em > i) {
					sucs.add(new MCState(mcs.wm + i + 1, mcs.wc, !mcs.boat));
				}
				
				if(mcs.ec > i) {
					sucs.add(new MCState(mcs.wm, mcs.wc + i + 1, !mcs.boat));
				}
				
				i--;
			}
			
			if(mcs.ec > 0 && mcs.em > 0) {
				sucs.add(new MCState(mcs.wm + 1, mcs.wc + 1, !mcs.boat));
			}
		}
		
		sucs.removeIf(Predicate.not(MCState::isLegal));
		return sucs;
	}
	
	static void displaySolution(List<MCState> path) {
		if(path.size() == 0) {
			return; // checa a sanidade
		}
		
		MCState oldState = path.get(0);
		System.out.println(oldState);
		
		for(MCState currentState : path.subList(1, path.size())) {
			if(currentState.boat) {
				System.out.printf("%d missionarios e %d canibais movidos de Leste para Oeste.%n", 
						oldState.em - currentState.em, oldState.ec - currentState.ec);
			}else {
				System.out.printf("%d missionarios e %d canibais movidos do Oeste para Leste.%n",
						oldState.wm - currentState.wm, oldState.wc - currentState.wc);
			}
			
			System.out.println(currentState);
			oldState = currentState;
		}
	}

	@Override
	public String toString() {
		return String.format(
				"No lado Oeste do banco estao %d missionarios e %d canibais.%n"
				+ "No lado Leste do banco estao %d missionarios e %d canibais.%n"
				+ "O barco esta no lado %s do banco.%n", wm, wc, em, ec, boat ? "Oeste" : "Leste");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (boat ? 1231 : 1237);
		result = prime * result + ec;
		result = prime * result + em;
		result = prime * result + wc;
		result = prime * result + wm;
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MCState other = (MCState) obj;
		return boat == other.boat && ec == other.ec && em == other.em && wc == other.wc && wm == other.wm;
	}
	
	public static void main(String[] args) {
		MCState start = new MCState(MAX_NUM, MAX_NUM, true);
//		Node<MCState> solution = GenericSearch.bfs(start, MCState::goalTest, MCState::successors);
		Node<MCState> solution = GenericSearch.bfs(start, MCState::goalTest, MCState::successorsToN);
		
		if(solution == null) {
			System.out.println("Sem solução possível!");
		}else {
			List<MCState> path = GenericSearch.nodeToPath(solution);
			displaySolution(path);
		}
		
	}
}
