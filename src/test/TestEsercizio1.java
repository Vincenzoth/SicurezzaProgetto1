package test;

import java.util.ArrayList;

import esercizio1.Hill;
import esercizio1.MyException;
import esercizio2.BruteForceAttack;
import esercizio2.KeyPlainText;

public class TestEsercizio1 {
	public static void main(String [] args) {
		System.out.println("---  Test esercizio 1  ----------------------------- ");
		System.out.println("---------------------------------------------------- ");
		Hill cipher = new Hill();
		
		System.out.println("\n   - Generare una chiave:");
		String key = cipher.genKey();
		
		System.out.println("            \""+key+"\"");
		
		System.out.println("\n   - Imposta la chiave:");
		try {
			cipher.setKey("alto");
			//cipher.setKey(key);
			System.out.println("             Chiave impostata!");
		} catch (MyException e) {
			System.out.println("             ERRORE !!  "+e.getMessage());
		}
		
		System.out.println("\n   - Criptare la frase \"i topi non avevano nipoti\":");
		String plainText = "i topi non avevano nipoti";
		String cipherText = cipher.Enc(plainText);
		System.out.println("            testo cifrato: \""+cipherText+"\"");
		
		System.out.println("\n   - decriptare la frase \""+cipherText+"\":");
		//String plainText = "i topi non avevano nipoti";
		String plainText2 = cipher.Dec(cipherText);
		System.out.println("            testo decodificato: \""+plainText2+"\"");
		
		
		System.out.println("\n\n---  Test esercizio 1  ----------------------------- ");
		System.out.println("---------------------------------------------------- ");
		
		String cipherToAttack = "iuaavhsgemtoftmrxpsgj ipiu";
		//String cipherToAttack = "kgeprrm ,gilzhpn,fhcaposvv,rqrp'pwwdj vb,gkgklweshwmqrosvvzolwilrfxpgoezfnkldiqs";
		BruteForceAttack brtfrz = new BruteForceAttack();
		ArrayList<KeyPlainText> pairs = brtfrz.run(cipherToAttack);
		
		
		for(KeyPlainText pair: pairs) {
			System.out.println("testo decriptato: "+pair.getPlainText());
		}
		
		System.out.println("Possibili coppie trovate: "+ pairs.size());
	}

}
