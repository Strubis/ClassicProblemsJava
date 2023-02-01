package chapter02;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Missionarios e Canibais
 * O problema consiste em conter um certo numero de missionarios e canibais num lado
 * da ilha, precisando atravessar para o outro lado utilizando um barco. As regras sao:
 * 	- Em qualquer lado da ilha o numero de canibais nao pode ser maior que o de missionarios;
 * 	- No minimo uma pessoa e no maximo duas precisam estar no barco para que ele atravesse;
 * 	- Todas devem estar do outro lado no final do problema.
 * 
 * @author Emerson
 * @since 2023
 * @see David Kopec - Classic Computer Science Problems in Java
 * */
public class MCState {
	private static final int MAX_NUM = 3;
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

	@Override
	public String toString() {
		return String.format(
				"No lado Oeste do banco estao %d missionarios e %d canibais.%n"
				+ "No lado Leste do banco estao %d missionarios e %d canibais.%n"
				+ "O barco esta no lado %s do banco.", wm, wc, em, ec, boat ? "Oeste" : "Leste");
	}
	
}
