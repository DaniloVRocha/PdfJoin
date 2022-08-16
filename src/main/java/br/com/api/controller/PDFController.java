package br.com.api.controller;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
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
import br.com.api.model.response.ModelResponse;
import br.com.domain.service.PDFService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/joinpdf")
public class PDFController {
	
	private PDFService service;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void salvarPDF(@Valid @RequestBody PDFRequest req) {
		service.salvarPDF(req);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ModelResponse> listarPdf(@RequestParam(name="nomePasta") String nomePasta) {
		return service.listarPDFPorPasta(nomePasta);
	}
	
	@GetMapping("/folders")
	@ResponseStatus(HttpStatus.OK)
	public List<ModelResponse> listarPastas() {
		return service.listarTodasAsPastas();
	}
	
	@RequestMapping(value = "/merge-documents", method = RequestMethod.POST, consumes = "application/json")
	public ByteArrayOutputStream mergePdf(@RequestBody EscolhidosRequest escolhidos) {
		return service.mergePdf(escolhidos.getEscolhidos());
	}

}
