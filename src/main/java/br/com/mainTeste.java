package br.com;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;

import br.com.domain.utils.PDFUtils;

public class mainTeste {
	
	public static void main(String[] args) {
		PDFUtils teste = new PDFUtils();
		teste.teste();
	    	String file = new File("client_secret.json").getAbsolutePath();
	    	System.out.println(file);
	}
}
