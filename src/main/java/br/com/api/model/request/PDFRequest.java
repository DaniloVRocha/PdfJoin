package br.com.api.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PDFRequest {

	@NotNull
	@NotBlank
	@Size(min = 3, max = 30)
	private String nome;
	
	@NotBlank
	@NotNull
	private String base64;
}
