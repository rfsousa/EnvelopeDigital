package br.ufpi.seguranca;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class RC4Cypher implements SymmetricCypher {
	private SecretKey rc4Key;
	
	public RC4Cypher() throws Exception {
		rc4Key = KeyGenerator.getInstance("RC4").generateKey();
	}
	
	public void setKey(String key) {
		rc4Key = new SecretKeySpec(Utils.hexToBytes(key), "RC4");
	}
	
	public String getKey() {
		return Utils.bytesToHex(rc4Key.getEncoded());
	}

	public String encrypt(String text) throws Exception {
		Cipher cipher = Cipher.getInstance("RC4");
	    cipher.init(Cipher.ENCRYPT_MODE, rc4Key);
	    byte[] cipherText = cipher.doFinal(text.getBytes());
	    return Base64.getEncoder()
	        .encodeToString(cipherText);
	}

	public String decrypt(String text) throws Exception {
		Cipher cipher = Cipher.getInstance("RC4");
	    cipher.init(Cipher.DECRYPT_MODE, rc4Key);
	    byte[] plainText = cipher.doFinal(Base64.getDecoder()
	        .decode(text));
	    return new String(plainText);
	}

}
