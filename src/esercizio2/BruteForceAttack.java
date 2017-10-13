package esercizio2;

import java.util.ArrayList;
import java.util.Arrays;

import esercizio1.Hill;
import esercizio1.KeyPlainText;
import esercizio1.MyException;

public class BruteForceAttack {
	private static final int MOD = 29; 
	private Hill cipher;
	private ArrayList<String> articles;

	public BruteForceAttack() {
		cipher = new Hill();

		articles = new ArrayList<String>();
		// ITALIANO
		articles.addAll(Arrays.asList("il","lo","la","i","gli","le"));
		articles.addAll(Arrays.asList("un","una","uno"));
		articles.addAll(Arrays.asList("del","dello","dei","degli","della","delle","dell'"));
		articles.addAll(Arrays.asList("in","con","su","per","tra","fra"));
		articles.addAll(Arrays.asList("non","nei"));
		
		//INGLESE
		articles.addAll(Arrays.asList("the","a","an","some","any"));
		articles.addAll(Arrays.asList("is","are","was","have","has","had"));
		articles.addAll(Arrays.asList("in","on","at"));
		articles.addAll(Arrays.asList("of","lot","most"));
		articles.addAll(Arrays.asList("and","or","but","so","because"));
			
	}

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
								possibleWords.retainAll(articles);

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

		if(possiblePairs.size() == 0 && !articles.contains("i")) {
			articles.add("i");
			possiblePairs = attack(cipherText);
		}
		else {
			possiblePairs.add(0, BestKeyPlainText);
		}

		return possiblePairs;
	}

	private boolean isValidText(String text) {
		String[] words = text.split(" ");
		int maxLength = 0;
		int meanLenght = 0;
		int numKnownWords = 0;

		for(String word: words) {
			// test virgole in mezzo alle parole
			if(word.length() > 2 && word.substring(0, (word.length()-1)).contains(","))
				return false;

			if(articles.contains(word))
				numKnownWords++;

			if(word.length() > maxLength)
				maxLength = word.length();

			meanLenght = meanLenght + word.length();

		}

		if(maxLength > 25)
			return false;
		if (numKnownWords < 2)
			return false;

		meanLenght = meanLenght/words.length;
		if (meanLenght > 6)
			return false;


		return true;
	}


	private String computeKey(int a, int b, int c, int d) {
		String key = Hill.decAlphabet.get(a) 
				+ Hill.decAlphabet.get(b)
				+ Hill.decAlphabet.get(c)
				+ Hill.decAlphabet.get(d);

		return key;
	}
}