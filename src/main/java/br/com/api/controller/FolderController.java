package br.com.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.model.request.FolderRequest;
import br.com.api.model.response.EntityResponse;
import br.com.api.model.response.FolderResponse;
import br.com.domain.service.FolderService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/folders")
public class FolderController {
	
	private FolderService service;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<EntityResponse> getFolders() {
		return service.getFolders();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FolderResponse createNewFolder(@Valid @RequestBody FolderRequest folder) {
		return service.createNewFolder(folder.getNameFolder());
	}
}
