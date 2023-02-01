package chapter02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import chapter02.GenericSearch.Node;

/**
 * O problema do labirinto
 * 
 * @author Emerson
 * @since 2023
 * @see David Kopec - Classic Computer Science Problems in Java
 * */
public class Maze {
	private final int rows, columns;
	private static int stepNum;
	private final MazeLocation start, goal;
	private Cell[][] grid;
	
	Maze(){
		this(10, 10, new MazeLocation(0, 0), new MazeLocation(9, 9), 0.2);
	}
	
	Maze(int rows, int columns, MazeLocation start, MazeLocation goal, double sparseness){
		// Inicializando as variaveis estaticas
		this.rows = rows;
		this.columns = columns;
		this.start = start;
		this.goal = goal;
		// Preenchendo o grid com os espacos vazios
		this.grid = new Cell[rows][columns];
		for(Cell[] row : grid) {
			Arrays.fill(row, Cell.EMPTY);
		}
		
		// Populando o grid com os espacos bloqueados
		randomlyFill(sparseness);
		
		// Preenchendo a localizacao inicial e final
		grid[start.row][start.column] = Cell.START;
		grid[goal.row][goal.column] = Cell.GOAL;
	}
	
	private void randomlyFill(double sparseness) {
		for(int row = 0; row < rows; row++) {
			for(int column = 0; column < columns; column++) {
				if(Math.random() < sparseness) {
					grid[row][column] = Cell.BLOCKED;
				}
			}
		}
	}
	
	// Metodo que verifica se chegamos no nosso objetivo
	public boolean goalTest(MazeLocation ml) {
		return goal.equals(ml);
	}
	
	// Metodo que retorna os caminhos possiveis para andar, ou seja, em branco
	public List<MazeLocation> successors(MazeLocation ml){
		List<MazeLocation> locations = new ArrayList<>();
		
		// Acima
		if( ml.row + 1 < rows && grid[ml.row + 1][ml.column] != Cell.BLOCKED ) {
			locations.add( new MazeLocation(ml.row + 1, ml.column));
		}
		
		// Abaixo
		if( ml.row - 1 >= 0 && grid[ml.row - 1][ml.column] != Cell.BLOCKED ) {
			locations.add( new MazeLocation(ml.row - 1, ml.column));
		}
		
		// Direita
		if( ml.column + 1 < columns && grid[ml.row][ml.column + 1] != Cell.BLOCKED ) {
			locations.add( new MazeLocation(ml.row, ml.column + 1));
		}
		
		// Esquerda
		if( ml.column - 1 > 0 && grid[ml.row][ml.column - 1] != Cell.BLOCKED ) {
			locations.add( new MazeLocation(ml.row, ml.column - 1));
		}
		
		return locations;
	}
	
	/**
	 * De acordo com uma determinada localizacao, retorna uma linha reta em direcao
	 * ao objetivo, utilizando o Teorema de Pitagoras.
	 * 
	 * @param ml a localizacao
	 * @return a distancia em linha reta ate o objetivo
	 * */
	double euclideanDistance(MazeLocation ml) {
		int xDist = ml.column - goal.column;
		int yDist = ml.row - goal.row;
		
		return Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
	}
	
	/**
	 * Calcula uma distancia de um ponto ate o objetivo final, utilizando o 
	 * calculo da distancia Manhattan, onde soma-se o numero de linhas horizontais
	 * e verticais que serao utilizadas para chegar.
	 * 
	 * @param ml o ponto
	 * @return a distancia Manhattan do ponto ate o objetivo final
	 * */
	double manhattanDistance(MazeLocation ml) {
		int xDis = Math.abs(ml.column - goal.column);
		int yDis = Math.abs(ml.row - goal.row);
		
		return xDis + yDis;
	}
	
	void mark(List<MazeLocation> path) {
		stepNum = 0;
		for(MazeLocation ml : path) {
			grid[ml.row][ml.column] = Cell.PATH;
			stepNum++;
		}
		
		grid[start.row][start.column] = Cell.START;
		grid[goal.row][goal.column] = Cell.GOAL;
	}
	
	void clear(List<MazeLocation> path) {
		for(MazeLocation ml : path) {
			grid[ml.row][ml.column] = Cell.EMPTY;
		}
		
		grid[start.row][start.column] = Cell.START;
		grid[goal.row][goal.column] = Cell.GOAL;
	}
	
	@Override
	public String toString() {
		// Retorna o labirinto formatado para impressao no console
		StringBuilder sb = new StringBuilder();
		
		for(Cell[] row : grid) {
			for(Cell cell : row) {
				sb.append(cell.toString());
			}
			sb.append(System.lineSeparator());
		}
		
		return sb.toString();
	}

	enum Cell{
		EMPTY(" "),
		BLOCKED("X"),
		START("S"),
		GOAL("G"),
		PATH("*");
		
		private final String code;
		
		Cell(String code){
			this.code = code;
		}
		
		@Override
		public String toString() {
			return code;
		}
	}
	
	static class MazeLocation{
		final int row, column;
		
		MazeLocation(int row, int column){
			this.row = row;
			this.column = column;
		}

		@Override
		public int hashCode() {
			return Objects.hash(column, row);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MazeLocation other = (MazeLocation) obj;
			return column == other.column && row == other.row;
		}
		
	}
	
	public static void main(String[] args) {
		Maze maze = new Maze();
		System.out.println("Original Maze:\n" + maze);
		
		Node<MazeLocation> solution1 = GenericSearch.dfs(maze.start, maze::goalTest, maze::successors);
		
		if(solution1 == null) {
			System.out.println("Sem solucao possivel utilizando DFS!");
		}else {
			System.out.println("Utilizando DFS:");
			List<MazeLocation> path1 = GenericSearch.nodeToPath(solution1);
			maze.mark(path1);
			System.out.println(maze);
			maze.clear(path1);
			System.out.println("Quantidade de passos: " + stepNum + "\n");
		}
		
		Node<MazeLocation> solution2 = GenericSearch.bfs(maze.start, maze::goalTest, maze::successors);
		
		if(solution2 == null) {
			System.out.println("Sem solucao possivel utilizando BFS!");
		}else {
			System.out.println("Utilizando BFS:");
			List<MazeLocation> path2 = GenericSearch.nodeToPath(solution2);
			maze.mark(path2);
			System.out.println(maze);
			maze.clear(path2);
			System.out.println("Quantidade de passos: " + stepNum + "\n");
		}
		
		Node<MazeLocation> solution3 = GenericSearch.astar(maze.start, maze::goalTest, maze::successors, maze::manhattanDistance);
		
		if(solution3 == null) {
			System.out.println("Sem solucao possivel utilizando A*!");
		}else {
			System.out.println("Utilizando A*:");
			List<MazeLocation> path3 = GenericSearch.nodeToPath(solution3);
			maze.mark(path3);
			System.out.println(maze);
			maze.clear(path3);
			System.out.println("Quantidade de passos: " + stepNum + "\n");
		}
	}

}
