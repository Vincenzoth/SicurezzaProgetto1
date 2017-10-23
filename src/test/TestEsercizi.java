package test;

import java.util.ArrayList;

import esercizio1.Hill;
import esercizio1.KeyPlainText;
import esercizio1.MyException;
import esercizio2.BruteForceAttack;
import esercizio3.KnowPlainText;
import esercizio4.CryptAnalysis;

public class TestEsercizi {
	public static void main(String[] args) {

		boolean test1 = true;
		boolean test2 = true;
		boolean test3 = true;
		boolean test4 = true;

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
			
			
			key = "alto";
			System.out.println("\n   - Imposta la chiave '" + key + "':");
			try {
				cipher.setKey(key);
				System.out.println("             Chiave impostata!");
			} catch (MyException e) {
				System.out.println("             ERRORE !!  "+e.getMessage());
			}
			
			System.out.println("\n   - chiave: "+ cipher.getKey());
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

			String cipherToAttack = "iuaavhsgemtoftmrxpsgj ipiu";
			//String cipherToAttack = "kgeprrm ,gilzhpn,fhcaposvv,rqrp'pwwdj vb,gkgklweshwmqrosvvzolwilrfxpgoezfnkldiqs";
			//String cipherToAttack = "gbgbemumlcdvbb,izn qxpmwatoehldvmg qqumnivlw jmwpoeiyxyhnemwu w,u mnjidqo,fddqdvcvswumlcdvcvswumoe";
			//String cipherToAttack = "hv ymkne,dxupzmqojqjtmqjrvlqtw,dtvrvphkcqjpzgzzole ham'bsbcujqbjxppzgzbef'xykrvrml'sampzgzjrrvokmbzobyb,qbpzgzjr'be,d bcgwleeknvwffqbjrqhvtrgoydrgnzj'tm yqfzmzo'bwzyqvr";
			//String cipherToAttack = "x'hi,qtsikgaphpsuowd'dozyuaysefyburrlwk'ekeekcybx'hi,qtsikgaphpsuowd'dozyuaysewdr'mfthyybzir";//inglese
			//String cipherToAttack = "ushssoyvxiywkbb hsdmyhyee blhgg,,z, ,znznqywgggvhv'qkberjy";//inglese
			//String cipherToAttack = "m bqhigabqkmawahofsbhx'frc'zavfqbntgxpo'r ckudiqrqrvexj,jtesllffo'i vttytwofhjgohtbays'i";

			System.out.println("   - Testo cifrato:");
			System.out.println("            " + cipherToAttack);


			BruteForceAttack brtfrz = new BruteForceAttack();

			ArrayList<KeyPlainText> pairsKeyPlain = brtfrz.attack(cipherToAttack);
			KeyPlainText possiblePair = pairsKeyPlain.remove(0);

			System.out.println("\n     Testo in chiaro:");
			System.out.println("            " + possiblePair.getPlainText());
			System.out.println("\n     Chiave: '" + possiblePair.getKey() +"'" );

			System.out.println("\n\nTutte le possibili soluzioni:");
			System.out.println("soluzioni possibili: "+pairsKeyPlain.size());
			for(KeyPlainText kpt: pairsKeyPlain) {
				System.out.println("     CHIAVE: " + kpt.getKey() + "   TESTO IN CHIARO: " + kpt.getPlainText());
			}
		}

		if (test3) {
			System.out.println("\n\n---  Test esercizio 3  ----------------------------- ");
			System.out.println("---------------------------------------------------- ");

			//plainText = "attacco all'alba";
			//cipherText = "axiarpjuzhvizhz'";

			//plainText = "sicurezza informatica";
			//cipherText = "xrjddqf,jgimyexebfyojg";

			//plainText = "farc, fuerzas armadas revolucionarias de colombia";
			//cipherText = "dylqxjpwchylduv'gs'fduskfsbvjpn'v'zjdumwytgeybvuqx";

			//plainText = "postuv korespondencni problem";
			//cipherText = "i, hcjtxxk yi,phlvj,iuywpm bmk";

			//plainText = "dali' salvador, pittore";
			//cipherText = "pmeovcezwuqje'ofdcpee'fn";

			plainText = "i topi non avevano nipoti";
			cipherText = "iuaavhsgemtoftmrxpsgj ipiu";

			KnowPlainText knowAttack = new KnowPlainText(plainText, cipherText);
			System.out.println("Plain text:   " + plainText);
			System.out.println("Cipher text:  " + cipherText+"\n");

			try {
				key = knowAttack.attack();
				System.out.println("    - Chiave trovata: " + key);

				System.out.println("\n\n----- Verifica chiave -----");
				cipher.setKey(key);
				System.out.println("    -  "+ key +", chiave impostata!");
			} catch (MyException e) {
				System.out.println("             ERRORE !!  "+e.getMessage());
			}

			System.out.println("\n    - Criptare la frase:   \""+plainText+"\"");

			cipherText = cipher.Enc(plainText);
			System.out.println("    - testo cifrato:       \""+cipherText+"\"");

		}

		if(test4) {
			System.out.println("\n\n---  Test esercizio 4  ----------------------------- ");
			System.out.println("---------------------------------------------------- ");
			System.out.println("Attacco con analisi delle frequenze\n");
			
			ArrayList<KeyPlainText> possiblePairs;
			
			int bigramsToCompare = 5;

			System.out.println("Attacco considerando i primi " + bigramsToCompare +" digrammi più frequenti\n");
			
			
			String filenameBigram = "/text/Jones2004_Bigram.txt";
			CryptAnalysis analysis = new CryptAnalysis(filenameBigram);

			possiblePairs = analysis.decipher("/text/ciphertext.txt", bigramsToCompare);
			
			try {
				KeyPlainText bestPossiblePair = possiblePairs.remove(0);
				System.out.println("\n     Testo in chiaro:");
				System.out.println("            " + bestPossiblePair.getPlainText());
				System.out.println("\n     Chiave: '" + bestPossiblePair.getKey() +"'" );
				
				System.out.println("\n\nTutte le possibili soluzioni:");
				System.out.println("soluzioni possibili: "+possiblePairs.size());
				for(KeyPlainText kpt: possiblePairs) {
					System.out.println("     CHIAVE: " + kpt.getKey() + "   TESTO IN CHIARO: " + kpt.getPlainText());
				}
				
			}catch(IndexOutOfBoundsException e) {
				System.out.println("Non è possibile decriptare il testo con "+bigramsToCompare+" diagrammi");
			}			
			
			
			
		}
	}

}