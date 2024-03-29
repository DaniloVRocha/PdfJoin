package br.com.api.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FolderRequest {
	

	@NotEmpty
	@Size(min=3, max=20)
	private String nameFolder;

}
