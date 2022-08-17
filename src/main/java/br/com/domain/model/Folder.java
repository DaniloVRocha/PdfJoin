package br.com.domain.model;

import java.util.List;

import br.com.api.model.response.EntityResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Folder {	
	private String id;
	private String nome;
	private List<EntityResponse> documentos;
}
