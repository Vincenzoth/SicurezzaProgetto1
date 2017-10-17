package esercizio4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import esercizio1.Hill;
import esercizio1.KeyPlainText;
import esercizio1.MyException;
import esercizio3.KnowPlainText;

public class CryptAnalysis {

	private Map<String, Integer> frequencyMap;
	private ArrayList<String> knownWords;

	public CryptAnalysis(String frequencyFile) {
		frequencyMap = loadFrequencyDouble(frequencyFile);				
		
		knownWords=loadDictionary();
		
	}
	
	public ArrayList<KeyPlainText> decipher(String filename, int numBigrams) {
		ArrayList<KeyPlainText> possiblePairs = new ArrayList<KeyPlainText>();
		
		KnowPlainText kpt = new KnowPlainText();
		Hill cipher = new Hill();

		String cypherText = readText(filename);

		Map<String, Integer> occurencyMap = countDoubleOccurency(cypherText);


		Iterator<Entry<String, Integer>> iter1 = frequencyMap.entrySet().iterator();
		Iterator<Entry<String, Integer>> iter2 = occurencyMap.entrySet().iterator();

		String composeFreq;
		String composeCipher;
		String possiblePlainText;
		String possibleKey;
		ArrayList<String> possibleWords;
		int maxCommon = 0;
		KeyPlainText bestKeyPlainText = null;
		
		String[] bigramsFreq = new String[numBigrams];
		String[] bigramsCipher = new String[numBigrams];
		
		for(int i = 0; i < numBigrams; i++) {
			bigramsFreq[i] = iter1.next().getKey();
			bigramsCipher[i] = iter2.next().getKey();
		}

		for (int i = 1; i < numBigrams; i++){
			for(int j = 0; j < i; j++) {
				if(bigramsFreq[i] != bigramsFreq[j]) {
					composeFreq = bigramsFreq[i] + bigramsFreq[j];

					for(int x = 0; x < numBigrams; x++)
						for(int z = 0; z < numBigrams; z++) {
							if(bigramsCipher[x] != bigramsCipher[z]) {
								composeCipher = bigramsCipher[x] + bigramsCipher[z];

								try {
									// known plain
									kpt.setPlainText(composeFreq);
									kpt.setCipherText(composeCipher);
									possibleKey = kpt.attack();
									cipher.setKey(possibleKey);
									possiblePlainText = cipher.Dec(cypherText);
									if(isValidTest(possiblePlainText)) {
										// add to possible pairs
										KeyPlainText newPairs = new KeyPlainText(possibleKey, possiblePlainText);
										boolean isNew = true;
										for(KeyPlainText pair: possiblePairs)
											if(pair.getKey().equals(newPairs.getKey()))
												isNew = false;
										if(isNew)
											possiblePairs.add(newPairs);
										
										
										// Count known words
										possibleWords = new ArrayList<String>( Arrays.asList(possiblePlainText.split(" ")));
										possibleWords.retainAll(knownWords);
										if(possibleWords.size() > maxCommon) {
											maxCommon = possibleWords.size();
											if(bestKeyPlainText == null)
												bestKeyPlainText = new KeyPlainText(possibleKey, possiblePlainText);
											else {
												bestKeyPlainText.setKey(possibleKey);
												bestKeyPlainText.setPlainText(possiblePlainText);
											}										
										}
									}
									
								} catch (MyException e) {
									// Chiave non valida
								}				
								
							}
						}
				}
			}
		}
		
		if(possiblePairs.size() != 0)
			possiblePairs.add(0, bestKeyPlainText);
		
		return possiblePairs;
	}
	
	private boolean isValidTest(String text) {
		// controllo parole lunghe
		ArrayList<String> words = new ArrayList<String>( Arrays.asList(text.split(" ")));
		
		int maxLength = 0;			
		for(String word: words) {
			if(word.length() > maxLength)
				maxLength = word.length();
		}
		
		if(maxLength > 30)
			return false;
		
		// confronto parole ottenute con gli articoli
		words.retainAll(knownWords);
		
		// verifico che almeno il 2.5% delle parole sia tra quelle conosciute
		if(words.size() >= text.length()*2.5/100)
			return true;
		
		return false;
	}

	private Map<String, Integer> loadFrequencyDouble(String filename) {		//
		Map<String, Integer> frequencyMap = new HashMap<String, Integer>();
		File file = new File(Paths.get(System.getProperty("user.dir")+filename).toString());
		Scanner input;
		String symbol = "";

		try {
			input = new Scanner(file);
			while (input.hasNextLine()) {
				List<String> tmp = new ArrayList<String>();			 
				tmp.addAll(Arrays.asList(input.nextLine().split("\t")));				 
				if(Hill.encAlphabet.containsKey(tmp.get(0))&&Hill.encAlphabet.containsKey(tmp.get(1))) {					 
					symbol=tmp.remove(0)+tmp.remove(0);					 
					int sum=0;				 	 
					for(String element:tmp){						  
						sum += Integer.parseInt(element); 						
					}					 				 	
					frequencyMap.put(symbol, sum/tmp.size());				 
				}

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		frequencyMap = sortByValue(frequencyMap);
		System.out.println(Arrays.toString(frequencyMap.entrySet().toArray()));		
		return frequencyMap;	
	}

	private Map<String, Integer> countDoubleOccurency(String cipherText){
		Map<String, Integer> occurencyMap = new HashMap<String, Integer>();

		for(String s : cipherText.split("(?<=\\G.{2})")) {
			//if((int)c!=10 && (int)c!=13) {				
			if(occurencyMap.containsKey(s))
				occurencyMap.put(s, occurencyMap.get(s)+1);
			else
				occurencyMap.put(s, 1);
			//}
		}

		occurencyMap = sortByValue(occurencyMap);
		System.out.println(Arrays.toString(occurencyMap.entrySet().toArray()));
		return occurencyMap;

	}

	private static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {

		// 1. Convert Map to List of Map
		List<Map.Entry<String, Integer>> list =
				new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

		// 2. Sort list with Collections.sort(), provide a custom Comparator
		//    Try switch the o1 o2 position for a different order
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
					Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		/*
        //classic iterator example
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }*/


		return sortedMap;
	}

	private String readText(String filename) {
		//List<String> cypherList = new ArrayList<String>();
		String cypherText = "";

		byte[] b;
		try {
			b = Files.readAllBytes(Paths.get(System.getProperty("user.dir") + filename));
			cypherText = new String(b, Charset.defaultCharset());
			cypherText=cypherText.replaceAll("\n", "");

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return cypherText;

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
