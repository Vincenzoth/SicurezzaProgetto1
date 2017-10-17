package test;

import java.util.ArrayList;

import esercizio1.Hill;
import esercizio1.KeyPlainText;
import esercizio1.MyException;
import esercizio2.BruteForceAttack;
import esercizio3.KnowPlainText;
import esercizio4.CryptAnalysis;

public class TestEsercizio1 {
	public static void main(String[] args) {

		boolean test1 = true;
		boolean test2 = true;
		boolean test3 = false;
		boolean test4 = false;

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
				cipher.setKey("ggpa");
				//cipher.setKey(key);
				System.out.println("             Chiave impostata!");
			} catch (MyException e) {
				System.out.println("             ERRORE !!  "+e.getMessage());
			}
			
			System.out.println("\n   - chiave: "+ cipher.genKey());
			plainText = "i topi non avevano nipoti";
			/*
			plainText = "far out in the uncharted backwaters of the unfashionable end of the western spiral arm of the galaxy lies a small unregarded " + 
					"yellow sun orbiting this at a distance of roughly ninetytwo million miles is an utterly insignificant little blue green planet whose ape " + 
					"descended life forms are so amazingly primitive that they still think digital watches are a pretty neat idea " +  
					"this planet has or rather had a problem, which was this most of  the people on it were unhappy for pretty much of the time " + 
					"many solutions were suggested for this problem, but most of these were largely concerned with the movements of small green pieces " + 
					"of paper, which is odd because on the whole it wasn't the small green pieces of paper that were unhappy " + 
					"and so the problem remained, lots of the people were mean, and most of them were miserable, even the ones with digital watches " +  
					"many were increasingly of the opinion that they'd all made a big mistake in coming down from the trees in the first place and " + 
					"some said that even the trees had been a bad move, and that no one should ever have left the oceans " + 
					"and then, one thursday, nearly two thousand years after one man had been nailed to a tree for saying how great it would be to be " + 
					"nice to people for a change, one girl sitting on her own in a small cafe in rickmansworth suddenly realized what it was that " + 
					"had been going wrong all this time, and she finally knew how the world could be made a good and happy place this time it was " + 
					"right, it would work, and no one would have to get nailed to anything " +  
					"sadly, however, before she could get to a phone to tell anyone about it, a terribly stupid catastrophe occurred, and the idea " + 
					"was lost forever this is not her story but it is the story of that terrible stupid catastrophe and some of its consequences " + 
					"it is also the story of a book, a book called the hitch hiker's guide to the galaxy, not an earth book, never published on " + 
					"earth, and until the terrible catastrophe occurred, never seen heard of by any earthman " +  
					"nevertheless, a wholly remarkable book in fact it was probably the most remarkable book ever to come out " + 
					"of the great publishing houses of ursa minor, of which no earthman had ever heard either " +  
					"not only is it a wholly remarkable book, it is also a highly successful one, more popular than the celestial home care " + 
					"omnibus, better selling than fifty more things to do in zero gravity, and more controversial than oolon colluphid's trilogy of " + 
					"philosophical blockbusters where god went wrong, some more of god's greatest mistakes and who is this god person anyway " +  
					"in many of the more relaxed civilizations on the outer eastern rim of the galaxy, the hitch hiker's guide has already supplanted " + 
					"the great encyclopedia galactica as the standard repository of all knowledge and wisdom, for though it has many omissions and " + 
					"contains much that is apocryphal, or at least wildly inaccurate, it scores over the older, more pedestrian work in two important " + 
					"respects first, it is slightly cheaper, and secondly it has the words ''don't panic'' inscribed in large friendly letters on its cover " + 
					"but the story of this terrible, stupid thursday, the story of its extraordinary consequences, and the story of how these " + 
					"consequences are inextricably intertwined with this remarkable book begins very simply it begins with a house";
*/
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
			//String cipherToAttack = "kgeprrm ,gilzhpn,fhcaposvv,rqrp'pwwdj vb,gkgklweshwmqrosvvzolwilrfxpgoezfnkldiqs";
			//String cipherToAttack = "gbgbemumlcdvbb,izn qxpmwatoehldvmg qqumnivlw jmwpoeiyxyhnemwu w,u mnjidqo,fddqdvcvswumlcdvcvswumoe";
			String cipherToAttack = "hv ymkne,dxupzmqojqjtmqjrvlqtw,dtvrvphkcqjpzgzzole ham'bsbcujqbjxppzgzbef'xykrvrml'sampzgzjrrvokmbzobyb,qbpzgzjr'be,d bcgwleeknvwffqbjrqhvtrgoydrgnzj'tm yqfzmzo'bwzyqvr";
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

			plainText = "thhe";
			cipherText = "ei ,";
			cipherText = "v,j'";


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
			
	
			int bigramsToCompare = 4;
			System.out.println("Attacco considerando i primi " + bigramsToCompare +" digrammi più frequenti\n");
			
			
			String filenameBigram = "/text/Jones2004_Bigram.txt";
			CryptAnalysis analysis = new CryptAnalysis(filenameBigram);

			possiblePairs = analysis.decipher("/text/ciphertext3.txt", bigramsToCompare);
			
			try {
				KeyPlainText bestPossiblePair = possiblePairs.remove(0);
				System.out.println("\n     Testo in chiaro:");
				System.out.println("            " + bestPossiblePair.getPlainText());
				System.out.println("\n     Chiave: '" + bestPossiblePair.getKey() +"'" );
			}catch(IndexOutOfBoundsException e) {
				System.out.println("Non è possibile decriptare il testo con "+bigramsToCompare+" diagrammi");
			}			
			
			System.out.println("\n\nTutte le possibili soluzioni:");
			System.out.println("soluzioni possibili: "+possiblePairs.size());
			for(KeyPlainText kpt: possiblePairs) {
				System.out.println("     CHIAVE: " + kpt.getKey() + "   TESTO IN CHIARO: " + kpt.getPlainText());
			}
			
		}
	}

}