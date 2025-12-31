package com.easybid.category.dto;

import com.easybid.common.BaseResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto extends BaseResponseDto {

  @Schema(description = "Name of the category", example = "Art")
  private String name;

  @Schema(description = "Description of the category", example = "Fine art from various periods")
  private String description;

  @Schema(description = "Indicates if the category is active", example = "true")
  private Boolean isActive;
}
