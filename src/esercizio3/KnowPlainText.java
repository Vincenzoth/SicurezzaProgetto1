package esercizio3;

import esercizio1.Hill;
import esercizio1.MyException;

public class KnowPlainText {
	
	private String plainText;
	private String cipherText;
	
	public KnowPlainText() {		

	}
	
	public KnowPlainText(String plainText, String cipherText) {		
		this.plainText = plainText;
		this.cipherText = cipherText;
	}
	
	public void setPlainText(String plainText) {
		this.plainText = plainText;
	}
	
	public void setCipherText(String cipherText) {
		this.cipherText = cipherText;
	}
	
	private void encodeText(String plain, String cipher, int[] plainMatrix, int[] cipherMatrix, int shift) {		
		
		for (int i = 0; i < 4; i++){			
			plainMatrix[i] = Hill.encAlphabet.get(plain.substring(i+shift, i+1+shift));
			cipherMatrix[i] = Hill.encAlphabet.get(cipher.substring(i+shift, i+1+shift));
		}
		
		
	}
	
	private String decodeKey(int[] matrix) {		
		
		String key = "";
		for (int i = 0; i < 4; i++){			
			key += Hill.decAlphabet.get(matrix[i]);
		}
		
		return key;
		
	}
	
	
	public String attack() throws MyException {
		int[] P = new int[4];
		int [] C = new int[4];
		
		encodeText(plainText, cipherText, P, C, 0);
		
		Hill hill=new Hill();
		
		// calcolo del determinante dell'equazione
		int[][] detMatrix = {{P[0], P[1]}, {P[2], P[3]}};
		int det = hill.det(detMatrix);
		
		//shift plainText e cipherText se det==0
		for(int i = 2; det==0 && i<=plainText.length(); i+=2) {
			
			if(i==plainText.length())
				throw new MyException("Impossibile trovare chiavi");
			
			encodeText(plainText, cipherText, P, C, i);
			detMatrix = new int[][]{{P[0], P[1]}, {P[2], P[3]}};
			det = hill.det(detMatrix);
		}
				
		// Utilizzo il metodo di Cramer per la soluzione delle equazioni
		int[][] detMatrixA = {{C[0], P[1]}, {C[2], P[3]}};
		int[][] detMatrixB = {{C[1], P[1]}, {C[3], P[3]}};
		int[][] detMatrixC = {{P[0], C[0]}, {P[2], C[2]}};
		int[][] detMatrixD = {{P[0], C[1]}, {P[2], C[3]}};

		// Calcolo dei determinanti delle matrici del numeratore
		float[] detValues = new float[4];
		detValues[0] = hill.det(detMatrixA);
		detValues[1] = hill.det(detMatrixB);
		detValues[2] = hill.det(detMatrixC);
		detValues[3] = hill.det(detMatrixD);
		
		// Calcolo dei coefficienti delle equazioni e successiva conversione in mod29
		int[] modValues = new int[4];		
		for(int i=0; i<4; i++) {
			float tmpValues = detValues[i]/det;
			if((int)tmpValues == tmpValues) {
				modValues[i] = hill.mod((int)tmpValues, 29);
			}
			else {
				modValues[i] = hill.mod( hill.mod((int)detValues[i], 29) * hill.modInverse(det), 29);
			}
		}		
		
		return decodeKey(modValues);
	}
	
	

}
