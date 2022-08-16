package br.com.domain.model;

import java.time.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter
@Getter
@Entity
public class PDF {
	
	@Id
	@EqualsAndHashCode.Include
	private String id;
	
	@NotBlank
	@Size(min = 3, max = 30)
	private String nome;
	private OffsetDateTime dataInclusao;
}
