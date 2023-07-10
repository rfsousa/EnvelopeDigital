# Envelope Digital

Um envelope digital é um método de criptografia que permite que uma mensagem seja enviada de forma segura de um remetente para um destinatário, sem que a mensagem seja interceptada por terceiros. O envelope digital é composto por duas etapas: a primeira etapa consiste em criptografar a mensagem com uma chave simétrica, e a segunda etapa consiste em criptografar a chave simétrica com uma chave pública. A chave simétrica é gerada aleatoriamente e é usada apenas para criptografar a mensagem. A chave simétrica é criptografada com a chave pública do destinatário e enviada junto com a mensagem criptografada. O destinatário utiliza sua chave privada para decifrar a chave simétrica e, em seguida, utiliza a chave simétrica para decifrar a mensagem.

## Como funciona

O programa suporta diferentes algoritmos de criptografia, como AES, DES e RC4, e utiliza chaves assimétricas RSA para criptografar e descriptografar uma chave simétrica aleatória que é usada para criptografar os dados do arquivo.

O projeto foi desenvolvido em Java e está dividido em várias classes.

- Existe a classe Main, responsável pela execução do programa. 

- A classe Utils que serve para converter um objeto do tipo String em um array de bytes e um array de bytes em um objeto do tipo String, de forma a facilitar as operações de cifragem e decifragem.
- A classe FileUtils serve para a leitura e escrita dos arquivos.
- Existe uma interface SymmetricCypher que serve para especificar os métodos que devem ser implementados pelas classes que implementam a cifra simétrica (AES/DES/RC4).
- As classes AESCypher, DESCypher, RC4Cypher implementam a interface SymmetricCypher e são responsáveis por cifrar e decifrar um texto utilizando os algoritmos AES, DES e RC4, respectivamente.
- A classe RSACypher permite entrar com um par de chaves no formato OpenSSL. A chave pública será utilizada para cifragem e a privada para decifragem. A chave é lida a partir de arquivos contendo as chaves.
- A classe DigitalEnvelope define o envelope digital, possuindo métodos para cifrar e decifrar um texto. Essa classe recebe como parâmetro um objeto do tipo SymmetricCypher, para escolher qual algoritmo de chave simétrica deve ser utilizado. Ela instancia um objeto da classe RSACypher.

## Como executar

### Construção

Para construir e executar, é recomendado utilizar a distribuição do Java "Eclipse Temurin", em sua versão 17.0.7 que pode ser encontrada [aqui](https://adoptium.net/temurin/releases/).

Não é necessário instalar a distribuição como um pacote, é possível apenas descompactar o arquivo em um diretório de sua preferência e lembrar do local dos arquivos binários da distribuição do Java para chamá-los no terminal.

A etapa de construção pode ser pulada, já que um executável já foi gerado e está disponível na pasta do projeto.

Clone o repositório ou baixe o arquivo zip e descompacte-o.

```git clone https://github.com/rfsousa/EnvelopeDigital.git```

Na pasta do projeto execute os seguintes comandos:

O seguinte comando compila os arquivos Java e coloca o resultado na pasta bin:

`javac -d bin src/br/ufpi/seguranca/*.java`

O seguinte comando gera o arquivo executável Seguranca.jar:

`jar cfm Seguranca.jar manifest.txt -C bin .`

**AVISO**: Os programas jar e javac devem ser os da distribuição do Java mencionada neste documento. Pode substituir essas chamadas pelo caminho completo do executável, por exemplo `C:\Users\ryan\.jdks\jdk17\bin\javac -d bin src/br/ufpi/seguranca/*.java`.

### Execução

- O método displayHelp() exibe uma mensagem de ajuda com os argumentos disponíveis para executar o programa. A mensagem de ajuda inclui as seguintes opções de argumentos:
```
-h: Exibe a mensagem de ajuda.
-encrypt <caminho>: Especifica o caminho para o arquivo contendo a chave pública.
-decrypt <caminho>: Especifica o caminho para o arquivo contendo a chave privada.
-algorithm <algoritmo>: Especifica o algoritmo usado para criptografar e descriptografar.
-output <caminho>: Especifica o caminho para o arquivo de saída.
-input <caminho>: Especifica o caminho para o arquivo a ser criptografado.
-symmetricKey <caminho>: Especifica o arquivo que contém a chave simétrica criptografada com RSA.
```

Para crifrar a mensagem é necessário utilizar os seguintes argumentos:
- encrypt : Especifica o caminho para o arquivo contendo a chave pública.
- algorithm : Especifica o algoritmo usado para criptografar e descriptografar.
- input : Especifica o caminho para o arquivo a ser criptografado.
- output : Especifica o caminho para o arquivo de saída.

```
-encrypt= PATH/Public_Key.pem 
-algorithm= rc4 
-input= PATH/Input_File_Name.txt 
-output= PATH/Output_File_Name.txt
```

Já para decrifrar a mensagem utiliza-se os seguintes argumentos na execução:

- -decrypt= Especifica o caminho para o arquivo contendo a chave privada.
- algorithm= Especifica o algoritmo usado para criptografar e descriptografar.
- input : Especifica o caminho para o arquivo a ser criptografado.
- symmetricKey= Especifica o arquivo que contém a chave simétrica criptografada com RSA.
- output= Especifica o caminho para o arquivo de saída.

```
-decrypt= PATH/private_key_rsa_4096_pkcs8-generated.pem 
-algorithm=rc4 
-input=PATH/Input_File_Name.txt 
-symmetricKey=PATH/Input_File_Name.txt-key 
-output=PATH/Output_File_Name.txt
```

**OBSERVAÇÃO**: O caminho deve ser COMPLETO e não relativo.

#### Exemplos

##### Cifragem

###### Windows
```
java -jar Seguranca.jar -encrypt=C:\Users\rfsousa\Documents\chavepublica.pem -algorithm=aes192 -input=C:\Users\rfsousa\Documents\arquivo.txt -output=C:\Users\rfsousa\Documents\criptografado.txt 
```

###### Linux

```
java -jar Seguranca.jar -encrypt=/home/pedro/Documentos/EnvelopeDigital/resources/public_key_rsa_4096_pkcs8-exported.pem -algorithm=aes192 -input=/home/pedro/Documentos/EnvelopeDigital/resources/lorem.txt -output=/home/pedro/Documentos/EnvelopeDigital/criptografado.txt 
```

##### Decifragem

###### Windows
```
java -jar Seguranca.jar -decrypt=C:\Users\rfsousa\Documents\chaveprivada.pem -algorithm=aes192 -input=C:\Users\rfsousa\Documents\criptografado.txt -symmetricKey=C:\Users\rfsousa\Documents\criptografado.txt-key -output=C:\Users\rfsousa\Documents\decifrado.txt
```

###### Linux

```
java -jar Seguranca.jar -decrypt=/home/pedro/Documentos/EnvelopeDigital/resources/private_key_rsa_4096_pkcs8-generated.pem -algorithm=aes192 -input=/home/pedro/Documentos/EnvelopeDigital/criptografado.txt -symmetricKey= /home/pedro/Documentos/EnvelopeDigital/criptografado.txt-key -output=/home/pedro/Documentos/EnvelopeDigital/decifrado.txt 
```

