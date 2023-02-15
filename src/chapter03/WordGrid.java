package chapter03;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Caca-Palavras
 * 
 * Um tabuleiro com varias letras embaralhadas, onde algumas palavras 
 * previamente escolhidas estao escondidas. O dever do usuario eh 
 * encontrar as mesmas.
 * 
 * @author Emerson
 * @since 2023
 * @see David Kopec - Classic Computer Science Problems in Java
 * */
public class WordGrid {

	private final char ALPHABET_LENGTH = 26;
	private final char FIRST_LETTER = 'A';
	private final int rows, columns;
	private char[][] grid;
	
	static class GridLocation{
		private final int row, column;
		
		GridLocation(int row, int column){
			this.row = row;
			this.column = column;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + column;
			result = prime * result + row;
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
			GridLocation other = (GridLocation) obj;
			return column == other.column && row == other.row;
		}
	}
	
	WordGrid(int rows, int columns){
		this.rows = rows;
		this.columns = columns;
		grid = new char[rows][columns];
		
		// Inicializando o grid com as letras aleatorias
		Random random = new Random();
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < columns; col++) {
				char randomLetter = (char) (random.nextInt(ALPHABET_LENGTH) + FIRST_LETTER);
				grid[row][col] = randomLetter;
			}
		}
	}
	
	void mark(String word, List<GridLocation> locations) {
		for(int i = 0; i < word.length(); i++) {
			GridLocation location = locations.get(i);
			grid[location.row][location.column] = word.charAt(i);
		}
	}
	
	List<List<GridLocation>> generateDomain(String word){
		List<List<GridLocation>> domain = new ArrayList<>();
		int length = word.length();
		
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < columns; col++) {
				if(col + length <= columns) {
					// Esquerda -> direita
					fillRight(domain, row, col, length);
					
					// Diagonal para a direita
					if(row + length <= rows) {
						fillDiagonalRight(domain, row, col, length);
					}
				}
				
				if(row + length <= rows) {
					// Direita -> esquerda
					fillDown(domain, row, col, length);
					
					// Diagonal para a esquerda
					if(col - length >= 0) {
						fillDiagonalLeft(domain, row, col, length);
					}
				}
			}
		}
		
		return domain;
	}
	
	private void fillRight(List<List<GridLocation>> domain, int row, int col, int length) {
		List<GridLocation> locations = new ArrayList<>();
		
		for(int c = col; c < (col + length); c++) {
			locations.add(new GridLocation(row, c));
		}
		
		domain.add(locations);
	}
	
	private void fillDown(List<List<GridLocation>> domain, int row, int col, int length) {
		List<GridLocation> locations = new ArrayList<>();
		
		for(int r = row; r < (row + length); r++) {
			locations.add(new GridLocation(r, col));
		}
		
		domain.add(locations);
	}
	
	private void fillDiagonalRight(List<List<GridLocation>> domain, int row, int col, int length) {
		List<GridLocation> locations = new ArrayList<>();
		int r = row;
		
		for(int c = col; c < (col + length); c++) {
			locations.add(new GridLocation(r, c));
			r++;
		}
		
		domain.add(locations);
	}
	
	private void fillDiagonalLeft(List<List<GridLocation>> domain, int row, int col, int length) {
		List<GridLocation> locations = new ArrayList<>();
		int c = col;
		
		for(int r = row; r < (row + length); r++) {
			locations.add(new GridLocation(r, c));
			c--;
		}
		
		domain.add(locations);
	}

	// Imprime o tabuleiro com as letras
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(char[] ch : grid) {
			sb.append(ch);
			sb.append(System.lineSeparator());
		}
		
		return sb.toString();
	}
}
