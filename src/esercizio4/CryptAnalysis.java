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
import esercizio1.MyException;
import esercizio3.KnowPlainText;

public class CryptAnalysis {

	private Map<String, Integer> frequencyMap;
	private ArrayList<String> articles;

	public CryptAnalysis(String frequencyFile) {
		frequencyMap = loadFrequencyDouble(frequencyFile);
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

	private Map<String, Integer> loadFrequencyDouble(String filename) {		//
		Map<String, Integer> frequencyMap = new HashMap<String, Integer>();
		File file = new File(Paths.get(System.getProperty("user.dir")+filename).toString());
		Scanner input;
		String symbol = "";

		//E che ne saccio.........
		//occurencyMap.put(" ", 6859483);

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

	private Map<String, Integer> countDoubleOccurency(String cypherText){
		Map<String, Integer> occurencyMap = new HashMap<String, Integer>();

		for(String s : cypherText.split("(?<=\\G.{2})")) {
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

	public String decipher(String filename) {		
		String text="";	
		KnowPlainText kpt = new KnowPlainText();
		Hill cipher = new Hill();

		String cypherText=readText(filename);

		Map<String, Integer> occurencyMap = countDoubleOccurency(cypherText);

		//Map<String, String> substitutionMap = new HashMap<String, String>();

		Iterator<Entry<String, Integer>> iter1 = frequencyMap.entrySet().iterator();
		Iterator<Entry<String, Integer>> iter2 = occurencyMap.entrySet().iterator();

		int ITER = 5;
		String composeFreq;
		String composeCipher;
		String testDec;
		String possibleKey;
		
		String[] bigramsFreq = new String[ITER];
		String[] bigramsCipher = new String[ITER];
		
		for(int i = 0; i < ITER; i++) {
			bigramsFreq[i] = iter1.next().getKey();
			bigramsCipher[i] = iter2.next().getKey();
		}

		for (int i = 0; i < ITER; i++){
			for(int j = 0; j < ITER; j++) {
				if(bigramsFreq[i] != bigramsFreq[j]) {
					composeFreq = bigramsFreq[i] + bigramsFreq[j];

					for(int x = 0; x < ITER; x++)
						for(int z = 0; z < ITER; z++) {
							if(bigramsCipher[x] != bigramsCipher[z]) {
								composeCipher = bigramsCipher[x] + bigramsCipher[z];

								// ora provo known plain
								kpt.setPlainText(composeFreq);
								kpt.setCipherText(composeCipher);

								possibleKey = kpt.attack();
								try {
									cipher.setKey(possibleKey);
									testDec = cipher.Dec(cypherText);
									if(isValidTest(testDec)) {
										System.out.println("\n"+composeFreq+ " -> " + composeCipher);
										System.out.println("Chiave: "+possibleKey);
										System.out.println(testDec);
										System.out.println();
									}
									
								} catch (MyException e) {
									// Chiave non valida
								}				
								
							}
						}
				}
			}
		}


		/*

		  	while(iter1.hasNext() && iter2.hasNext()) {
			Entry<String, Integer> e1 = iter1.next();		  
			Entry<String, Integer> e2 = iter2.next();		  
			substitutionMap.put(e2.getKey(), e1.getKey());
		}

		 for(String s : cypherText.split("(?<=\\G.{2})")) {			
			text +=substitutionMap.get(s);
		}

		System.out.println(Arrays.toString(substitutionMap.entrySet().toArray()));
		System.out.println(frequencyMap.size());
		System.out.println(occurencyMap.size());
		System.out.println(substitutionMap.size());
		System.out.println(cypherText);
		System.out.println(text);
		 */

		return text;

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
		words.retainAll(articles);
		
		// ho impostato un numero minimo di 20 articoli nel testo per scartare le altre opzioni
		if(words.size() >= 20)
			return true;
		
		return false;
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

	/*
	public Map<String, Integer> loadFrequencySingle(String filename) {
		//Map<String, List<String>> doubleMap = new HashMap<String, List<String>>();
		Map<String, Integer> occurencyMap = new HashMap<String, Integer>();
		File file = new File(Paths.get(System.getProperty("user.dir")+filename).toString());
		Scanner input;
		String symbol = "";

		//E che ne saccio.........
		occurencyMap.put(" ", 99999999);

		try {
			input = new Scanner(file);
			 while (input.hasNextLine()) {
				 List<String> tmp = new ArrayList<String>();			 
				 tmp.addAll(Arrays.asList(input.nextLine().split("\t")));
				 if(Hill.encAlphabet.containsKey(tmp.get(0))) {
					 symbol=tmp.remove(0);
				 	 int sum=0;
					 for(String element:tmp){						  
					    sum += Integer.parseInt(element); 						
					 }				 		 
					 occurencyMap.put(symbol, sum/tmp.size());				 
				 }

			 }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		occurencyMap = sortByValue(occurencyMap);
		System.out.println(Arrays.toString(occurencyMap.entrySet().toArray()));		
		return occurencyMap;	
	}
	 */

	/*
	public Map<String, Integer> countSingleOccurency(String cypherText){
		Map<String, Integer> occurencyMap = new HashMap<String, Integer>();

		for(String c : cypherText.split("")) {				
			if(occurencyMap.containsKey(c))
				occurencyMap.put(c, occurencyMap.get(c)+1);
			else
				occurencyMap.put(c, 1);			
		}

		occurencyMap = sortByValue(occurencyMap);
		System.out.println(Arrays.toString(occurencyMap.entrySet().toArray()));
		return occurencyMap;

	}
	 */

	/*
	public String substitutionSingle(String cypherText, String filename) {		
		String text="";

		Map<String, Integer> frequencyMap = loadFrequencySingle(filename);
		cypherText = "cfdbvtf fbdi mfuufs jo uif nfttbhf ibt b ejsfdu usbotmbujpo up bopuifs mfuufs gsfrvfodz bobmztjt dbo cf vtfe up efdjqifs uif nfttbhf gps fybnqmf uif mfuufs f jt uif nptu dpnnpomz vtfe mfuufs jo uif fohmjti mbohvbhf uivt jg uif nptu dpnnpo mfuufs jo b tfdsfu nfttbhf jt l ju jt mjlfmz uibu l sfqsftfout f beejujpobmmz dpnnpo xpse foejoht tvdi bt joh mz boe ft bmtp hjwf dmvft";
		Map<String, Integer> occurencyMap = countSingleOccurency(cypherText);

		Map<String, String> substitutionMap = new HashMap<String, String>();

		Iterator<Entry<String, Integer>> iter1 = frequencyMap.entrySet().iterator();
		Iterator<Entry<String, Integer>> iter2 = occurencyMap.entrySet().iterator();
		while(iter1.hasNext() && iter2.hasNext()) {
		  Entry<String, Integer> e1 = iter1.next();
		  Entry<String, Integer> e2 = iter2.next();
		  substitutionMap.put(e2.getKey(), e1.getKey());
		}

		for(String c : cypherText.split("")) {			
			text +=substitutionMap.get(c);			
		}

		System.out.println(Arrays.toString(substitutionMap.entrySet().toArray()));
		System.out.println(frequencyMap.size());
		System.out.println(occurencyMap.size());
		System.out.println(cypherText);
		System.out.println(text);
		return text;

	}
	 */
}
