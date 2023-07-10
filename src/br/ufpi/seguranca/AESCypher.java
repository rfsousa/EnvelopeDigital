package br.ufpi.seguranca;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCypher implements SymmetricCypher {
	private SecretKey aesKey;
	private IvParameterSpec iv;
	
	public AESCypher(int n) throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
	    keyGenerator.init(n);
	    aesKey = keyGenerator.generateKey();
	    iv = generateIv();
	}
	
	private static IvParameterSpec generateIv() {
	    byte[] iv = new byte[16];
	    new SecureRandom().nextBytes(iv);
	    return new IvParameterSpec(iv);
	}
	
	public String encrypt(String text) throws Exception {
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    cipher.init(Cipher.ENCRYPT_MODE, aesKey, iv);
	    byte[] cipherText = cipher.doFinal(text.getBytes());
	    return Base64.getEncoder()
	        .encodeToString(cipherText);
	}
	
	public String decrypt(String text) throws Exception {
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    cipher.init(Cipher.DECRYPT_MODE, aesKey, iv);
	    byte[] plainText = cipher.doFinal(Base64.getDecoder()
	        .decode(text));
	    return new String(plainText);
	}

	@Override
	public void setKey(String key) {
		String parts[] = key.split("\n");
		iv = new IvParameterSpec(Utils.hexToBytes(parts[0]), 0, 16);
		aesKey = new SecretKeySpec(Utils.hexToBytes(parts[1]), "AES");
	}
	
	@Override
	public String getKey() {
		return Utils.bytesToHex(iv.getIV()) + '\n' + Utils.bytesToHex(aesKey.getEncoded());
	}
}
