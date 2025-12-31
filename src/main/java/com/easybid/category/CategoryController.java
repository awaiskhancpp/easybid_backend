package com.easybid.category;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.easybid.category.dto.CategoryResponseDto;
import com.easybid.category.dto.CreateCategoryDTO;
import com.easybid.category.dto.UpdateCategoryDTO;

@Controller
@RestController
@RequestMapping("api/v1")
public class CategoryController {
  private final CategoryService categoryService;

  public CategoryController(final CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PostMapping("/categories")
  public ResponseEntity<CategoryResponseDto> createCategory(
      @RequestBody @Validated CreateCategoryDTO createCategoryDTO) {
    System.out.println(createCategoryDTO.toString());
    CategoryResponseDto category = this.categoryService.createCategory(createCategoryDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(category);
  }

  @GetMapping("/categories")
  public ResponseEntity<List<CategoryResponseDto>> getAllCategories(
      @RequestParam(value = "isActive", required = false) Boolean isActive) {
    List<CategoryResponseDto> categories;
    if (isActive == null) {
      categories = this.categoryService.getAllCategories();
    } else {
      System.out.println("isActive " + isActive);
      categories = this.categoryService.getAllCategories(isActive);
    }
    return ResponseEntity.ok(categories);
  }

  @GetMapping("/categories/{categoryId}")
  public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable UUID categoryId) {
    CategoryResponseDto categoryRes = this.categoryService.getCategoryById(categoryId);
    return ResponseEntity.ok(categoryRes);

  }

  @PatchMapping("/categories/{categoryId}")
  public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable UUID categoryId,
      @RequestBody @Validated UpdateCategoryDTO updateCategoryDTO) {
    System.out.println(updateCategoryDTO.toString());
    CategoryResponseDto categoryRes = this.categoryService.updateCategory(categoryId, updateCategoryDTO);
    return ResponseEntity.ok(categoryRes);
  }

  @DeleteMapping("/categories/{categoryId}")
  public ResponseEntity<String> deleteCategory(@PathVariable UUID categoryId) {
    this.categoryService.deleteCategory(categoryId);
    return ResponseEntity.ok("Category Deleted Successfully");
  }

}
