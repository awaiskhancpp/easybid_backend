package com.easybid.item.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "DTO for Updating a Item")
public class UpdateItemDTO {

  @Pattern(regexp = ".*\\S.*", message = "Name must not be blank")
  @Schema(description = "Name of the Item", example = "Mona Lisa")
  @Size(max = 255)
  private String name;

  @Pattern(regexp = ".*\\S.*", message = "Description must not be blank")
  @Schema(description = "Description of the Item", example = "This is Mona Lisa art Panting")
  @Size(max = 255)
  private String description;

  @Schema(description = "StartingBidof the Item", example = "10.5")
  @Size(max = 255)
  private Double startingBid;

  @Schema(description = "BuyNOwPrice of the Item", example = "1000.34")
  @Size(max = 255)
  private Double buyNowPrice;

  @Schema(description = "categoryId of the Item")
  private UUID categoryId;
}
