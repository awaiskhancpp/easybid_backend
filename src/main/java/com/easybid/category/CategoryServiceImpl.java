package com.easybid.category;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.easybid.category.dto.CategoryResponseDto;
import com.easybid.category.dto.CreateCategoryDTO;
import com.easybid.category.dto.UpdateCategoryDTO;
import com.easybid.exceptions.ResourceNotFoundException;
import com.easybid.mapper.CategoryMapper;

@Service
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;

  public CategoryServiceImpl(final CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public CategoryResponseDto createCategory(CreateCategoryDTO createCategoryDTO) {
    if (createCategoryDTO.getIsActive() == null) {
      createCategoryDTO.setIsActive(true);
    }
    CategoryEntity category = new CategoryEntity();
    category.setName(createCategoryDTO.getName());
    category.setDescription(createCategoryDTO.getDescription());
    category.setIsActive(createCategoryDTO.getIsActive());
    this.categoryRepository.save(category);
    return CategoryMapper.toCategoryResponseDTO(category);
  }

  @Override
  public CategoryResponseDto updateCategory(UUID categoryId,
      UpdateCategoryDTO updateUserDTO) {
    CategoryEntity category = this.findCategoryById(categoryId);
    CategoryMapper.updateEntityFromDTO(category, updateUserDTO);
    this.categoryRepository.save(category);
    return CategoryMapper.toCategoryResponseDTO(category);
  }

  @Override
  public CategoryResponseDto getCategoryById(UUID categoryId) {
    return CategoryMapper.toCategoryResponseDTO(findCategoryById(categoryId));
  }

  @Override
  public List<CategoryResponseDto> getAllCategories() {
    List<CategoryEntity> categories = this.categoryRepository.findAll();
    return categories.stream()
        .map(CategoryMapper::toCategoryResponseDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<CategoryResponseDto> getAllCategories(Boolean isActive) {
    List<CategoryEntity> categories = this.categoryRepository.findByDeletedAtIsNullAndIsActive(isActive);
    return categories.stream()
        .map(CategoryMapper::toCategoryResponseDTO)
        .collect(Collectors.toList());
  }

  @Override
  public void deleteCategory(UUID categoryId) {
    CategoryEntity category = this.findCategoryById(categoryId);
    category.setDeletedAt(LocalDateTime.now());
    this.categoryRepository.save(category);
  }

  @Override
  public CategoryEntity findCategoryById(UUID categoryId) {
    CategoryEntity category = this.categoryRepository.findByIdAndDeletedAtIsNull(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category not found for ID: " + categoryId));
    return category;
  }

}
