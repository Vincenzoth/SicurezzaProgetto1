package esercizio2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import esercizio1.Hill;
import esercizio1.MyException;

public class BruteForceAttack {
	private static final int MOD = 29; 
	private Hill cipher;
	private ArrayList<String> articles;

	public BruteForceAttack() {
		cipher = new Hill();

		articles = new ArrayList<String>();
		// ITALIANO
		articles.add("il");
		articles.add("lo");
		articles.add("la");
		//articles.add("i");
		articles.add("gli");
		articles.add("le");
		articles.add("un");
		articles.add("una");
		articles.add("uno");
		articles.add("del");
		articles.add("dello");
		articles.add("dei");
		articles.add("degli");
		articles.add("della");
		articles.add("delle");
		articles.add("in");
		articles.add("con");
		//articles.add("su");
		articles.add("per");
		articles.add("tra");
		articles.add("fra");
		articles.add("non");
		articles.add("nei");


		//INGLESE
		articles.add("of");
		articles.add("on");
		articles.add("is");
		articles.add("the");
		//articles.add("a");
		articles.add("an");
		articles.add("and");
	}

	public ArrayList<KeyPlainText> run(String cipherText) {	
		String possibleKey;
		String possiblePlainText;
		ArrayList<KeyPlainText> possiblePairs = new ArrayList<KeyPlainText>();
		int maxCommon = 0;
		KeyPlainText keyPlainText = null;
		
		for(int i = 0; i < MOD; i++)
			for(int j = 0; j < MOD; j++)
				for(int k = 0; k < MOD; k++)
					for(int z = 0; z < MOD; z++) {
						possibleKey = computeKey(i, j, k, z);
						try {
							cipher.setKey(possibleKey);

							possiblePlainText = cipher.Dec(cipherText);
							
							ArrayList<String> possibleWords = new ArrayList<String>(Arrays.asList(possiblePlainText.split(" ")));
							possibleWords.retainAll(articles);
							
							if(possibleWords.size() > maxCommon) {
								maxCommon = possibleWords.size();
								//System.out.println(possibleWords);
								keyPlainText = new KeyPlainText(possibleKey, possiblePlainText);
							}
							
							//if(validateText(possiblePlainText))
							//	possiblePairs.add(new KeyPlainText(possibleKey, possiblePlainText));													

						} catch (MyException e) {					

						}						
					}
		System.out.println("Elementi comuni trovati: "+maxCommon+" con chiave: "+keyPlainText.getKey());
		possiblePairs.add(keyPlainText);
		
		/*
		if(possiblePairs.size() == 0) {
			if(! articles.contains("i")) {
				articles.add("i");
				possiblePairs = run(cipherText);
			}
		}
		*/

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
		boolean articleIn = false;
		int countArt = 0;

		for(String word: possibleWords) {
			// non possono esserci virgole in mezzo a una parola
			if( word.length() > 1 && word.substring(0, word.length()-1).contains(","))
				return false;
				

			// la frase dovrebbe contenere almeno un articolo
			if(articles.contains(word)) {
				countArt++;
				if(countArt == 2)
					articleIn = true;
			}

			meanLength = meanLength + word.length();

			if(word.length() > maxLength)
				maxLength = word.length();
		}
		meanLength = meanLength/possibleWords.length;

		if(possibleWords.length < 15) {
			//	retrurn false;
		}
		if(meanLength > 6) {
			return false;
		}
		if(maxLength > 30) {
			//return false;
		}


		return articleIn;
	}
}