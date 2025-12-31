package com.easybid.category;

import java.util.List;
import java.util.UUID;

import com.easybid.category.dto.CategoryResponseDto;
import com.easybid.category.dto.CreateCategoryDTO;
import com.easybid.category.dto.UpdateCategoryDTO;

public interface CategoryService {

  CategoryResponseDto createCategory(CreateCategoryDTO createCategoryDTO);

  CategoryResponseDto updateCategory(UUID categoryId, UpdateCategoryDTO updateCategoryDTO);

  CategoryResponseDto getCategoryById(UUID categoryId);

  List<CategoryResponseDto> getAllCategories();

  List<CategoryResponseDto> getAllCategories(Boolean isActive);

  void deleteCategory(UUID categoryId);

  CategoryEntity findCategoryById(UUID categoryId);

}
