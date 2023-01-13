package chapter01;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Existem tres torres A, B e C. A primeira torre (A) possui tres discos que sao
 * ordenados do maior para o menor e de baixo para cima, ou seja, o maior ficara
 * no menor nivel enquanto o menor disco ficara no topo. O problema consiste em
 * passar todos os discos da torre A para a torre C, movendo somente um disco por
 * vez e, sabendo que um disco maior nao pode ficar sobre um disco menor.
 * 
 * @author Emerson
 * @since 2023
 * @see David Kopec - Classic Computer Science Problems in Java
 * */
public class TowersOfHanoi {
	
	/**
	 * Solucao do desafio proposto pelo autor: realizar a implementacao do algoritmo
	 * para n torres.
	 * */
	public static class HanoiMultipleTowers{
		private static int numDiscs = 0;
		private static int numTowers = 0;
		
		// Uma lista das pilhas/torres
		public final List<Stack<Integer>> towers;
		
		/**
		 * Constroi as torres de hanoi com a quantidade determinada de discos.
		 * 
		 * @param numTowers quantidade de torres
		 * @param numDiscs quantidade de discos
		 * */
		HanoiMultipleTowers(int numTowers, int numDiscs){
			towers = new ArrayList<>();
			this.numTowers = numTowers;
			
			for(int i = 0; i < numTowers; i++) {
				towers.add( new Stack<Integer>() );
			}
			
			this.numDiscs = numDiscs;
			for(int i = 1; i <= numDiscs; i++) {
				towers.get(0).push( i );
			}
		}
		
		// Sem alteracoes nessa parte do algoritmo
		private void move(Stack<Integer> begin, Stack<Integer> end, Stack<Integer> temp, int n) {
			if( begin.isEmpty() ) {
				return;
			}
			
			if( n == 1 ) {
				end.push( begin.pop() );
			}else {
				move(begin, temp, end, n - 1);
				move(begin, end, temp, n);
				move(temp, end, begin, n - 1);
			}
		}
		
		/**
		 *  Mantemos a primeira torre sendo a inicial e a ultima sendo a final, pegamos a 
		 * 	torre mediapara utilizarmos como temp.
		 */
		public void solve() {
			move(towers.get(0), towers.get(numTowers - 1), towers.get(numTowers / 2), numDiscs);
		}
	}
	
	public static class Hanoi{
		private static int numDiscs = 0;
		
		/**
		 * Seguindo a estrutura de pilha (Stack), onde o ultimo elemento inserido
		 * e o primeiro a sair (LIFO - Last In First Out), iremos criar as nossas 
		 * torres. Preenchendo a torre A com os nossos discos.
		 * */
		public final Stack<Integer> towerA = new Stack<>();
		public final Stack<Integer> towerB = new Stack<>();
		public final Stack<Integer> towerC = new Stack<>();
		
		/**
		 * Constroi a torre de hanoi com a quantidade determinada de discos.
		 * 
		 * @param discs quantidade de discos
		 * */
		public Hanoi(int discs) {
			numDiscs = discs;
			for(int i = 1; i <= discs; i++) {
				towerA.push( i );
			}
		}
		
		/**
		 * Para a resolucao do problema vamos usar a estrategia de dividir e conquistar,
		 * separando em um caso base e uma solucao recursiva. Nosso caso base sera
		 * transferir um simples disco para outra torre e mover recursivamente os outros
		 * discos para uma outra torre. Sendo assim:
		 * 	- mover o n-1 disco superior da torre inicial (A) para uma torre temporaria (B), 
		 * 		usando uma outra torre (C) como uma parada entre elas;
		 * 	- mover o disco mais baixo de uma torre (A) para outra (C);
		 *  - mover os outros n-1 discos de uma torre (B) para outra (C), usando a torre 
		 *  	inicial (A) como parada entre elas.
		 *  
		 *  @param begin torre inicial, no exemplo e a torre A
		 *  @param end torre destino ou final, no exemplo e a torre C
		 *  @param temp torre intermediaria, utilizada como auxiliar para a troca dos discos
		 *  @param n quantidade de discos
		 * */
		private void move(Stack<Integer> begin, Stack<Integer> end, Stack<Integer> temp, int n) {
			if( begin.isEmpty() ) {
				return;
			}
			
			if( n == 1 ) {
				end.push( begin.pop() );
			}else {
				move(begin, temp, end, n - 1);
				move(begin, end, temp, n);
				move(temp, end, begin, n - 1);
			}
		}
		
		public void solve() {
			move(towerA, towerC, towerB, numDiscs);
		}
	}
	
	
	public static void main(String[] args) {
		Hanoi hanoi = new Hanoi(5);
		
		System.out.println("Antes de iniciar:");
		System.out.println( "\tTorre A: " + hanoi.towerA );
		System.out.println( "\tTorre B: " + hanoi.towerB );
		System.out.println( "\tTorre C: " + hanoi.towerC );
		hanoi.solve();
		
		System.out.println("Depois de terminar:");
		System.out.println( "\tTorre A: " + hanoi.towerA );
		System.out.println( "\tTorre B: " + hanoi.towerB );
		System.out.println( "\tTorre C: " + hanoi.towerC );
		
		HanoiMultipleTowers hanoiMultiple = new HanoiMultipleTowers(5, 7);
		
		System.out.println("\nHanoi Multiple Towers:");
		System.out.println("Antes de iniciar:");
		hanoiMultiple.towers.forEach(tower -> System.out.println("\t" + tower));
		hanoiMultiple.solve();
		
		System.out.println("Depois de terminar:");
		hanoiMultiple.towers.forEach(tower -> System.out.println("\t" + tower));
	}
}
