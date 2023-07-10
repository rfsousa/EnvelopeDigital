package br.ufpi.seguranca;

public class Utils {
	public static byte[] hexToBytes(String hex) {
	    int len = hex.length();
	    byte[] bytes = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
	                + Character.digit(hex.charAt(i + 1), 16));
	    }
	    return bytes;
	}
	
	public static String bytesToHex(byte[] bytes) {
	    StringBuilder sb = new StringBuilder();
	    for (byte b : bytes) {
	        sb.append(String.format("%02x", b));
	    }
	    return sb.toString();
	}
}
