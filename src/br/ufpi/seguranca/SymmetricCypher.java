package br.ufpi.seguranca;

public interface SymmetricCypher {
	public void setKey(String key);
	public String encrypt(String text) throws Exception;
	public String decrypt(String text) throws Exception;
	public String getKey();
}
