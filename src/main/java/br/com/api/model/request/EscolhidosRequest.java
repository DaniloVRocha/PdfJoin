package br.com.api.model.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EscolhidosRequest {
	private List<String> escolhidos;
}
