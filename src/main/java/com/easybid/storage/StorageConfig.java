package com.easybid.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

  @Value("${file.storage.type:LOCAL}")
  private String storageType;

  @Bean
  public FileStorageService fileStorageService() {
    if ("AWS".equalsIgnoreCase(storageType)) {
      throw new UnsupportedOperationException("AWS support is not implemented yet.");
    } else {
      return new LocalFileStorageService();
    }
  }
}
