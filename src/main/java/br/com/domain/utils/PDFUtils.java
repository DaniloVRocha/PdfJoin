package br.com.domain.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

public class PDFUtils {

	public InputStream mergePDF(List<InputStream> escolhidos) {
		
		PDFMergerUtility pdfMerger = new PDFMergerUtility();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		InputStream inputStream = null;
		
		try {
			pdfMerger.setDestinationStream(os);
			pdfMerger.addSources(escolhidos);
			pdfMerger.mergeDocuments(null);
			byte[] bytes = os.toByteArray();
			inputStream = new ByteArrayInputStream(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return inputStream;
	}
	
	public ByteArrayOutputStream mergePDFFile(List<File> escolhidos) {
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
}
