package esercizio2;

import java.util.ArrayList;
import java.util.Arrays;

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
		articles.add("i");
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
		articles.add("su");
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
		articles.add("a");
		articles.add("an");
		articles.add("and");
	}

	public KeyPlainText run(String cipherText) {	
		String possibleKey;
		String possiblePlainText;
		ArrayList<KeyPlainText> possiblePairs = new ArrayList<KeyPlainText>();
		ArrayList<String> possibleWords;
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
							
							possibleWords = new ArrayList<String>( Arrays.asList(possiblePlainText.split(" ")));
							possibleWords.retainAll(articles);
							
							if(possibleWords.size() > maxCommon) {
								maxCommon = possibleWords.size();
								
								if(keyPlainText == null)
									keyPlainText = new KeyPlainText(possibleKey, possiblePlainText);
								else {
									keyPlainText.setKey(possibleKey);
									keyPlainText.setPlainText(possiblePlainText);
								}
							}												

						} catch (MyException e) {					

						}						
					}
		possiblePairs.add(keyPlainText);

		return keyPlainText;
	}

	private String computeKey(int a, int b, int c, int d) {
		String key = Hill.decAlphabet.get(a) 
				+ Hill.decAlphabet.get(b)
				+ Hill.decAlphabet.get(c)
				+ Hill.decAlphabet.get(d);

		return key;
	}
}