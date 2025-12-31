package com.easybid.itemImage;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.easybid.item.ItemEntity;

public interface ItemImageService {
  void createItemImage(ItemEntity itemEntity, MultipartFile[] files);

  void deleteItemImage(ItemImageEntity imageEntity);

  ItemImageEntity findItemImageById(UUID itemImageId);

}
