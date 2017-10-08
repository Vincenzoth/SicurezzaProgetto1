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
		int [] P = new int[4];
		int [] C = new int[4];
		
		encodeText(plainText, cipherText, P, C);
		
		Hill hill=new Hill();
		
		int[][] detMatrix = {{P[0], P[1]}, {P[2], P[3]}};
		int[][] detMatrixA = {{C[0], P[1]}, {C[2], P[3]}};
		int[][] detMatrixB = {{P[0], C[0]}, {P[2], C[2]}};
		int[][] detMatrixC = {{C[1], P[1]}, {C[3], P[3]}};
		int[][] detMatrixD = {{P[0], C[1]}, {P[2], C[3]}};
		
		int det = hill.det(detMatrix);
		if(det==0)
			return "impossibile";
		
		float[] detValues = new float[4];
		detValues[0] = hill.det(detMatrixA);
		detValues[1] = hill.det(detMatrixB);
		detValues[2] = hill.det(detMatrixC);
		detValues[3] = hill.det(detMatrixD);
		
		int[] modValues = new int[4];
		
		for(int i=0; i<4; i++) {
			float tmpValues = (float) detValues[i]/det;
			if((int)tmpValues == tmpValues) {
				modValues[i] = hill.mod((int)tmpValues, 29);
			}
			else {
				modValues[i] = hill.mod(hill.mod((int)detValues[i], 29)*hill.modInverse(det),29);
			}
		}
		
		System.out.println(Arrays.toString(modValues));
		
		return "";
	}
	
	

}
