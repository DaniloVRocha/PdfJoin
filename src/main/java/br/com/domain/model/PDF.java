package br.com.domain.model;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter
@Getter
public class PDF {
	
	@EqualsAndHashCode.Include
	private String id;
	
	@NotBlank
	private String nome;
	private OffsetDateTime dataInclusao;
}
