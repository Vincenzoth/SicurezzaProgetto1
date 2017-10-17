package esercizio1;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class Hill implements ClassicCipher{
	public static final int DIM = 2;

	private int keyMatrix[][];
	private int keyMatrixInv[][];
	private String key;
	// due mappe al posto di una bidirezionale
	private String alph = " abcdefghijklmnopqrstuvwxyz,'";
	private int modVal;

	public static Map<Integer,String> decAlphabet;
	public static Map<String,Integer> encAlphabet;

	/**
	 * Costruttore della classe.
	 * Il metodo inizializza le mappe dell'alfameto di codifica.
	 * inoltre inizializza il cifrario generando una chiave valida in maniera casuale.
	 */
	public Hill() {
		// inizializzazione alfabeto
		decAlphabet = new HashMap<Integer,String>();
		encAlphabet = new HashMap<String,Integer>();

		String[] alphArray = alph.split("");		
		for(int i=0; i<alphArray.length;i++) {
			decAlphabet.put(i, alphArray[i]);
			encAlphabet.put(alphArray[i], i);
		}

		modVal = encAlphabet.size();

		// inizializzazione a chiave casuale
		keyMatrix = new int[DIM][DIM];
		try {
			setKey(genKey());
		} catch (MyException e) {
		}
	}

	/**
	 * Il metodo setta la chiave passata come parametro key.
	 * Lancia eccezione se la chiave passata come parametro non è una chiave valida
	 */
	public void setKey(String key) throws MyException {

		if(! checkKey(key))
			throw new MyException("La chiave non è una chiave valida!");

		if(keyMatrixInv != null)
			keyMatrixInv = null;

		if(keyMatrix != null)
			keyMatrix = null;

		// set key string
		this.key = key;

		// set key matrix
		this.keyMatrix = computeKeyMatrix(key);

	}


	/**
	 * Il metodo restituisce la chiave corrente.
	 */
	public String getKey() {
		return this.key;
	}


	/**
	 * Il metodo restituisce una chiave valida generata casualmente.
	 */
	public String genKey() {
		SecureRandom random = new SecureRandom();
		int ranval;
		String genKey = "";

		for(int i = 1; i <= 4; i++) {
			ranval = random.nextInt(28);
			genKey = genKey + decAlphabet.get(ranval);
		}

		if(! checkKey(genKey))
			genKey = genKey();

		return genKey;
	}

	/**
	 * Il metodo codifica la stringa plainText, restituendo la corrispondente stringa codificata.
	 */
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


	/**
	 * Il metodo decodifica la stringa cipherText, restituendo la corrispondente stringa decodificata.
	 */
	public String Dec(String cipherText) {
		// Initialize inverse key
		if(keyMatrixInv == null)
			keyMatrixInv = inverseMatrix(keyMatrix);

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
	 * Il metodo calcola la matrice chiave a partire dalla stringa key
	 */
	private int[][] computeKeyMatrix(String key){
		int[][] outMatrix = new int[DIM][DIM];
		char [] keyArray = key.toCharArray();
		for(int i = 0; i < DIM; i++)
			for(int j = 0; j < DIM; j++)
				outMatrix[i][j] = encAlphabet.get(Character.toString(keyArray[2*i+j]));

		return outMatrix;		
	}

	/**
	 * Il metodo calcola la matrice inversa della matrice matrix passata come parametro.
	 * Le operazione sono effettuate in algebra modulare.
	 */
	private int[][] inverseMatrix(int[][] matrix){
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

	/**
	 * Il metodo calcola il modulo y del numero x
	 */
	public int mod(int n, int mod){
		int result = n % mod;
		return result < 0? result + mod : result;
	}


	/**
	 * Il metodo calcola il determinante della matrice di interi matrix
	 * Le operazione sono effettuate in algebra modulare.
	 */
	public int det(int[][] matrix) {
		int d = matrix[0][0]*matrix[1][1]-matrix[0][1]*matrix[1][0];		
		// fai modulo
		d = mod(d, modVal);
		return d;
	}

	/**
	 * Il metodo inverte il numero n.
	 * Le operazione sono effettuate in algebra modulare.
	 */
	public int modInverse(int n){
		n = n % modVal;
		for (int i = 1; i < modVal; i++)
			if ((n*i) % modVal == 1)
				return i;
		return 0;
	}

	/**
	 * Il metodo verifica che la chiave passata come parametro sia una chiave valida
	 */
	private boolean checkKey(String key) {

		if(key.length() != 4)
			return false;

		int[][] keyMatrix = computeKeyMatrix(key);				

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