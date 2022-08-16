package br.com.domain.service;

import java.io.ByteArrayOutputStream;
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
import br.com.api.model.response.ModelResponse;
import br.com.domain.model.PDF;
import br.com.domain.repository.PDFRepository;
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
	private PDFRepository repository;
	private PDFUtils pdfUtils;

	public PDF salvarPDF(PDFRequest req) {

		LocalDateTime data = LocalDateTime.now();
		OffsetDateTime dataFormat = OffsetDateTime.of(data, ZoneOffset.UTC);

		PDF pdf = new PDF();

		java.io.File file = decoder.decodeBase64(req.getBase64());
		String id = CreateGoogleFile.saveFile(file, req.getNome());
		pdf.setId(id);
		pdf.setNome(req.getNome());
		pdf.setDataInclusao(dataFormat);

		repository.save(pdf);
		if (file.exists()) {
			file.delete();
		}
		return pdf;
	}

	public List<ModelResponse> listarPDFPorPasta(String nomePasta) {
		List<ModelResponse> listaPdf = new ArrayList<>();
		try {
			File folder = GetSubFoldersByName.getFolderByName(nomePasta);
			Drive service = GoogleDriveUtils.getDriveService();
			FileList result = service.files().list().setPageSize(10)
					.setFields("nextPageToken, files(id, name, mimeType)").setQ(" '" + folder.getId() + "' in parents ")
					.execute();
			List<File> files = result.getFiles();
			for (File file : files) {
				ModelResponse pdf = new ModelResponse();
				pdf.setId(file.getId());
				pdf.setNome(file.getName());
				listaPdf.add(pdf);
				System.out.printf("%s (%s)\n", file.getName(), file.getId());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return listaPdf;
	}

	public List<ModelResponse> listarTodasAsPastas() {
		List<ModelResponse> listaDePastas = new ArrayList<>();
		try {
			Drive service = GoogleDriveUtils.getDriveService();
			FileList result = service.files().list().setPageSize(10)
					.setFields("nextPageToken, files(id, name, mimeType)")
					.setQ("mimeType='application/vnd.google-apps.folder' and trashed=false").execute();
			List<File> files = result.getFiles();
			for (File file : files) {
				ModelResponse folder = new ModelResponse();
				folder.setId(file.getId());
				folder.setNome(file.getName());
				listaDePastas.add(folder);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listaDePastas;
	}

	public ByteArrayOutputStream mergePdf(List<String> escolhidos) {
		List<InputStream> filesPDF = new ArrayList<>();
		try {
			Drive service = GoogleDriveUtils.getDriveService();
			

			for (String id : escolhidos) {
				filesPDF.add(service.files().get(id).executeMediaAsInputStream());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pdfUtils.juntarPdf(filesPDF);
	}

}
