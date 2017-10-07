package esercizio1;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class Hill implements ClassicCipher{
	private static final int DIM = 2;

	private int keyMatrix[][];
	private int keyMatrixInv[][];
	private String key;
	// due mappe al posto di una bidirezionale
	public static Map<Integer,String> decAlphabet;
	public static Map<String,Integer> encAlphabet;
	private int modVal;

	public Hill() {
		keyMatrix = new int[DIM][DIM];
		keyMatrix = null;

		decAlphabet = new HashMap<Integer,String>();
		decAlphabet.put(0, " ");
		decAlphabet.put(1, "a");
		decAlphabet.put(2, "b");
		decAlphabet.put(3, "c");
		decAlphabet.put(4, "d");
		decAlphabet.put(5, "e");
		decAlphabet.put(6, "f");
		decAlphabet.put(7, "g");
		decAlphabet.put(8, "h");
		decAlphabet.put(9, "i");
		decAlphabet.put(10, "j");
		decAlphabet.put(11, "k");
		decAlphabet.put(12, "l");
		decAlphabet.put(13, "m");
		decAlphabet.put(14, "n");
		decAlphabet.put(15, "o");
		decAlphabet.put(16, "p");
		decAlphabet.put(17, "q");
		decAlphabet.put(18, "r");
		decAlphabet.put(19, "s");
		decAlphabet.put(20, "t");
		decAlphabet.put(21, "u");
		decAlphabet.put(22, "v");
		decAlphabet.put(23, "w");
		decAlphabet.put(24, "x");
		decAlphabet.put(25, "y");
		decAlphabet.put(26, "z");
		decAlphabet.put(27, ",");
		decAlphabet.put(28, "'");

		encAlphabet = new HashMap<String,Integer>();
		encAlphabet.put(" ", 0);
		encAlphabet.put("a", 1);
		encAlphabet.put("b", 2);
		encAlphabet.put("c", 3);
		encAlphabet.put("d", 4);
		encAlphabet.put("e", 5);
		encAlphabet.put("f", 6);
		encAlphabet.put("g", 7);
		encAlphabet.put("h", 8);
		encAlphabet.put("i", 9);
		encAlphabet.put("j", 10);
		encAlphabet.put("k", 11);
		encAlphabet.put("l", 12);
		encAlphabet.put("m", 13);
		encAlphabet.put("n", 14);
		encAlphabet.put("o", 15);
		encAlphabet.put("p", 16);
		encAlphabet.put("q", 17);
		encAlphabet.put("r", 18);
		encAlphabet.put("s", 19);
		encAlphabet.put("t", 20);
		encAlphabet.put("u", 21);
		encAlphabet.put("v", 22);
		encAlphabet.put("w", 23);
		encAlphabet.put("x", 24);
		encAlphabet.put("y", 25);
		encAlphabet.put("z", 26);
		encAlphabet.put(",", 27);
		encAlphabet.put("'", 28);

		modVal = encAlphabet.size();
	}

	@Override
	public void setKey(String key) throws MyException {
		int[][] testKeyMatrix = computeKeyMatrix(key);

		if(! checkKey(key, testKeyMatrix))
			throw new MyException("La chiave non è una chiave valida!");
		
		if(keyMatrixInv != null)
			keyMatrixInv = null;

		// set key string
		this.key = key;

		// set key matrix
		this.keyMatrix = testKeyMatrix;

	}


	@Override
	public String getKey() {
		return this.key;
	}


	@Override
	public String genKey() {
		SecureRandom random = new SecureRandom();
		int ranval;
		String genKey = "";

		for(int i = 1; i <= 4; i++) {
			ranval = random.nextInt(28);
			genKey = genKey + decAlphabet.get(ranval);
		}

		return genKey;
	}

	@Override
	public String Enc(String plainText) {
		String cipherText = "";
		int[] digVector = new int[DIM];
		String[] digs = plainText.split("(?<=\\G.{"+DIM+"})");
		int i = 0;

		// se l'ultimo digramma non è pieno, cioè c'è un solo carattere, aggiunge uno spazio
		if(digs[digs.length-1].length() == 1)
			digs[digs.length-1] = digs[digs.length-1]+" ";


		for(String dig: digs) {
			i = 0;
			for (char ch: dig.toCharArray()) {
				digVector[i] = encAlphabet.get(Character.toString(ch));
				i++;
			}

			// codifica
			int tmpVal = 0;
			for(int ii = 0; ii < DIM ; ii++) {
				for(int j = 0; j < DIM ; j++)
					tmpVal = tmpVal + (keyMatrix[j][ii] * digVector[j]) ;

				tmpVal = mod(tmpVal, modVal);

				cipherText = cipherText + decAlphabet.get(tmpVal);
				tmpVal = 0;
			}			
		}

		return cipherText;
	}

	@Override

	public String Dec(String cipherText) {
		// Initialize inverse key
		if(keyMatrixInv == null)
			keyMatrixInv = inverseMatric(keyMatrix);

		String plainText = "";
		int[] digVector = new int[DIM];
		String[] digs = cipherText.split("(?<=\\G.{"+DIM+"})");
		int i = 0;

		for(String dig: digs) {
			i = 0;
			for (char ch: dig.toCharArray()) {
				digVector[i] = encAlphabet.get(Character.toString(ch));
				i++;
			}

			// decodifica
			int tmpVal = 0;
			for(int ii = 0; ii < DIM ; ii++) {
				for(int j = 0; j < DIM ; j++)
					tmpVal = tmpVal + (keyMatrixInv[j][ii] * digVector[j]) ;

				tmpVal = mod(tmpVal, modVal);

				plainText = plainText + decAlphabet.get(tmpVal);
				tmpVal = 0;
			}
		}

		return plainText;
	}

	/**
	 * Il metodo calcola la matrice chiave a partire dalla strina key
	 * @param key
	 * @return una matrice di interi
	 */
	private int[][] computeKeyMatrix(String key){
		int[][] outMatrix = new int[DIM][DIM];
		char [] keyArray = key.toCharArray();
		for(int i = 0; i < DIM; i++)
			for(int j = 0; j < DIM; j++)
				outMatrix[i][j] = encAlphabet.get(Character.toString(keyArray[2*i+j]));

		return outMatrix;		
	}

	private int[][] inverseMatric(int[][] matrix){
		int[][] inverse = new int[DIM][DIM];
		int invDet = BigInteger.valueOf(det(matrix)).modInverse(BigInteger.valueOf(modVal)).intValue();

		for(int i = 0; i < DIM; i++)
			for(int j = 0; j < DIM; j++) {
				if(i == j)
					inverse[i][j] =  mod((matrix[DIM-i-1][DIM-j-1] * invDet), modVal) ; 
				else
					inverse[i][j] = mod(((-1) * matrix[i][j] * invDet), modVal) ;					
			}

		return inverse;
	}


	private int mod(int x, int y){
		int result = x % y;
		return result < 0? result + y : result;
	}

	
	/**
	 * Il metodo calcola il determinante della matrice
	 * @param matrix
	 * @return un intero rappresentate il determinante della matrice
	 */
	private int det(int[][] matrix) {
		int d = matrix[0][0]*matrix[1][1]-matrix[0][1]*matrix[1][0];
		// fai modulo
		d = mod(d, modVal);
		return d;
	}

	/**
	 * Il metodo inverte il numero n in modulo 29
	 * @param a
	 * @return
	 */
	private int modInverse(int n){
		n = n % modVal;
		for (int i = 1; i < modVal; i++)
			if ((n*i) % modVal == 1)
				return i;
		return 0;
	}

	/**
	 * Il metodo verifica che la chiave passata come parametro sia una chiave valida
	 * @param key, stringa da testaee
	 * @return true se la chave è valida, flase altrimenti
	 */
	private boolean checkKey(String key, int[][] keyMatrix) {
		if(key.length() != 4)
			return false;

		for (char ch: key.toCharArray()) 
			if(encAlphabet.get(Character.toString(ch)) == null)
				return false;

		// La matrice chiave deve essere invertibile
		if(det(keyMatrix) == 0)
			return false;
		// basta solo questo controllo, il 29 è primo!

		return true;
	}

}
