package test;

import esercizio1.Hill;
import esercizio1.MyException;
import esercizio2.BruteForceAttack;
import esercizio2.KeyPlainText;
import esercizio3.KnowPlainText;

public class TestEsercizio1 {
	public static void main(String [] args) {
		boolean test1 = false;
		boolean test2 = false;
		boolean test3 = true;
		
		Hill cipher = new Hill();
		String key;
		String plainText;
		String cipherText;

		if(test1) {
			System.out.println("---  Test esercizio 1  ----------------------------- ");
			System.out.println("---------------------------------------------------- ");
			
			System.out.println("\n   - Generare una chiave:");
			key = cipher.genKey();

			System.out.println("            \""+key+"\"");

			System.out.println("\n   - Imposta la chiave:");
			try {
				cipher.setKey("alto");
				//cipher.setKey(key);
				System.out.println("             Chiave impostata!");
			} catch (MyException e) {
				System.out.println("             ERRORE !!  "+e.getMessage());
			}

			plainText = "i topi non avevano nipoti";

			System.out.println("\n   - Criptare la frase \""+plainText+"\":");

			cipherText = cipher.Enc(plainText);
			System.out.println("            testo cifrato: \""+cipherText+"\"");

			System.out.println("\n   - decriptare la frase \""+cipherText+"\":");

			String plainText2 = cipher.Dec(cipherText);
			System.out.println("            testo decodificato: \""+plainText2+"\"");
		}

		if(test2) {
			System.out.println("\n\n---  Test esercizio 2  ----------------------------- ");
			System.out.println("---------------------------------------------------- ");
			System.out.println("Attacco forza bruta\n");

			//String cipherToAttack = "iuaavhsgemtoftmrxpsgj ipiu";
			String cipherToAttack = "kgeprrm ,gilzhpn,fhcaposvv,rqrp'pwwdj vb,gkgklweshwmqrosvvzolwilrfxpgoezfnkldiqs";
			//String cipherToAttack = "gbgbemumlcdvbb,izn qxpmwatoehldvmg qqumnivlw jmwpoeiyxyhnemwu w,u mnjidqo,fddqdvcvswumlcdvcvswumoe";
			//String cipherToAttack = "hv ymkne,dxupzmqojqjtmqjrvlqtw,dtvrvphkcqjpzgzzole ham'bsbcujqbjxppzgzbef'xykrvrml'sampzgzjrrvokmbzobyb,qbpzgzjr'be,d bcgwleeknvwffqbjrqhvtrgoydrgnzj'tm yqfzmzo'bwzyqvr";
			//String cipherToAttack = "x'hi,qtsikgaphpsuowd'dozyuaysefyburrlwk'ekeekcybx'hi,qtsikgaphpsuowd'dozyuaysewdr'mfthyybzir";//inglese
			//String cipherToAttack = "ushssoyvxiywkbb hsdmyhyee blhgg,,z, ,znznqywgggvhv'qkberjy";//inglese
			//String cipherToAttack = "m bqhigabqkmawahofsbhx'frc'zavfqbntgxpo'r ckudiqrqrvexj,jtesllffo'i vttytwofhjgohtbays'i";

			System.out.println("   - Testo cifrato:");
			System.out.println("            " + cipherToAttack);


			BruteForceAttack brtfrz = new BruteForceAttack();
			KeyPlainText pairKeyPlain = brtfrz.run(cipherToAttack);


			System.out.println("\n     Testo in chiaro:");
			System.out.println("            " + pairKeyPlain.getPlainText());
			System.out.println("\n     Chiave: '" + pairKeyPlain.getKey() +"'" );
		}

		if (test3) {
			System.out.println("\n\n---  Test esercizio 3  ----------------------------- ");
			System.out.println("---------------------------------------------------- ");

			//plainText = "attacco all'alba";
			//cipherText = "axiarpjuzhvizhz'";

			//plainText = "sicurezza informatica";
			//cipherText = "xrjddqf,jgimyexebfyojg";

			plainText = "farc, fuerzas armadas revolucionarias de colombia";
			cipherText = "dylqxjpwchylduv'gs'fduskfsbvjpn'v'zjdumwytgeybvuqx";

			//plainText = "postuv korespondencni problem";
			//cipherText = "i, hcjtxxk yi,phlvj,iuywpm bmk";

			//plainText = "dali' salvador, pittore";
			//cipherText = "pmeovcezwuqje'ofdcpee'fn";

			//plainText = "i topi non avevano nipoti";
			//cipherText = "iuaavhsgemtoftmrxpsgj ipiu";


			KnowPlainText knowAttack = new KnowPlainText(plainText, cipherText);
			System.out.println("Plain text:   " + plainText);
			System.out.println("Cipher text:  " + cipherText+"\n");
			
			key = knowAttack.attack();
			System.out.println("    - Chiave trovata: " + key);

			
			System.out.println("\n\n----- Verifica chiave -----");
			try {
				cipher.setKey(key);
				System.out.println("    -  "+ key +", chiave impostata!");
			} catch (MyException e) {
				System.out.println("             ERRORE !!  "+e.getMessage());
			}

			System.out.println("\n    - Criptare la frase:   \""+plainText+"\"");

			cipherText = cipher.Enc(plainText);
			System.out.println("    - testo cifrato:       \""+cipherText+"\"");

		}
	}

}