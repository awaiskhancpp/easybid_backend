package com.easybid.mapper;

import com.easybid.category.CategoryEntity;
import com.easybid.category.dto.CategoryResponseDto;
import com.easybid.category.dto.UpdateCategoryDTO;

public class CategoryMapper {

  public static CategoryResponseDto toCategoryResponseDTO(CategoryEntity categoryEntity) {
    CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
    categoryResponseDto.setId(categoryEntity.getId());
    categoryResponseDto.setName(categoryEntity.getName());
    categoryResponseDto.setDescription(categoryEntity.getDescription());
    categoryResponseDto.setIsActive(categoryEntity.getIsActive());
    categoryResponseDto.setCreatedAt(categoryEntity.getCreatedAt());
    categoryResponseDto.setUpdatedAt(categoryEntity.getUpdatedAt());
    categoryResponseDto.setDeletedAt(categoryEntity.getDeletedAt());
    return categoryResponseDto;
  }

  public static void updateEntityFromDTO(CategoryEntity categoryEntity, UpdateCategoryDTO dto) {
    if (dto.getName() != null && !dto.getName().isBlank()) {
      categoryEntity.setName(dto.getName());
    }
    if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
      categoryEntity.setDescription(dto.getDescription());
    }
    if (dto.getIsActive() != null) {
      categoryEntity.setIsActive(dto.getIsActive());
    }
  }
}
