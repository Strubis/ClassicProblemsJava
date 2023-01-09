package chapter01;

import java.util.Random;

/**
 * Esse algoritmo utilizada da operacao XOR, que é um OU Exclusivo representado pelo sinal "^".
 * Sua exclusividade se dá no seu retorno:
 * 	- True, quando um dos operandos é True -> 1 no binario
 * 	- False, quando os dois operandos são True ou quando nenhum é -> 0 no binario
 * 
 * @author Emerson
 * @since 2023
 * @see David Kopec - Classic Computer Science Problems in Java
 * */
public class UnbreakableEncryption {
	//Classe responsavel por conter o par de chaves
	public static class KeyPair{
		public final byte[] key1;
		public final byte[] key2;
		
		KeyPair(byte[] key1, byte[] key2) {
			this.key1 = key1;
			this.key2 = key2;
		}
	}
	
	// Gera o array de bytes preenchido com o *tamanho* aleatorio
	private static byte[] randomKey(int length){
		byte[] dummy = new byte[length];
		
		// Lib utilizada para gerar os aleatorios
		Random random = new Random();
		random.nextBytes(dummy);
		
		return dummy;
	}
	
	// Encryption
	public static KeyPair encrypt(String original) {
		byte[] originalBytes = original.getBytes();
		byte[] dummyKey = randomKey(originalBytes.length);
		byte[] encryptedKey = new byte[originalBytes.length];
		
		for(int i = 0; i < originalBytes.length; i++) {
			// Aplicando a operacao XOR para cada byte
			encryptedKey[i] = (byte) (originalBytes[i] ^ dummyKey[i]);
		}
		
		return new KeyPair(dummyKey, encryptedKey);
	}
	
	// Decryption
	public static String decrypt(KeyPair kp) {
		byte[] decrypted = new byte[kp.key1.length];
		for(int i = 0; i < kp.key1.length; i++) {
			// Aplicando novamente o XOR para cada byte
			decrypted[i] = (byte) (kp.key1[i] ^ kp.key2[i]);
		}
		
		return new String(decrypted);
	}
	
	public static void main(String[] args) {
		KeyPair kp = encrypt( "Test your luck!" );
		String reverse = decrypt( kp );
		
		System.out.println(reverse);
	}
}
