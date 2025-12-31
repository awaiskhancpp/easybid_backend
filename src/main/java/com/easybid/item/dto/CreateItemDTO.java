package com.easybid.item.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "DTO for Creating a Item")
public class CreateItemDTO {
  @NotBlank(message = "Name must not be null or blank")
  @Schema(description = "Name of the Item", example = "Mona Lisa")
  @Size(max = 255)
  private String name;

  @Schema(description = "Description of the Item", example = "This is Mona Lisa art Panting")
  @Size(max = 255)
  private String description;

  @NotNull(message = "StartingBid must not be null")
  @Schema(description = "StartingBidof the Item", example = "10.5")
  @Size(max = 255)
  private Double startingBid;

  @NotNull(message = "BuyNOwPrice must not be null")
  @Schema(description = "BuyNOwPrice of the Item", example = "1000.34")
  @Size(max = 255)
  private Double buyNowPrice;

  @NotNull(message = "userId must not be null")
  @Schema(description = "UserId of the Item", example = "69a5223e-b6ca-11ef-8908-03f021d7927c")
  private UUID userId;

  @NotNull(message = "categroyId must not be null")
  @Schema(description = "categoryId of the Item", example = "4dc7b8e2-b6ca-11ef-b524-8f6af1739663")
  private UUID categoryId;
}
