package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import esercizio1.Hill;
import esercizio1.MyException;
import esercizio2.BruteForceAttack;
import esercizio2.KeyPlainText;
import esercizio3.KnowPlainText;
import esercizio4.CriptAnalysis;

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
			//cipher.setKey("alto");
			cipher.setKey("qxrz");
			//cipher.setKey(key);
			System.out.println("             Chiave impostata!");
		} catch (MyException e) {
			System.out.println("             ERRORE !!  "+e.getMessage());
		}
		
		//String plainText = "i topi non avevano nipoti";
		String plainText = "farc, fuerzas armadas revolucionarias de colombia";
		
		System.out.println("\n   - Criptare la frase \""+plainText+"\":");
	
		String cipherText = cipher.Enc(plainText);
		System.out.println("            testo cifrato: \""+cipherText+"\"");
		
		System.out.println("\n   - decriptare la frase \""+cipherText+"\":");
		//String plainText = "i topi non avevano nipoti";
		String plainText2 = cipher.Dec(cipherText);
		System.out.println("            testo decodificato: \""+plainText2+"\"");
		
		
		System.out.println("\n\n---  Test esercizio 2  ----------------------------- ");
		System.out.println("---------------------------------------------------- ");
		System.out.println("Attacco forza bruta\n");
		
		//String cipherToAttack = "iuaavhsgemtoftmrxpsgj ipiu";
		//String cipherToAttack = "kgeprrm ,gilzhpn,fhcaposvv,rqrp'pwwdj vb,gkgklweshwmqrosvvzolwilrfxpgoezfnkldiqs";
		//String cipherToAttack = "gbgbemumlcdvbb,izn qxpmwatoehldvmg qqumnivlw jmwpoeiyxyhnemwu w,u mnjidqo,fddqdvcvswumlcdvcvswumoe";
		//String cipherToAttack = "hv ymkne,dxupzmqojqjtmqjrvlqtw,dtvrvphkcqjpzgzzole ham'bsbcujqbjxppzgzbef'xykrvrml'sampzgzjrrvokmbzobyb,qbpzgzjr'be,d bcgwleeknvwffqbjrqhvtrgoydrgnzj'tm yqfzmzo'bwzyqvr";
		//String cipherToAttack = "x'hi,qtsikgaphpsuowd'dozyuaysefyburrlwk'ekeekcybx'hi,qtsikgaphpsuowd'dozyuaysewdr'mfthyybzir";//inglese
		//String cipherToAttack = "ushssoyvxiywkbb hsdmyhyee blhgg,,z, ,znznqywgggvhv'qkberjy";//inglese
		//String cipherToAttack = "m bqhigabqkmawahofsbhx'frc'zavfqbntgxpo'r ckudiqrqrvexj,jtesllffo'i vttytwofhjgohtbays'i";
		String cipherToAttack = "v'orv,f,ndtlod'nfpycwmrdczkyb wstvowfjknxoigtvjjazws ,,pdexcb vuufeiv,rrldzxwyfjkndq,mfpycn,eifpycwmbwhwobg vu,p,aorv,ruv,,dfjlracxusabehweiv,yz'udqb xowwr',pf,ndowrkfpxfaz,hsaau,dfjn,heorzdowndqypjobudvbzpu pusaufigzx'yw zi,dauwwj' ,ylbkvuiopqyz'ukkj'elfmvsomhbg npjoeidfaunl uzkqmvu,plncbb  qtvob't";
			
		System.out.println("   - Testo cifrato:");
		System.out.println("            " + cipherToAttack);
		
		/*
		BruteForceAttack brtfrz = new BruteForceAttack();
		KeyPlainText pairKeyPlain = brtfrz.run(cipherToAttack);
		
		
		System.out.println("\n     Testo in chiaro:");
		System.out.println("            " + pairKeyPlain.getPlainText());
		System.out.println("\n     Chiave: '" + pairKeyPlain.getKey() +"'" );
		*/
		
		System.out.println("\n\n---  Test esercizio 3  ----------------------------- ");
		System.out.println("---------------------------------------------------- ");
		System.out.println("Attacco Known plaintext\n");
		
		String k = "jgqm";
		
		try {
			cipher.setKey(k);
			System.out.println("             "+ k +" Chiave impostata!");
		} catch (MyException e) {
			System.out.println("             ERRORE !!  "+e.getMessage());
		}
		
		plainText = "sicurezza informatica";
		System.out.println("\n   - Criptare la frase \""+plainText+"\":");
	
		cipherText = cipher.Enc(plainText);
		System.out.println("            testo cifrato: \""+cipherText+"\"");
		
		//System.out.println("Possibili testi in chiaro trovati: "+ pairs.size());
		
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
		cipherText="v'or";
        plainText="alic"; 
		//plainText = "i topi non avevano nipoti";
		//cipherText = "iuaavhsgemtoftmrxpsgj ipiu";
		

		KnowPlainText knowAttack = new KnowPlainText(plainText, cipherText);
		System.out.println("Plain text: "+plainText);
		System.out.println("Cipher text: "+cipherText);
		System.out.println("Chiave trovata: "+knowAttack.attack());		
		
		System.out.println("\n\n---  Test esercizio 4  ----------------------------- ");
		System.out.println("---------------------------------------------------- ");
		
		//String filenameSingle = "/text/Jones2004_Single.txt";
		String filenameBigram = "/text/Jones2004_Bigram.txt";
		CriptAnalysis analysis = new CriptAnalysis(filenameBigram);						
				
		//analysis.substitutionSingle(cypherText.get(0), filenameSingle);
		analysis.decipher("/text/ciphertext.txt");
			
	}

	
}