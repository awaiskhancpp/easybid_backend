package com.easybid.item.dto;

import java.util.List;

import com.easybid.category.dto.CategoryResponseDto;
import com.easybid.common.BaseResponseDto;
import com.easybid.itemImage.ItemImageEntity;
import com.easybid.user.dto.UserResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "DTO for Response of Item")
public class ItemResponseDTO extends BaseResponseDto {

  @Schema(description = "Name of the Item", example = "Mona Lisa")
  private String name;

  @Schema(description = "Description of the Item", example = "This is Mona Lisa art Panting")
  private String description;

  @Schema(description = "StartingBidof the Item", example = "10.5")
  private Double startingBid;

  @Schema(description = "BuyNOwPrice of the Item", example = "1000.34")
  private Double buyNowPrice;

  @Schema(description = "BuyNOwPrice of the Item", example = "69a5223e-b6ca-11ef-8908-03f021d7927c")
  private UserResponseDTO user;

  @Schema(description = "categoryId of the Item", example = "4dc7b8e2-b6ca-11ef-b524-8f6af1739663")
  private CategoryResponseDto category;

  @Schema(description = "List of the Item Images")
  private List<ItemImageEntity> images;
}
