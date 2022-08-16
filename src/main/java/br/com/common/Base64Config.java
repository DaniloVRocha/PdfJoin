package br.com.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.domain.utils.Base64DecodePdf;

@Configuration
public class Base64Config {
	
	@Bean
	public Base64DecodePdf base64DecodePdf() {
		return new Base64DecodePdf();
	}
}
