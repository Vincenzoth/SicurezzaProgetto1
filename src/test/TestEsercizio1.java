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
		
		
		System.out.println("\n\n---  Test esercizio 2  ----------------------------- ");
		System.out.println("---------------------------------------------------- ");
		
		//String cipherToAttack = "iuaavhsgemtoftmrxpsgj ipiu";
		//String cipherToAttack = "kgeprrm ,gilzhpn,fhcaposvv,rqrp'pwwdj vb,gkgklweshwmqrosvvzolwilrfxpgoezfnkldiqs";
		//String cipherToAttack = "gbgbemumlcdvbb,izn qxpmwatoehldvmg qqumnivlw jmwpoeiyxyhnemwu w,u mnjidqo,fddqdvcvswumlcdvcvswumoe";
		//String cipherToAttack = "hv ymkne,dxupzmqojqjtmqjrvlqtw,dtvrvphkcqjpzgzzole ham'bsbcujqbjxppzgzbef'xykrvrml'sampzgzjrrvokmbzobyb,qbpzgzjr'be,d bcgwleeknvwffqbjrqhvtrgoydrgnzj'tm yqfzmzo'bwzyqvr";
		//String cipherToAttack = "x'hi,qtsikgaphpsuowd'dozyuaysefyburrlwk'ekeekcybx'hi,qtsikgaphpsuowd'dozyuaysewdr'mfthyybzir";//inglese
		//String cipherToAttack = "ushssoyvxiywkbb hsdmyhyee blhgg,,z, ,znznqywgggvhv'qkberjy";//inglese
		String cipherToAttack = "m bqhigabqkmawahofsbhx'frc'zavfqbntgxpo'r ckudiqrqrvexj,jtesllffo'i vttytwofhjgohtbays'i";
		
		BruteForceAttack brtfrz = new BruteForceAttack();
		ArrayList<KeyPlainText> pairs = brtfrz.run(cipherToAttack);
		
		
		for(KeyPlainText pair: pairs) {
			System.out.println("( '"+ pair.getPlainText()+"'  -  '"+pair.getKey()+"' )");
		}
		
		
		System.out.println("Possibili testi in chiaro trovati: "+ pairs.size());
	}

}