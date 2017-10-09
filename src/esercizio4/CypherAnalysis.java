package esercizio4;

import java.io.File;
import java.io.FileNotFoundException;
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

public class CypherAnalysis {

	public Map<Character, Integer> loadFrequency(String filename) {
		//Map<String, List<String>> doubleMap = new HashMap<String, List<String>>();
		Map<Character, Integer> occurencyMap = new HashMap<Character, Integer>();
		File file = new File(filename);
		Scanner input;
		char symbol = 0;
		
		//E che ne saccio.........
		occurencyMap.put(" ".charAt(0), 99999999);
		
		try {
			input = new Scanner(file);
			 while (input.hasNextLine()) {
				 List<String> tmp = new ArrayList<String>();			 
				 tmp.addAll(Arrays.asList(input.nextLine().split("\t")));
				 if(Hill.encAlphabet.containsKey(tmp.get(0))) {
					 symbol=tmp.remove(0).charAt(0);
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
	
	public Map<Character, Integer> countSingleOccurency(String cypherText){
		Map<Character, Integer> occurencyMap = new HashMap<Character, Integer>();
		
		for(char c : cypherText.toCharArray()) {
			if((int)c!=10 && (int)c!=13) {				
				if(occurencyMap.containsKey(c))
					occurencyMap.put(c, occurencyMap.get(c)+1);
				else
					occurencyMap.put(c, 1);
			}
		}
		
		occurencyMap = sortByValue(occurencyMap);
		System.out.println(Arrays.toString(occurencyMap.entrySet().toArray()));
		return occurencyMap;
		
	}

	public String substitution(String cypherText, String filename) {		
		String text="";
		
		Map<Character, Integer> frequencyMap = loadFrequency(filename);
		Map<Character, Integer> occurencyMap = countSingleOccurency(cypherText);
		
		Map<Character, Character> substitutionMap = new HashMap<Character, Character>();
				
		Iterator<Entry<Character, Integer>> iter1 = frequencyMap.entrySet().iterator();
		Iterator<Entry<Character, Integer>> iter2 = occurencyMap.entrySet().iterator();
		while(iter1.hasNext() || iter2.hasNext()) {
		  Entry<Character, Integer> e1 = iter1.next();
		  Entry<Character, Integer> e2 = iter2.next();
		  substitutionMap.put(e2.getKey(), e1.getKey());
		}
		
		for(char c : cypherText.toCharArray()) {
			if((int)c!=10 && (int)c!=13) {	
				text +=substitutionMap.get(c);
				
			}
		}
		
		System.out.println(Arrays.toString(substitutionMap.entrySet().toArray()));
		System.out.println(frequencyMap.size());
		System.out.println(occurencyMap.size());
		System.out.println(cypherText);
		System.out.println(text);
		return text;
		
	}
	
	private static Map<Character, Integer> sortByValue(Map<Character, Integer> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<Character, Integer>> list =
                new LinkedList<Map.Entry<Character, Integer>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<Character, Integer>>() {
            public int compare(Map.Entry<Character, Integer> o1,
                               Map.Entry<Character, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<Character, Integer> sortedMap = new LinkedHashMap<Character, Integer>();
        for (Map.Entry<Character, Integer> entry : list) {
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
