package esercizio2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import esercizio1.Hill;
import esercizio1.KeyPlainText;
import esercizio1.MyException;

public class BruteForceAttack {
	private static final int MOD = 29; 
	private Hill cipher;
	private ArrayList<String> knownWords;

	/**
	 * Costruttore della classe.
	 */
	public BruteForceAttack() {
		cipher = new Hill();


		knownWords = new ArrayList<String>();
		knownWords = loadDictionary();
		
		/*
		// ITALIANO
		knownWords.addAll(Arrays.asList("il","lo","la","i","gli","le"));
		knownWords.addAll(Arrays.asList("un","una","uno"));
		knownWords.addAll(Arrays.asList("del","dello","dei","degli","della","delle","dell'"));
		knownWords.addAll(Arrays.asList("in","con","su","per","tra","fra"));
		knownWords.addAll(Arrays.asList("non","nei"));

		//INGLESE
		knownWords.addAll(Arrays.asList("the","a","an","some","any"));
		knownWords.addAll(Arrays.asList("is","are","was","have","has","had"));
		knownWords.addAll(Arrays.asList("in","on","at"));
		knownWords.addAll(Arrays.asList("of","lot","most"));
		knownWords.addAll(Arrays.asList("and","or","but","so","because"));		
		*/
	}
	
	/**
	 * Il metodo implementa un attacco di forza bruta per rompere il cifrario di Hill.	 
	 * @param cipherText
	 * @return arrayList delle coppie "ciave"-"testo in chiaro", la coppia in posizione 0 è la coppia che con probabilità maggiore è quella che decifra il testo in ingresso
	 */
	public ArrayList<KeyPlainText> attack(String cipherText) {	
		String possibleKey;
		String possiblePlainText;
		ArrayList<KeyPlainText> possiblePairs = new ArrayList<KeyPlainText>();
		ArrayList<String> possibleWords;
		int maxCommon = 0;
		KeyPlainText keyPlainText = null;
		KeyPlainText BestKeyPlainText = null;

		for(int i = 0; i < MOD; i++)
			for(int j = 0; j < MOD; j++)
				for(int k = 0; k < MOD; k++)
					for(int z = 0; z < MOD; z++) {
						possibleKey = computeKey(i, j, k, z);
						try {
							cipher.setKey(possibleKey);
							possiblePlainText = cipher.Dec(cipherText);

							if(isValidText(possiblePlainText)) {
								// Aggiungi a frasi possibili
								if(keyPlainText == null)
									keyPlainText = new KeyPlainText(possibleKey, possiblePlainText);
								else {
									keyPlainText.setKey(possibleKey);
									keyPlainText.setPlainText(possiblePlainText);
								}
								possiblePairs.add(new KeyPlainText(possibleKey, possiblePlainText));

								possibleWords = new ArrayList<String>( Arrays.asList(possiblePlainText.split(" ")));
								possibleWords.retainAll(knownWords);

								if(possibleWords.size() > maxCommon) {
									maxCommon = possibleWords.size();

									if(BestKeyPlainText == null)
										BestKeyPlainText = new KeyPlainText(possibleKey, possiblePlainText);
									else {
										BestKeyPlainText.setKey(possibleKey);
										BestKeyPlainText.setPlainText(possiblePlainText);
									}
								}	
							}

						} catch (MyException e) {
							// Chiave di test non valida!
						}						
					}

		if(possiblePairs.size() == 0 && !knownWords.contains("i")) {
			knownWords.add("i");
			possiblePairs = attack(cipherText);
		}
		else {
			possiblePairs.add(0, BestKeyPlainText);
		}

		return possiblePairs;
	}

	/**
	 * Il metodo verifica se il testo passato come parametro può essere un teso di senso compiuto nella ingua italiana o inglese. 
	 * @param text 
	 * @return
	 */
	private boolean isValidText(String text) {
		String[] words = text.split(" ");
		int maxLength = 0;
		int meanLenght = 0;
		int numKnownWords = 0;

		for(String word: words) {
			// test virgole in mezzo alle parole
			if(word.length() > 2 && word.substring(0, (word.length()-1)).contains(","))
				return false;

			if(knownWords.contains(word))
				numKnownWords++;

			if(word.length() > maxLength)
				maxLength = word.length();

			meanLenght = meanLenght + word.length();

		}

		if(maxLength > 30)
			return false;
		if (numKnownWords < 2)
			return false;

		meanLenght = meanLenght/words.length;
		if (meanLenght > 6)
			return false;


		return true;
	}

	/**
	 * Il metodo calcola e restituisce la stringa della chiave a patire dai quattro interi passati come parametro 
	 * @param ch1
	 * @param ch2
	 * @param ch3
	 * @param ch4
	 * @return
	 */
	private String computeKey(int ch1, int ch2, int ch3, int ch4) {
		String key = Hill.decAlphabet.get(ch1) 
				+ Hill.decAlphabet.get(ch2)
				+ Hill.decAlphabet.get(ch3)
				+ Hill.decAlphabet.get(ch4);

		return key;
	}
	
	private ArrayList<String> loadDictionary() {
		ArrayList<String> dictionary = new ArrayList<String>();
		
		byte[] italian;
		byte[] english;
		
		try {
			italian = Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/text/dizionario.txt"));			
			english = Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/text/dictionary.txt"));
			
			byte[] combined = new byte[italian.length + english.length];

			System.arraycopy(italian, 0, combined, 0, italian.length);
			System.arraycopy(english, 0, combined, italian.length, english.length);			
			dictionary.addAll(Arrays.asList(new String(combined, Charset.defaultCharset()).split("\r\n")));			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		return dictionary;	
	}
}