package esercizio2;

import java.util.ArrayList;

import esercizio1.Hill;
import esercizio1.MyException;

public class BruteForceAttack {
	private static final int MOD = 29; 
	private Hill cipher;

	public BruteForceAttack() {
		cipher = new Hill();
	}

	public ArrayList<KeyPlainText> run(String cipherText) {	
		String possibleKey;
		String possiblePlainText;
		ArrayList<KeyPlainText> possiblePairs = new ArrayList<KeyPlainText>();

		int contVal = 0;
		int contInval = 0;

		for(int i = 0; i < MOD; i++)
			for(int j = 0; j < MOD; j++)
				for(int k = 0; k < MOD; k++)
					for(int z = 0; z < MOD; z++) {
						possibleKey = computeKey(i, j, k, z);
						//possibleKey = "alto";
						try {
							cipher.setKey(possibleKey);
							
							possiblePlainText = cipher.Dec(cipherText);

							if(validateText(possiblePlainText))
								possiblePairs.add(new KeyPlainText(possibleKey, possiblePlainText));													

							contVal++;
						} catch (MyException e) {					
							contInval++;
						}						
					}
		System.out.println("Chiavi valide: "+contVal);
		System.out.println("Chiavi non valide: "+contInval);
		System.out.println("Chiavi tot: "+(contVal+contInval));

		return possiblePairs;
	}

	private String computeKey(int a, int b, int c, int d) {
		String key = Hill.decAlphabet.get(a) 
				+ Hill.decAlphabet.get(b)
				+ Hill.decAlphabet.get(c)
				+ Hill.decAlphabet.get(d);

		return key;
	}

	private boolean validateText(String possiblePlainText) {
		String[] possibleWords = possiblePlainText.split(" ");
		int meanLength = 0;
		int maxLength = 0;
		
		String[] articoli = {"il","lo","la","i","gli","le"};
		
		
			
			
		for(String word: possibleWords) {
			// non possono esserci virgole in mezzo a una parola
			if( word.length() > 1 && word.substring(0, word.length()-1).contains(","))
				return false;
				
			meanLength = meanLength + word.length();

			if(word.length() > maxLength)
				maxLength = word.length();
			
			
		}
		meanLength = meanLength/possibleWords.length;

		if(possibleWords.length < 4)
			return false;
		if(meanLength > 5) {
			return false;
		}
		if(maxLength > 7) {
			return false;
		}


		return true;
	}
}
