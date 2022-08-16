package br.com.domain.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PDFUtils {

	public ByteArrayOutputStream juntarPdf(List<InputStream> escolhidos) {
		PDFMergerUtility pdfMerger = new PDFMergerUtility();
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		try {
			pdfMerger.setDestinationStream(os);
			pdfMerger.addSources(escolhidos);
			pdfMerger.mergeDocuments(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return os;
	}
	
	
	public ByteArrayOutputStream juntarPdfFile(List<File> escolhidos) {
		PDFMergerUtility pdfMerger = new PDFMergerUtility();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			pdfMerger.setDestinationStream(os);
			for(File arquivo: escolhidos) {
				pdfMerger.addSource(arquivo);
			}
			pdfMerger.mergeDocuments(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return os;
	}
	
	public void salvarPdf() {
		PDFUtils pdf = new PDFUtils();
		File arquivo = new File("C:\\Users\\Bradesco\\Downloads\\teste");
		List<InputStream> lista = new ArrayList<>();
		
		try {
			for(File file : arquivo.listFiles()) {
				InputStream in = new FileInputStream(file.getAbsolutePath());
				lista.add(in);
			}
			ByteArrayOutputStream arquivoFinal = pdf.juntarPdf(lista);
			PDDocument document = PDDocument.load(arquivoFinal.toByteArray());
			document.save("C:\\Users\\Bradesco\\Downloads\\teste\\arquivo.pdf");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
