package com.easybid.bid.dto;

import com.easybid.auction.AuctionEntity;
import com.easybid.common.BaseResponseDto;
import com.easybid.user.dto.UserResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BidResponseDTO extends BaseResponseDto {

  @Schema(description = "User associated with the bid")
  private UserResponseDTO user;

  @Schema(description = "AuctionId associated with the bid")
  private AuctionEntity auction;

  @Schema(description = "Amount of the bid", example = "10.5")
  private Double amount;
}
