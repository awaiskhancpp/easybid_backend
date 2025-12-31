package com.easybid.auction.dto;

import java.time.LocalDateTime;

import com.easybid.bid.dto.BidResponseDTO;
import com.easybid.common.BaseResponseDto;
import com.easybid.enums.AuctionStatus;
import com.easybid.enums.AuctionType;
import com.easybid.enums.IncrementType;
import com.easybid.item.dto.ItemResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Response DTO for Auction Details")
public class AuctionResponseDTO extends BaseResponseDto {

  @Schema(description = "Item associated with the Auction")
  private ItemResponseDTO item;

  @Schema(description = "Highes bid")
  private BidResponseDTO highestBid;

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
