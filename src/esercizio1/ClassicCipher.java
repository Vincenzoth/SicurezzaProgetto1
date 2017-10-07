package esercizio1;

public interface ClassicCipher {
	
	// riceve in input una stringa di quattro caratteri e imposta la chiave della codifica
	void setKey(String key) throws MyException;
	
	// restituisce una stringa di quattro caratteri rappresentante la chiave della codifica
	String getKey();
	
	// genera una chiave valida
	String genKey();
	
	// codifica la stringa passata e restituisce la stringa codificata
	String Enc(String plainText);
	
	// decodifica la stringa passata e restituisce la stringa decodificata
	String Dec(String cipherText);

}
