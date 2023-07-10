package br.ufpi.seguranca;

public class Main {
	
	private static void displayHelp() {

		System.out.println("Argumentos disponiveis:");
	    System.out.println("-h: Exibir esta mensagem de ajuda");
	    System.out.println("-encrypt <caminho>: caminho para o arquivo com a chave publica");
		System.out.println("-decrypt <caminho>: caminho para o arquivo com a chave privada");
	    System.out.println("-algorithm <algoritmo> : algoritmo usado para criptografar e descriptografar");
	    System.out.println("-output <caminho>: caminho para o arquivo de saida");
		System.out.println("-input <caminho>: caminho para arquivo a ser criptografado");
		System.out.println("-symmetricKey <caminho>: arquivo que contem a chave simetrica criptografada com RSA");
	    System.out.println("exemplo: candre.jar -decrypt private=C:\\candre\\Documents\\chavepub.pem -algorithm aes192 arquivo.txt");

	}
	
	public static void main(String[] args) throws Exception {
		// DigitalEnvelope env = new DigitalEnvelope();
		// env.setPrivateKey("C:\\Users\\rfsousa\\Projetos\\Java\\Seguranca\\resources\\private_key_rsa_4096_pkcs8-generated.pem");
		// env.setPublicKey("C:\\Users\\rfsousa\\Projetos\\Java\\Seguranca\\resources\\public_key_rsa_4096_pkcs8-exported.pem");
		// System.out.println(env.decrypt(env.encrypt("qualquer\nútêéfi")));
		// if(1 > 0) System.exit(0);

		if(args.length == 0 || args[0] == "-h") {
			displayHelp();
			System.exit(0);
		} else {
			String publicKey = null,
					privateKey = null,
					inputFile = null,
					outputFile = null,
					algorithm = null,
					symmetricKey = null;
			boolean encryptMode = false;

			for(String part: args) {
				if(part.startsWith("-encrypt")) {
					encryptMode = true;
					publicKey = part.split("=")[1];
					System.out.println(publicKey);
				} else if(part.startsWith("-decrypt")) {
					privateKey = part.split("=")[1];
				} else if(part.startsWith("-algorithm")) {
					algorithm = part.split("=")[1];
				} else if(part.startsWith("-output")) {
					// Problema maior
					outputFile = part.split("=")[1];
				} else if(part.startsWith("-input")) {
					inputFile = part.split("=")[1];
				} else if(part.startsWith("-symmetricKey")) {
					symmetricKey = part.split("=")[1];
				}
			}

			if(algorithm == null) {
				System.out.println("Especifique um algoritmo a ser utilizado.");
				System.exit(0);
			}

			SymmetricCypher cypher = null;

			if(algorithm.equalsIgnoreCase("AES192"))
				cypher = new AESCypher(192);
			else if(algorithm.equalsIgnoreCase("AES128"))
				cypher = new AESCypher(128);
			else if(algorithm.equalsIgnoreCase("AES256"))
				cypher = new AESCypher(256);
			else if(algorithm.equalsIgnoreCase("DES"))
				cypher = new DESCypher();
			else if(algorithm.equalsIgnoreCase("RC4"))
				cypher = new RC4Cypher();

			if(cypher == null) {
				System.out.println("Algoritmo não existe.");
				System.exit(0);
			}
			
			DigitalEnvelope envelope = new DigitalEnvelope(cypher);

			if(encryptMode) {
				if(publicKey == null){
					System.out.println("Chave publica não especificada.");
					System.exit(0);
				}
				
				envelope.setPublicKey(publicKey);

				if(inputFile == null){
					System.out.println("Arquivo de entrada não especificado.");
					System.exit(0);
				}

				String text = FileUtils.readLinesFromFile(inputFile);
				text = envelope.encrypt(text);

				if(outputFile == null){
					System.out.println("Arquivo de saida indefinido.");
					System.exit(0);
				}
				
				String output[] = text.split("\n");

				FileUtils.writeLinesToFile(outputFile + "-key", output[0]);
				FileUtils.writeLinesToFile(outputFile, output[1]);
			} else {
				if(privateKey == null){
					System.out.println("Chave privada não especificada.");
					System.exit(0);
				}
				
				envelope.setPrivateKey(privateKey);

				String symKey = FileUtils.readLinesFromFile(symmetricKey);
				String text = FileUtils.readLinesFromFile(inputFile);

				text = envelope.decrypt(symKey + text);

				FileUtils.writeLinesToFile(outputFile, text);
			}
			
		}
	}
}
