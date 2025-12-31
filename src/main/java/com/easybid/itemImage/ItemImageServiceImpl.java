package com.easybid.itemImage;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.easybid.common.Constants;
import com.easybid.exceptions.FileSizeExceededException;
import com.easybid.exceptions.ResourceNotFoundException;
import com.easybid.exceptions.UnsupportedFileFormatException;
import com.easybid.item.ItemEntity;
import com.easybid.storage.FileStorageService;

@Service
public class ItemImageServiceImpl implements ItemImageService {

  private final ItemImageRepository itemImageRepository;
  private final FileStorageService fileStorageService;

  public ItemImageServiceImpl(
      final ItemImageRepository itemImageRepository,
      final FileStorageService fileStorageService) {
    this.itemImageRepository = itemImageRepository;
    this.fileStorageService = fileStorageService;
  }

  @Override
  public void createItemImage(ItemEntity itemEntity, MultipartFile[] files) {
    for (MultipartFile file : files) {
      if (file.getContentType() == null || !file.getContentType().startsWith(Constants.ALLOWED_FILE_TYPE)) {
        throw new UnsupportedFileFormatException("Please upload a valid image file.");
      }
      if (file.getSize() > Constants.MAX_FILE_SIZE) {
        throw new FileSizeExceededException("File size exceeds the maximum limit of 5 MB.");
      }
      String url = this.fileStorageService.uploadFile(file);
      ItemImageEntity imageEntity = new ItemImageEntity();
      imageEntity.setItem(itemEntity);
      imageEntity.setUrl(url);
      this.itemImageRepository.save(imageEntity);
    }
  }

  @Override
  public void deleteItemImage(ItemImageEntity imageEntity) {
    UUID itemImageId = imageEntity.getId();
    this.findItemImageById(itemImageId);
    this.fileStorageService.deleteFile(imageEntity.getUrl());
    this.itemImageRepository.deleteById(itemImageId);
  }

  @Override
  public ItemImageEntity findItemImageById(UUID itemImageId) {
    return this.itemImageRepository.findById(itemImageId)
        .orElseThrow(() -> new ResourceNotFoundException("Item Image not found for ID: " + itemImageId));

  }

}
