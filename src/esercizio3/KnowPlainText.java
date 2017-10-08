package esercizio3;

import java.util.Arrays;

import esercizio1.Hill;

public class KnowPlainText {
	
	private String plainText;
	private String cipherText;
	
	
	public KnowPlainText(String plainText, String cipherText) {		
		this.plainText = plainText;
		this.cipherText = cipherText;
	}
	
	private void encodeText(String plain, String cipher, int[] plainMatrix, int[] cipherMatrix) {		
		
		for (int i = 0; i < 4; i++){			
			plainMatrix[i] = Hill.encAlphabet.get(plain.substring(i,i+1));
			cipherMatrix[i] = Hill.encAlphabet.get(cipher.substring(i,i+1));
		}
		
	}
	
	public String attack() {
		int [] plainMatrix = new int[4];
		int [] cipherMatrix = new int[4];
		encodeText(plainText, cipherText, plainMatrix, cipherMatrix);		
		
		System.out.println("plainText: "+plainText+" cipherText "+cipherText);
		System.out.println("plainMatrix: "+Arrays.toString(plainMatrix)+" cipherMatrix "+Arrays.toString(cipherMatrix));
		
		return "";
	}
	
	

}
