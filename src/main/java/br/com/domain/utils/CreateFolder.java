package br.com.domain.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import br.com.api.model.response.FolderResponse;

public class CreateFolder {

	public static final File createGoogleFolder(String folderIdParent, String folderName) throws IOException {

		File fileMetadata = new File();

		fileMetadata.setName(folderName);
		fileMetadata.setMimeType("application/vnd.google-apps.folder");

		if (folderIdParent != null) {
			List<String> parents = Arrays.asList(folderIdParent);

			fileMetadata.setParents(parents);
		}
		Drive driveService = GoogleDriveUtils.getDriveService();

		// Create a Folder.
		// Returns File object with id & name fields will be assigned values
		File file = driveService.files().create(fileMetadata).setFields("id, name").execute();

		return file;
	}

	public static FolderResponse createFolder(String name) {
		File folder;
		FolderResponse response = new FolderResponse();
		try {
			folder = createGoogleFolder(null, name);
			response.setId(folder.getId());
			response.setName(folder.getName());
			
			System.out.println("Created folder with id= " + folder.getId());
			System.out.println(" name= " + folder.getName());
			System.out.println("Done!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return response;
	}

}
