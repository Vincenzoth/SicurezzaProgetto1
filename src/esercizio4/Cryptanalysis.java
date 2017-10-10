package esercizio4;

import java.io.File;
import java.io.FileNotFoundException;
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
import java.util.TreeMap;

import esercizio1.Hill;

public class Cryptanalysis {

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
	
	public Map<String, Integer> loadFrequencyBigram(String filename) {		//
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
			e.printStackTrace();
		}
		
		frequencyMap = sortByValue(frequencyMap);
		System.out.println(" ---  Frequenza bigrammi inglese ---");
		System.out.println(Arrays.toString(frequencyMap.entrySet().toArray()));		
		return frequencyMap;	
	}
	
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
	
	public Map<String, Integer> countBigrameOccurency(String cypherText){
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
		System.out.println(" ---  Frequenza bigrammi testo ---");
		System.out.println(Arrays.toString(occurencyMap.entrySet().toArray()));
		return occurencyMap;
		
	}
	
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
	
	public String substitutionBigram(String cypherText, String filename) {		
		String text="";
		
		Map<String, Integer> frequencyMap = loadFrequencyBigram(filename);
		
		Map<String, Integer> occurencyMap = countBigrameOccurency(cypherText);
		
		Map<String, String> substitutionMap = new HashMap<String, String>();
				
		Iterator<Entry<String, Integer>> iter1 = frequencyMap.entrySet().iterator();
		Iterator<Entry<String, Integer>> iter2 = occurencyMap.entrySet().iterator();
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
		
		return text;
		
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
}
