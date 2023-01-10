package chapter01;

/**
 * O calculo de PI pode ser obtido atraves da Formula de Leibniz:
 * - pi = 4/1 - 4/3 + 4/5 - 4/7 + 4/9...
 * 
 * Podemos observar que o denominador aumenta de 2 em 2, enquanto
 * o numerador permanece em 4 e as operacoes oscilam entre + e -.
 * 
 * @author Emerson
 * @since 2023
 * @see David Kopec - Classic Computer Science Problems in Java 
 * */
public class PiCalculator {
	
	/**
	 * Esse metodo calcula os n-termos de pi
	 * 
	 * @param nTerms os n-termos para serem calculados
	 * @return o valor da soma dos n-termos de pi
	 * */
	private static double calculatePi(int nTerms) {
		final double numerator = 4.0;
		double denominator = 1.0;
		double pi = 0.0;
		// A operacao de + e - serao representadas por -1 e 1
		double operation = 1;
		
		for(int i = 0; i < nTerms; i++) {
			pi += operation * (numerator / denominator);
			denominator += 2.0;
			operation *= -1.0;
		}
		
		return pi;
	}
	
	public static void main(String[] args) {
		// Quanto maior o numero de termos maior sera a precisao
		System.out.println( calculatePi(1000000000) );
	}
}
