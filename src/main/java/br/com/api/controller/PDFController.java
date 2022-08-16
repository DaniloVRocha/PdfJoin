package br.com.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.model.request.EscolhidosRequest;
import br.com.api.model.request.PDFRequest;
import br.com.api.model.response.EntityResponse;
import br.com.domain.service.PDFService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/documents")
public class PDFController {
	
	private PDFService service;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void savePDF(@Valid @RequestBody PDFRequest req) {
		service.savePDF(req);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<EntityResponse> getFileByFolder(@RequestParam(name="folderName") String folderName) {
		return service.getFileByFolder(folderName);
	}
	
	@RequestMapping(value = "/merge-documents", method = RequestMethod.POST, produces = "application/pdf")
	public ResponseEntity<InputStreamResource> downloadPDFFile(@RequestBody EscolhidosRequest req)
	        throws IOException {
		
		InputStream file = service.mergePDF(req.getSelected());
	    return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(new InputStreamResource(file));
	}

}
