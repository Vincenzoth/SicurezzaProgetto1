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
import esercizio4.CryptAnalysis;

public class TestEsercizio1 {
	public static void main(String[] args) {

		boolean test1 = true;
		boolean test2 = false;
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

			System.out.println("\n   - Imposta la chiave:");
			try {
				cipher.setKey("pqom");
				//cipher.setKey(key);
				System.out.println("             Chiave impostata!");
			} catch (MyException e) {
				System.out.println("             ERRORE !!  "+e.getMessage());
			}

			plainText = "i topi non avevano nipoti";

			System.out.println("\n   - Criptare la frase \""+plainText+"\":");

			cipherText = cipher.Enc(plainText);
			cipherText = "v'orv,f,ndtlod'nfpycwmrdczkyb wstvowfjknxoigtvjjazws ,,pdexcb vuufeiv,rrldzxwyfjkndq,mfpycn,eifpycwmbwhwobg vu,p,aorv,ruv,,dfjlracxusabehweiv,yz'udqb xowwr',pf,ndowrkfpxfaz,hsaau,dfjn,heorzdowndqypjobudvbzpu pusaufigzx'yw zi,dauwwj' ,ylbkvuiopqyz'ukkj'elfmvsomhbg npjoeidfaunl uzkqmvu,plncbb  qtvob'tgyxo ,zikcpjobdewqqqjjsauf ,,pecufxsw omndhuxlomndruv,lnwtemctqyj' ,elauuwwstbwqdqb ctjeraudyvxofhacwswyfjxcynabzxxbcz ,,peiv,avhkjpowknuxgofpycpquwwwwswjfrufmfwtfjtlziqyeij' ,j'thrffhvuiomjktfpycynomw nljtfpyceiv,uwwwpomgzi ,ufjphd fpgomzibur'ibqnvfaujoeihefpbmtdqmibwyydciv,akdqb eib v,f,ndn,eifpycgykyb wsowtbrnqnfhsaufeizpghqybwabomhbg j'buldsaaugyudyvuxdsltdfauknj' ,zinwj'hw ,ysj' ,ibqnvfau qwswmsayg'ihrnplkbwhk,plkbwhk,p'hruv'ratlfkzpgnzi ,ufruv,eidf tauigvuud,pshr'xyysayzxigxdoegrxuj'hw ,,peizpxo ,vufmvsj'hw,dudziobwqowfjzpj'bumgaz,homaueiv,tvvyigomxlxojevyfjgkigv,gszdm ra'qauxb fj' ,ibqnvfauc,zdv'pgj'aobmpqf,qbltdfauknsaygzifrxclnzphexdst xomw fkaostfjzpsa xomw j' ,ufhcgrpofjobzxv'orv,xcysr'fjwmdqb ctje xctqysaauozkc ,fjc,thisdqb uxfpfjeizpxo ,dqrkimud,ptlbgowxojeufpqm dbigziigltl'eib omzifrxclnzphexdst xvu,ppqf,qbltwmj'gov,dfauknsa xomw azzkkgjjziigltoeqqciigvvxo ,ibwyom'xcindeiv,'p'ifjshr',pigzxwyfjbghoxqzp'iwsf,ndkhxcsauftvvyj'hwbkv,ighep'bwecufpqdnptv,m dbigdqnev,xqwq,peiv, ,xnv,fpomn,eib ux r fau'b,vzi fauv'orv,shr',pigzximud,pobg pjobdewqqqjjdqecfpj' ,ziqytxxo ,zikcj'hwmjaudfaueufrufeiv,m dbigdqnev,hubexojwfr tauobfksfv,pqzd'n'ictqyxo rv,f,vvomw j' ,ufykcpxuxoscwq,qws'b,vzxgyxoscwq,qwseizpomhbg dqrkghrvomux r fauwmj'buldqndfauxcp'nljjdqb bkuuazyrqyv,ruv,bgxqfj ,vb'iiox'xlfpyc'b,vomkyb wswqachuxll'eib j' ,zi'iraf,ndudyvbwjewnvu,pruv,zvxlkyb wstoecpgzxbg,pruv,,dfjav f',vuiotvvyomndruv,hubebwecufwmfkaobmqndfau ,,pwyfjwmziobwq,pxbzpzikcrxfpycwmdqxvlrufimqacthjxczxruv,jwpofjwmfkaobm'b,vomw uxgov,dfauxbzpruv,f,ndlnxsjjj'gzaz,hsaauf,ndwmhwuwrnj'hwbkv,wydrbujjj' ,ufruv,kv'uxuomaueiv,dewqndknj' ,zi'iqdomw ghrvorxuj',daueitdzib v,'pxlxuziigltoeyhwg jndwyfjyz'uxo , zqmdqb v,wyfjeib v,ruv, qtntbszomw heorzdowndhcjjylqcuflrtrxo ,j'aobm'b,vomenyscttherobv,knj' ,xo , zqmomndruv,jxisxusaauf,nddntlxlxunpqywymjuxystbdnwqloaz,hj'hw ,,peehkauyk qcp,xbevybesaauf,ndnvejwsruv,ykfjn,auhbstj'hwolp'j' ,enysctqycthk,pknt,eshbjjxo rwxjqvvxohwtbgsmjfjwmhe,hsaaufpwmvuimvuioeiv,oeyhwg jndkcruv,zvxlhekcauigju'icqj'elfmvsomhbg j'hw ,vb'ihrnpshr',pjpwjomctv'rakcj'bumgsaxo,dxleifpbmn,eifpycknj'idipfpyc'b,vxopehjndeltnlg,mv,eitdswrav'raeifpbmvyomauelvyzijzzx'hmfwtmrwkxonwomsleifpycqndfauigzxdw fsaio'hzvxlvulfj' ,j'p'knj' ,dqdfbkmlxborltf,ndudyvfksf'iwsjwep'b,vzx'b,vzx'b,vzidftxj' ,ctv'raimud,plnvyj'hwwyqgw np'hmfw b dqectbsluxesqmsaxhv,x'xl fazwseiwwj'tcgnxo ,xofrfjv'dffjcmuxlxautlrdcztvjjgyvyxbb v,imysj' ,pj fjwv,knj' ,qgyseifkczuxv,bkv,eizpzidftxazv,bgzkeidf qw uxesqmbwectisaj'buldmlbgzs,fdfxojezxv'orv,,dfjfhysbexodwb v'eifptrvuioeiwwxoqyaufpdqb fkqmgypusaufeiv,anelneth rzxwyfjeidf tj'bundf,ndn,aupqudyvrdaofjp'qchoxqigwsbg,pruecfpycknio ,,piuecfhxnuaomndeib v,f,ndn,vuimj'hwhbxc fj'hw ,zsxotvxlsaauf,ndrxjqhem  uorv,wmxonwsaaussb krqmzxeizph,omyz,hj' ,ibqlvsbwwwpehpv,az,hj' ,uf'hmfw b zi,daudntvzdwqqyfkobodzdwqsaxhv,rxauwmmlv'orv,,dfjn,sawqpqxbzpfkzpigscv,f,mgvu,pkvjjigscv,l'eib zx'qaueidf taueitdzib v,kgg rdm w ziqyayj'hw qwsdmqm fgcwsruv,tlkpufeufrufcmziobwq,pb'saxo,dxlctv'raqq taueithfmlteiv,hkholteltne,'nwsigswrabknvj'hwlnvyvu,hom,jjjj' ,heosavv,eizpziv'bmjoeieil',p ,rknd'b,vf, jj' ,ombegmzpbuqmzx'heifpbmnpxo ,zikcibzp ,,pbnrkeib v,f,ndn,vuimfkwwr'kgjjzxeiwwj'tcuaomndigbwabfvaugyxqfjzpomxlj' ,qq taumf jnp  'qau'hruv'ra,dudj'hwkcbmeinvzi,daueiv,gsvyvuioeiv,lnxqjwwswwzxpkqiiuechefhkcuauxadhlzxwwj'bundimtn,nv'wyfjqyomlxjwv'numlwyfjruv,jwpofjwmpjzkygtdomndruv,vi'uv,ctwyq'pjzkygtdfpyckc,fdfvnv,x'xlfpyceithfmlteiv,fr,p'b,fdfj'buld,fdfpjdftxuxwyeuv,ignpwyfjxbzpomufqln,m befkiggcv,odd,xo ,swraeifpbmvyctqyomebfpycn,zxigswraimud,p'bj'hwkcbmlrbdxvnd'hruv'rabkv,igziqqkt fylxkgyvyxbb gn'b,vzx'b,vzx'b,vj' ,owzikcghrvbujjqgmpv,wmbwgzxohwv'orv,gyobazkbwypei'fpyceufrufoffpteswraxsisuxv,udyvuxdsltwmghqlvszx'hrudftxj'buldmlykgsltf,ndeiv,efaucmdqp'v,eitdswraowvyfob dqb xol g ,pknuxesbmzpr'pqtvvybwfpteuxwswqyssaziwwltpkqihuowbwecuf ,owziigltvyj' ,owomowghhwxsg saufeiv,frzssaegomrqfremaz,h,fdfuxqlvspjzpwjomazzpzxwyfjeizph,kyb wshbstomuxdfbkzxpkqiiuecaz,hbwhwefygqgzpazzpmgsaziobwqlcomw dqb v,v'orv,tlkpufwmrdczibzp ,,ptojevqzxwyfjhubevuuf qmtjjj'hw ,vb'ihrsaufpqolhkcxgyhovuiof,vvnp'bpjzpndhkaurrygbwhwefygqgzpazzp'tomw xo rcztcqmzxofhwrrygqgzppjzp'tctqyzxpkqibkuaomndruv,lnwtmrwkompuhu,pl'eib wrepxcu tiigbwabfvauaawjuxzpr',pxborltf,wsruv,twauigxo ,ct'iaueizpxo ,zikcbwumfpycknhromw dqrkenlxautluoufwmbwowhlj',dauruv,f,ndf,i'fpyc,dw sauf,dw ziigltykgskwomw xonwfpycwmdqb kyb wshkexqmgcvvnpn,sqbwfptezxr'xluxv,eiv,jw,hltykfjpkqidwb qgzpomazzpmlxb fxoscwq,qvvj'hcmwj'hcmwbwecufruv,efvyylqcufpq ,xvvuioxcorz'omw bwyvfkhkudmgomw j' ,ctv'raf,ndssb v'orv,f,ndn,aupqvfauhchozxwyfjruv,khmwxuylxkobj'hw ,,pzvczsaufpq,jvyberuv,kv'uxuylwnaz,hsaauf,ndv'rauwrnvuudbdhkfjtlbgowdqb zikcomn,eib kvjjhekc qmjzxwyfjeiv,xbigv,m dbigzikcxotvxlsaufde t xdqzkyvfpyc'b,vsaaueib v,f,ndn,aupq,jvybej'hwtlfkciaumznwzi fauv'orv,hbstj' ,zifpemomw f,ndkhxcsauftvvyj'hw ,yssaau qvvomndigj'zkimfjpqlnexb zxfdltcxqgysndwyfjxbwwstvbzxeltndnr'saohndmjktfpjsxo ,zikcpjkvbkazzofpfjigzi ,ufruv,zdexxuj' ,pjqyimzsaz,hj' ,ibqnvfauf,ndn,fkobmj,pwmazv,bk fxo ,ctdfw  ,vb'iiofpomfkobxffkecdqv'qdzibuwjzikcfkigylxkakomibecvuiodnmwnd,djjfpycrq rj' ,ibaoioeib v,huowbwaovbomxlibdfw j' ,dqv'qdaz,hj' ,wshuowomxlfkxdstfjwyfjxb fv'orv,,dfjtl fomxlj' ,zinwbwecufobv,dewqomw ylxkeiv,rv ,zsj'yvfpycdwb ws'bqyzxruv,f,i'xuxorkpgbwecufeiv,xshdfhzxmfw b fpyceltnruv,f,nddwb j'hwmjaudfaueufrufjphd fpgxo ,pjhlv,ynobomfkiggcv,eiowv,fhngxuj'qnfhzxv'ratbwqvuiogyhbfjbnkcndeib v,f,ndn,eifpycobsaauetg ejomj'fpwsrxtx ft,tdzxwyfjv'orgnnd'pvbaueidf tauf,ndeizpsaauxs tautlkvjjj'hwobv,knj' ,bwaovbvuioeiv,,dxl'q xomdnndl'eib j' ,fkxdz'zib v,wmhwdnptuavu,peiv,stwsf,ndwmhwf'v'qd'qauzpomslibzpv,igzidftxghrvvulrufwywsknj' ,erelhuudzsvuufeiv,bklnw tvvyibdfw zxruv,efvyylqcufpqkvtnoehofrufruv,,dfjn,aun,tvg fjtlbgowzxwyfjtlbuw saauf,ndpqhbktfhbwao,pqndfau'psnjeuffpwjqmdqqlltruv,jwpofjeiv,hbktfhrdnewqufstwsfpj' ,fkxdapomw j'hw ,,peehkauwqhb tauigctigr'fjv'orv,p' fxuj' ,bwao,pwyfjbgxqfjeizpsaaufhfjfpwmomxotbxlhekc qmjzxn,auaawjfkysmj,peiwyomibzpdqnev,ruv,iu'iau'b,vomw fkaostfjv'obyceiv,jxiseuv,fpwmj' ,fkss'ipoxcrdyswqufpkqidwb xomzdqecxo ,fkobmjfjwmrdczvu,hvuioeizpbwysbm,dxlzxwyfjf,w b omyz,hom,jjjj'elbkazxundknazqq tauozecb ndwyfjeiciv,lnnectdfbefrpuzx'qauruv,lnwtfjn,audw frdczdqb dqhkfjeithfmlteiv,'bqyf,ws'yw qgudufb'uxws ,rkzidftxrdhweithfmkwmleidf tauqcqyomhbg zxcmaumfwtfjtlvuioudyvfkiggcv,lxv,joeidfaucxxoelwtwqvbvukwdqecsaziwwlt'hlnwtfjru,hylxkhbstomj''iqmlnlrsaj'buldsapjdftxzxb'savu,qwsiusgdqecj'hwtlodfvbgzs,fdfxojezxgyuxwywsdfauknj' ,zinwj'bujjnd,dfj,dcp fxufkzp'ivveizpomhbg dqrkazkbxqj'hweifpbmeizpkyb wszvtneifptrsaw jefjhuowibhkxlwstcqcis efheib v,bknvxuj'hwtlghhwlxv,fpzifrtvjjazwseiv,hbktfhbwaozsxohwruv,huberrjtj'hweiv,peipuadqv'ioelnljjxo ,uxqlvsctfpfjwyrv ,,pstwsobsa xvu,pzpwywsm r'omazaobmknibwtqmctqyxohcktfpyclrp'fhylxkhbstj''iqmlnlrndeiwwtvvyxo ,ctdfw omfkiggcv,yzktfhvuufigzxjubuwjpjb pefppgzikcghrvdqb v,tlbgowkkxofrfjv'oruaomw ibdfw j' ,ghihbmknj' ,azrvgcv,f,ndpqjxlr,pdntlqdziiglteiv,mf jndofqqlduxgnazhk,hb'wtpgheqqbexuvuufigsaufdnptv,fhktb ndigzikcomxlkyb wshuxlj'hw qwsofqqlduxuaml'qaueiv,jobkfkiggcv,v'orv,f,ndn,aurxfpycwmbwhweizpsaufpqhcgrwsttgzsaswrakv'ucthjxckkxo ,xofremnpwyfjbkv,xbcz ,,pigh,uxysstfjqcwwobvu,pn,ohctqyxo ,dqrkibhkfjbkudm rakgg hbktfhdqwwwmqqqmomyz,hpjbutxowufxbhw,dfjrxau'qex xomw qgzp fylxkakziesfjtlkcygomw vueib yl kfhkcwyaueifptrzxv'ratleflxv,eitdzidftxghrvibnvnvtl,peiv,demwfhibwtqmj' ,hjctqq faydqrkj'l  taueinvxodsltkczxeizpomibxudqrvqcst,pjoxlazzkufpkqib',fdfdqnefjigj'aofkobycwyfjeizpsaiopkqioeaupkzk'pjjb kyb wswqacpgziigltpqiub'uasaaulxcvxlwsipjeayomw xo ,dqrkghdwb bgptrvr'ufeizpzxb',fdfbwqqlduxdsltrq romazrvgcv,tbrnxunpqcwwobkksaauwwv',jxcpjb pefpj'hwyk qeejeziigltpkplxoaoim,pqyfkzpb elhuudzsj'bundyzktfhzikcghrvuxysstfjuq,xgytimlgyomhbg ky fzdowfjwmj'kcr'igzxwyfj'pw fpycigkyb wskgg zxigdqrkzxfpctc, xomxoqyauknuxkaxuctdnzrzkknpj ,grwspehozxoexcysemhefpv,xvavuaibwgxcj'zkstvvj'knzvuaomw dqrv'qktb xuj'wgxczxruv,udyvxoaouf'pkgruxusaauknio";
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
			cipherToAttack = "v'orv,f,ndtlod'nfpycwmrdczkyb wstvowfjknxoigtvjjazws ,,pdexcb vuufeiv,rrldzxwyfjkndq,mfpycn,eifpycwmbwhwobg vu,p,aorv,ruv,,";
			//String cipherToAttack = "kgeprrm ,gilzhpn,fhcaposvv,rqrp'pwwdj vb,gkgklweshwmqrosvvzolwilrfxpgoezfnkldiqs";
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

		if(test4) {
			System.out.println("\n\n---  Test esercizio 4  ----------------------------- ");
			System.out.println("---------------------------------------------------- ");
	
			//String filenameSingle = "/text/Jones2004_Single.txt";
			String filenameBigram = "/text/Jones2004_Bigram.txt";
			CryptAnalysis analysis = new CryptAnalysis(filenameBigram);

			//analysis.substitutionSingle(cypherText.get(0), filenameSingle);
			analysis.decipher("/text/ciphertext.txt");

		}
	}

}