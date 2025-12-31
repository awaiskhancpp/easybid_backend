package com.easybid.auction.dto;

import java.time.LocalDateTime;

import com.easybid.enums.AuctionStatus;
import com.easybid.enums.AuctionType;
import com.easybid.enums.IncrementType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO for Updating an Auction")
public class UpdateAuctionDTO {

  @Schema(description = "Start time of the Auction", example = "2024-12-25T10:00:00")
  private LocalDateTime startTime;

  @Schema(description = "End time of the Auction", example = "2024-12-26T10:00:00")
  private LocalDateTime endTime;

  @Schema(description = "Type of the Auction", example = "FIXED")
  private AuctionType type;

  @Schema(description = "Status of the Auction", example = "ACTIVE")
  private AuctionStatus status;

  @Schema(description = "Increment type for bidding", example = "AMOUNT")
  private IncrementType incrementType;

  @Schema(description = "Increment amount for bidding", example = "5.00")
  private Double incrementAmount;

  @Schema(description = "Increment percentage for bidding", example = "10.00")
  private Double incrementPercentage;

}
