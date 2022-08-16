package br.com.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.domain.utils.PDFUtils;

@Configuration
public class PDFUtilsConfig {
	
	@Bean
	public PDFUtils pdfUtils() {
		return new PDFUtils();
	}
}
