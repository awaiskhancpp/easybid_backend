package com.easybid.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "DTO for updating an existing user")
public class UpdateCategoryDTO {

  @Pattern(regexp = ".*\\S.*", message = "Name must not be blank")
  @Schema(description = "Name for category", example = "Arts")
  @Size(max = 255)
  private String name;

  @Pattern(regexp = ".*\\S.*", message = "Description must not be  blank")
  @Schema(description = "Description of the category", example = "Fine art from various periods")
  @Size(max = 255)
  private String description;

  @Schema(description = "Indicates if the category is active")
  private Boolean isActive;

}
