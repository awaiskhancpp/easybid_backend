package com.easybid.mapper;

import com.easybid.auction.AuctionEntity;
import com.easybid.auction.dto.AuctionResponseDTO;
import com.easybid.auction.dto.UpdateAuctionDTO;
import com.easybid.enums.AuctionType;
import com.easybid.enums.IncrementType;
import com.easybid.exceptions.BadRequestException;

public class AuctionMapper {

  public static AuctionResponseDTO toAuctionResponseDTO(AuctionEntity auctionEntity) {
    AuctionResponseDTO aResponseDTO = new AuctionResponseDTO();
    aResponseDTO.setId(auctionEntity.getId());
    aResponseDTO.setCreatedAt(auctionEntity.getCreatedAt());
    aResponseDTO.setUpdatedAt(auctionEntity.getUpdatedAt());
    aResponseDTO.setDeletedAt(auctionEntity.getDeletedAt());
    aResponseDTO.setItem(ItemMapper.toItemResponseDTO(auctionEntity.getItem()));
    aResponseDTO.setStartTime(auctionEntity.getStartTime());
    aResponseDTO.setEndTime(auctionEntity.getEndTime());
    aResponseDTO.setType(auctionEntity.getType());
    aResponseDTO.setStatus(auctionEntity.getStatus());
    if (auctionEntity.getHighestBid() != null) {
      aResponseDTO.setHighestBid(BidMapper.toBidResponseDTO(auctionEntity.getHighestBid()));
    }
    aResponseDTO.setIncrementType(auctionEntity.getIncrementType());
    aResponseDTO.setIncrementAmount(auctionEntity.getIncrementAmount());
    aResponseDTO.setIncrementPercentage(auctionEntity.getIncrementPercentage());
    return aResponseDTO;
  }

  public static void updateEntityFromDTO(AuctionEntity auctionEntity, UpdateAuctionDTO dto) {
    if (dto.getStartTime() != null) {
      auctionEntity.setStartTime(dto.getStartTime());
    }
    if (dto.getEndTime() != null) {
      auctionEntity.setEndTime(dto.getEndTime());
    }
    if (dto.getStatus() != null) {
      auctionEntity.setStatus(dto.getStatus());
    }
    if (dto.getType() != null) {
      auctionEntity.setType(dto.getType());

      if (dto.getType() == AuctionType.FREE) {
        auctionEntity.setIncrementType(IncrementType.NONE);
        auctionEntity.setIncrementAmount(null);
        auctionEntity.setIncrementPercentage(null);
        return;
      }

      if (dto.getType() == AuctionType.FIXED) {
        if (dto.getIncrementType() == null || dto.getIncrementType() == IncrementType.NONE) {
          throw new BadRequestException("Increment type must be specified and cannot be NONE for FIXED auctions.");
        }
      }
    }

    if (dto.getIncrementType() != null) {
      auctionEntity.setIncrementType(dto.getIncrementType());
      switch (dto.getIncrementType()) {
        case NONE:
          auctionEntity.setIncrementAmount(null);
          auctionEntity.setIncrementPercentage(null);
          break;

        case AMOUNT:
          if (dto.getIncrementAmount() == null) {
            throw new BadRequestException("Increment amount cannot be null when increment type is AMOUNT.");
          }
          auctionEntity.setIncrementAmount(dto.getIncrementAmount());
          auctionEntity.setIncrementPercentage(null);
          break;

        case PERCENTAGE:
          if (dto.getIncrementPercentage() == null) {
            throw new BadRequestException("Increment percentage cannot be null when increment type is PERCENTAGE.");
          }
          auctionEntity.setIncrementPercentage(dto.getIncrementPercentage());
          auctionEntity.setIncrementAmount(null);
          break;

        default:
          throw new BadRequestException("Invalid increment type specified.");
      }
    }
  }
}
