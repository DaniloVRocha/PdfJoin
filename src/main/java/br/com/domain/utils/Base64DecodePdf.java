package br.com.domain.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;

public class Base64DecodePdf {
	
	  public File decodeBase64(String b64) {
		  
		  
		  	b64 = b64.replace("data:application/pdf;base64,", "");
		    File file = new File("temp.pdf");
		    try (FileOutputStream fos = new FileOutputStream(file); ) {
		  
		      byte[] decoder = Base64.getDecoder().decode(b64);

		      fos.write(decoder);
		      fos.flush();
		      fos.close();
		      System.out.println("PDF File Saved");
		      
		    } catch (Exception e) {
		       e.printStackTrace();
		    }
		    return file;
	  }

}
