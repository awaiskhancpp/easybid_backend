package com.easybid.review.dto;

import com.easybid.common.BaseResponseDto;
import com.easybid.item.dto.ItemResponseDTO;
import com.easybid.user.dto.UserResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReviewResponseDTO extends BaseResponseDto {

  @Schema(description = "Comment of the review", example = "Great seller!")
  private String comment;

  @Schema(description = "Rating of the review", example = "5")
  private Integer rating;

  @Schema(description = "Reviewer of the review")
  private UserResponseDTO reviewer;

  @Schema(description = "Reviewee of the review")
  private UserResponseDTO reviewee;

  @Schema(description = "Item being reviewed")
  private ItemResponseDTO item;
}
