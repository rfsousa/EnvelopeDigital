package br.ufpi.seguranca;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class FileUtils {

    public static String readLinesFromFile(String file) throws IOException {
        return readLinesFromFile(new File(file));
    }
	
	public static String readLinesFromFile(File file) throws IOException {
		String ret = "";

		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
		BufferedReader br = new BufferedReader(isr);
		
		// br.lines().forEach(ret::);
        for(String line: br.lines().collect(Collectors.toList())) {
            ret += line + "\n";
        }

		br.close();
		isr.close();
		fis.close();
		
		return ret;
	}

    public static void writeLinesToFile(String file, String str) throws IOException {
        writeLinesToFile(new File(file), str);
    }
	
	public static void writeLinesToFile(File file, String str) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
		BufferedWriter bw = new BufferedWriter(osw);
		
		bw.write(str);
		
		bw.close();
		osw.close();
		fos.close();
	}
}