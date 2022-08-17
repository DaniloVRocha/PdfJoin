package br.com.api.model.request;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PDFRequest {

	@NotNull
	@NotBlank
	
	private String name;
	
	@NotBlank
	@NotNull
	private String base64;
}
