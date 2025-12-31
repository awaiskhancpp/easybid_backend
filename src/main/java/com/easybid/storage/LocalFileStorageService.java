package com.easybid.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.easybid.common.Constants;
import com.easybid.exceptions.BadRequestException;
import com.easybid.exceptions.FileStorageException;

@Service
public class LocalFileStorageService implements FileStorageService {

  @Value("${app.base-url}")
  private String baseUrl;

  public LocalFileStorageService() {
    File storageDir = new File(Constants.STORAGE_DIR);
    if (!storageDir.exists()) {
      storageDir.mkdir();
    }
  }

  @Override
  public String uploadFile(MultipartFile file) {
    if (file.isEmpty() || file == null) {
      throw new BadRequestException("File cannot be null or empty");
    }
    try {
      String fileExtension = getFileExtension(file.getOriginalFilename());
      String uniqueFileName = UUID.randomUUID().toString() + "_" + (fileExtension.isEmpty() ? "" : "." + fileExtension);

      Path filePath = Paths.get(Constants.STORAGE_DIR, uniqueFileName);
      Files.write(filePath, file.getBytes());

      return getFileUrl(uniqueFileName);
    } catch (IOException e) {
      throw new FileStorageException("Error while saving file: " + e.getMessage());
    }
  }

  @Override
  public void deleteFile(String fileUrl) {
    if (fileUrl == null || fileUrl.isEmpty()) {
      throw new BadRequestException("File URL cannot be null or empty");
    }

    String fileName = extractFileNameFromUrl(fileUrl);

    try {
      Path filePath = Paths.get(Constants.STORAGE_DIR, fileName);
      Files.deleteIfExists(filePath);
    } catch (IOException e) {
      throw new FileStorageException("Error while deleting file: " + e.getMessage());
    }
  }

  private String getFileExtension(String fileName) {
    if (fileName == null || !fileName.contains(".")) {
      return "";
    }
    return fileName.substring(fileName.lastIndexOf(".") + 1);
  }

  private String getFileUrl(String fileName) {
    return baseUrl + "files/" + fileName;
  }

  private String extractFileNameFromUrl(String fileUrl) {
    String[] urlParts = fileUrl.split("/files/");
    if (urlParts.length != 2) {
      throw new BadRequestException("Invalid file URL");
    }
    return urlParts[1];
  }
}
