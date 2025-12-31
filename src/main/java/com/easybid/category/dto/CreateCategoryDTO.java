package com.easybid.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "DTO for Creating a Category")
public class CreateCategoryDTO {

  @NotBlank(message = "Name must not be null or blank")
  @Schema(description = "Name of the category", example = "Arts")
  @Size(max = 255)
  private String name;

  @Schema(description = "Description of the category", example = "Fine art from various periods")
  @Size(max = 255)
  private String description;

  @Schema(description = "Indicates if the category is active", defaultValue = "true")
  private Boolean isActive;
}
