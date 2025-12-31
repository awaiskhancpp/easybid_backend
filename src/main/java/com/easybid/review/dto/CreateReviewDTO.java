package com.easybid.review.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for creating a review")
public class CreateReviewDTO {

  @NotNull(message = "itemId must not be null")
  @Schema(description = "ID of the item being reviewed", example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID itemId;

  @NotNull(message = "reviewerId must not be null")
  @Schema(description = "ID of the reviewer", example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID reviewerId;

  @NotNull(message = "revieweeId must not be null")
  @Schema(description = "ID of the reviewee", example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID revieweeId;

  @NotBlank(message = "Comment must not be null or blank")
  @Size(max = 255, min = 1, message = "Comment must be between 1 and 255 characters")
  private String comment;

  @NotNull(message = "Rating must not be null")
  @Schema(description = "Rating of the item", example = "5")
  @Min(value = 1, message = "Rating must be at least 1")
  @Max(value = 5, message = "Rating must be at most 5")
  private Integer rating;
}
