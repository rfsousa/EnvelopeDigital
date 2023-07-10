package br.ufpi.seguranca;

public class DigitalEnvelope {
	private RSACypher rsa;
	private SymmetricCypher symmetric;
	
	public DigitalEnvelope() throws Exception {
		this(new AESCypher(192));
	}

	public DigitalEnvelope(SymmetricCypher symmetric) throws Exception {
		rsa = new RSACypher();
		this.symmetric = symmetric;
	}

	public String encrypt(String s) throws Exception {
		String encryptedMessage = symmetric.encrypt(s);
		String key = rsa.encryptToBase64(symmetric.getKey());
		return key + '\n' + encryptedMessage;
	}
	
	public String decrypt(String s) throws Exception {
		String parts[] = s.split("\n");
		String key = rsa.decryptFromBase64(parts[0]);
		symmetric.setKey(key);
		return symmetric.decrypt(parts[1]);
	}

	public void setPublicKey(String key) throws Exception {
		rsa.setPublicKey(key);
	}

	public void setPrivateKey(String key) throws Exception {
		rsa.setPrivateKey(key);
	}

}
