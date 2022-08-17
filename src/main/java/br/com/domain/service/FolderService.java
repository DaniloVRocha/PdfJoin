package br.com.domain.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import br.com.api.model.response.EntityResponse;
import br.com.api.model.response.FolderResponse;
import br.com.domain.utils.CreateFolder;
import br.com.domain.utils.GoogleDriveUtils;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FolderService {
	
	public List<EntityResponse> getFolders() {
		List<EntityResponse> listaDePastas = new ArrayList<>();
		try {
			Drive service = GoogleDriveUtils.getDriveService();
			FileList result = service.files().list().setPageSize(10)
					.setFields("nextPageToken, files(id, name, mimeType)")
					.setQ("mimeType='application/vnd.google-apps.folder' and trashed=false").execute();
			List<File> files = result.getFiles();
			for (File file : files) {
				EntityResponse folder = new EntityResponse();
				folder.setId(file.getId());
				folder.setNome(file.getName());
				listaDePastas.add(folder);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listaDePastas;
	}
	
	public FolderResponse createNewFolder(String nameFolder) {
		return CreateFolder.createFolder(nameFolder);
	}
	
	public void deleteFolderById(String id) {
		try {
			Drive service = GoogleDriveUtils.getDriveService();
			service.files().delete(id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
