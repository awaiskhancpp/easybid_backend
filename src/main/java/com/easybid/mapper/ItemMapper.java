package com.easybid.mapper;

import java.util.ArrayList;

import com.easybid.item.ItemEntity;
import com.easybid.item.dto.ItemResponseDTO;
import com.easybid.item.dto.UpdateItemDTO;

public class ItemMapper {

  public static ItemResponseDTO toItemResponseDTO(ItemEntity item) {
    ItemResponseDTO responseDTO = new ItemResponseDTO();
    responseDTO.setId(item.getId());
    responseDTO.setCreatedAt(item.getCreatedAt());
    responseDTO.setUpdatedAt(item.getUpdatedAt());
    responseDTO.setDeletedAt(item.getDeletedAt());
    responseDTO.setName(item.getName());
    responseDTO.setDescription(item.getDescription());
    responseDTO.setStartingBid(item.getStartingBid());
    responseDTO.setBuyNowPrice(item.getBuyNowPrice());
    responseDTO.setUser(UserMapper.toUserResponseDTO(item.getUser()));
    responseDTO.setCategory(CategoryMapper.toCategoryResponseDTO(item.getCategory()));
    responseDTO.setImages(new ArrayList<>(item.getImages()));
    return responseDTO;
  }

  public static void updateEntityFromDTO(ItemEntity itemEntity, UpdateItemDTO updateItemDto) {
    if (updateItemDto.getName() != null && !updateItemDto.getName().isBlank()) {
      itemEntity.setName(updateItemDto.getName());
    }
    if (updateItemDto.getDescription() != null && !updateItemDto.getDescription().isBlank()) {
      itemEntity.setDescription(updateItemDto.getDescription());
    }
    if (updateItemDto.getStartingBid() != null && updateItemDto.getStartingBid() >= 0) {
      itemEntity.setStartingBid(updateItemDto.getStartingBid());
    }
    if (updateItemDto.getBuyNowPrice() != null && updateItemDto.getBuyNowPrice() >= 0) {
      itemEntity.setBuyNowPrice(updateItemDto.getBuyNowPrice());
    }
  }
}
