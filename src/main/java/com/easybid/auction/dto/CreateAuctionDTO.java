package com.easybid.auction.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.easybid.enums.AuctionType;
import com.easybid.enums.IncrementType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "DTO for Creating an Auction")
public class CreateAuctionDTO {

  @NotNull(message = "ItemId must not be null")
  @Schema(description = "ItemId associated with the Auction", example = "69a5223e-b6ca-11ef-8908-03f021d7927c")
  private UUID itemId;

  @NotNull(message = "Auction start time must not be null")
  @Schema(description = "Start time of the Auction", example = "2024-12-25T10:00:00")
  private LocalDateTime startTime;

  @NotNull(message = "Auction end time must not be null")
  @Schema(description = "End time of the Auction", example = "2024-12-26T10:00:00")
  private LocalDateTime endTime;

  @NotNull(message = "Auction type must not be null")
  @Schema(description = "Type of the Auction", example = "FIXED")
  private AuctionType type;

  @NotNull(message = "Increment type must not be null")
  @Schema(description = "Increment type for bidding", example = "AMOUNT")
  private IncrementType incrementType;

  @Schema(description = "Increment amount for bidding", example = "5.00")
  private Double incrementAmount;

  @Schema(description = "Increment percentage for bidding", example = "10.00")
  private Double incrementPercentage;

}
