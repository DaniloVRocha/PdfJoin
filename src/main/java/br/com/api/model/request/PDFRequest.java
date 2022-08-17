package br.com.api.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PDFRequest {

	@NotBlank
	private String name;
	
	@NotBlank
	private String base64;
}
