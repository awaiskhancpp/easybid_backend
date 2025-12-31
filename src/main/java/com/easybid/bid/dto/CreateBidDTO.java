package com.easybid.bid.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateBidDTO {
  @NotNull(message = "UserId must not be null")
  @Schema(description = "UserId associated with the bid", example = "69a5223e-b6ca-11ef-8908-03f021d7927c")
  private UUID userId;

  @NotNull(message = "AuctionId must not be null")
  @Schema(description = "AuctionId associated with the bid", example = "69a5223e-b6ca-11ef-8908-03f021d7927c")
  private UUID auctionId;

  @Schema(description = "Amount of the bid", example = "10.5")
  private Double amount;
}
