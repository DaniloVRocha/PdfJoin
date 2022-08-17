package br.com.domain.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import br.com.api.model.request.PDFRequest;
import br.com.api.model.response.EntityResponse;
import br.com.domain.model.Folder;
import br.com.domain.model.PDF;
import br.com.domain.utils.Base64DecodePdf;
import br.com.domain.utils.CreateGoogleFile;
import br.com.domain.utils.GetSubFoldersByName;
import br.com.domain.utils.GoogleDriveUtils;
import br.com.domain.utils.PDFUtils;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PDFService {

	private Base64DecodePdf decoder;
	private PDFUtils pdfUtils;
	private FolderService folderService;

	public PDF savePDF(PDFRequest req, String idPasta) {

		LocalDateTime data = LocalDateTime.now();
		OffsetDateTime dataFormat = OffsetDateTime.of(data, ZoneOffset.UTC);

		PDF pdf = new PDF();

		java.io.File file = decoder.decodeBase64(req.getBase64());
		String id = CreateGoogleFile.saveFile(idPasta, file, req.getName());
		pdf.setId(id);
		pdf.setNome(req.getName());
		pdf.setDataInclusao(dataFormat);
		
		if (file.exists()) {
			file.delete();
		}
		return pdf;
	}

	public List<EntityResponse> getFileByFolder(String nomePasta) {
		List<EntityResponse> listaPdf = new ArrayList<>();
		try {
			File folder = GetSubFoldersByName.getFolderByName(nomePasta);
			Drive service = GoogleDriveUtils.getDriveService();
			FileList result = service.files().list().setPageSize(10)
					.setFields("nextPageToken, files(id, name, mimeType)").setQ(" '" + folder.getId() + "' in parents and trashed=false")
					.execute();
			List<File> files = result.getFiles();
			for (File file : files) {
				if(file.getMimeType().equals("application/pdf")) {
					EntityResponse pdf = new EntityResponse();
					pdf.setId(file.getId());
					pdf.setNome(file.getName());
					listaPdf.add(pdf);
				}else {
					System.out.println("Não é um pdf");
					System.out.printf("%s (%s)\n", file.getName(), file.getId());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return listaPdf;
	}
	
	public InputStream mergePDF(List<String> escolhidos) {
		List<InputStream> filesPDF = new ArrayList<>();
		try {
			Drive service = GoogleDriveUtils.getDriveService();
			

			for (String id : escolhidos) {
				filesPDF.add(service.files().get(id).executeMediaAsInputStream());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pdfUtils.mergePDF(filesPDF);
	}
	
	public List<Folder> listAllPDF() {
		List<Folder> todosArquivos = new ArrayList<>();
		List<EntityResponse> folders = folderService.getFolders();	
		
		for(EntityResponse folder : folders) {
			List<EntityResponse> filesByFolder = getFileByFolder(folder.getNome());
			
			Folder pasta = new Folder();
			pasta.setId(folder.getId());
			pasta.setNome(folder.getNome());
			pasta.setDocumentos(filesByFolder);
			
			todosArquivos.add(pasta);
		}
		return todosArquivos;
	}
	
	public InputStream getById(String id) {
		InputStream arquivo = null;
		try {
			Drive service = GoogleDriveUtils.getDriveService();
			 arquivo = service.files().get(id).executeMediaAsInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arquivo;
	}
}
