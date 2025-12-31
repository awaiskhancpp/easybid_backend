package com.easybid.auction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easybid.auction.dto.AuctionResponseDTO;
import com.easybid.auction.dto.CreateAuctionDTO;
import com.easybid.auction.dto.UpdateAuctionDTO;
import com.easybid.enums.AuctionStatus;
import com.easybid.enums.AuctionType;
import com.easybid.enums.IncrementType;
import com.easybid.exceptions.BadRequestException;
import com.easybid.exceptions.ResourceNotFoundException;
import com.easybid.item.ItemEntity;
import com.easybid.item.ItemService;
import com.easybid.mapper.AuctionMapper;

@Service
public class AuctionServiceImpl implements AuctionService {
  private final AuctionRepository auctionRepository;
  private final ItemService itemService;

  public AuctionServiceImpl(AuctionRepository auctionRepository,
      ItemService itemService) {
    this.auctionRepository = auctionRepository;
    this.itemService = itemService;
  }

  @Override
  @Transactional
  public AuctionResponseDTO createAuction(CreateAuctionDTO createAuctionDTO) {
    AuctionEntity auctionEntity = new AuctionEntity();
    ItemEntity item = this.itemService.findItemById(createAuctionDTO.getItemId());
    validateAuctionDates(createAuctionDTO.getStartTime(), createAuctionDTO.getEndTime());
    auctionEntity.setItem(item);
    auctionEntity.setStartTime(createAuctionDTO.getStartTime());
    auctionEntity.setEndTime(createAuctionDTO.getEndTime());
    auctionEntity.setType(createAuctionDTO.getType());
    auctionEntity.setStatus(AuctionStatus.PENDING);
    auctionEntity.setHighestBid(null);
    if (createAuctionDTO.getType() == AuctionType.FIXED) {
      if (createAuctionDTO.getIncrementType() == IncrementType.NONE) {
        throw new BadRequestException("Increment type must be specified for fixed auctions.");
      }
      auctionEntity.setIncrementType(createAuctionDTO.getIncrementType());

      switch (createAuctionDTO.getIncrementType()) {
        case AMOUNT:
          if (createAuctionDTO.getIncrementAmount() == null) {
            throw new BadRequestException("Increment amount must be provided when increment type is AMOUNT.");
          }
          auctionEntity.setIncrementAmount(createAuctionDTO.getIncrementAmount());
          auctionEntity.setIncrementPercentage(null);
          break;
        case PERCENTAGE:
          if (createAuctionDTO.getIncrementPercentage() == null) {
            throw new BadRequestException("Increment percentage must be provided when increment type is PERCENTAGE.");
          }
          auctionEntity.setIncrementPercentage(createAuctionDTO.getIncrementPercentage());
          auctionEntity.setIncrementAmount(null);
          break;
        case NONE:
          auctionEntity.setIncrementAmount(null);
          auctionEntity.setIncrementPercentage(null);
          break;
        default:
          throw new BadRequestException("Invalid increment type specified.");
      }
    } else if (createAuctionDTO.getType() == AuctionType.FREE) {
      auctionEntity.setIncrementType(IncrementType.NONE);
      auctionEntity.setIncrementAmount(null);
      auctionEntity.setIncrementPercentage(null);
    } else {
      throw new BadRequestException("Unsupported auction type.");
    }

    this.auctionRepository.save(auctionEntity);

    return AuctionMapper.toAuctionResponseDTO(this.findAuctionById(auctionEntity.getId()));
  }

  @Override
  public List<AuctionResponseDTO> getAuctions(UUID itemId, UUID userId, UUID buyerId, Boolean winner) {
    List<AuctionEntity> auctions;

    if (itemId != null && userId != null) {
      auctions = auctionRepository.findByItemIdAndUserId(itemId, userId);
    } else if (itemId != null) {
      auctions = auctionRepository.findByItemIdOrderByCreatedAtDesc(itemId);
    } else if (userId != null) {
      auctions = auctionRepository.findByItemUserId(userId);
    } else if (itemId == null && userId == null && buyerId != null && winner != null) {
      System.out.println("winner id " + winner);
      System.out.println("buyer id " + buyerId);
      auctions = auctionRepository.findAllAuctionsByBuyerAndWinnerStatus(buyerId, winner);
    } else if (itemId == null && userId == null && buyerId != null) {
      auctions = auctionRepository.findAllAuctionBuyerParticipate(buyerId);
    } else {
      auctions = auctionRepository.findByDeletedAtIsNullOrderByCreatedAtDesc();
    }

    return auctions.stream()
        .map(AuctionMapper::toAuctionResponseDTO)
        .collect(Collectors.toList());
  }

  @Override
  public AuctionResponseDTO getAuctionById(UUID auctionId) {
    return AuctionMapper
        .toAuctionResponseDTO(this.findAuctionById(auctionId));
  }

  @Override
  public AuctionResponseDTO updateAuction(UUID auctionId, UpdateAuctionDTO updateAuctionDTO) {
    AuctionEntity auction = this.findAuctionById(auctionId);
    if (updateAuctionDTO.getStartTime() != null && updateAuctionDTO.getEndTime() != null) {
      validateAuctionDates(updateAuctionDTO.getStartTime(), updateAuctionDTO.getEndTime());
    } else if (updateAuctionDTO.getStartTime() != null) {
      validateAuctionDates(updateAuctionDTO.getStartTime(), auction.getEndTime());
    } else if (updateAuctionDTO.getEndTime() != null) {
      validateAuctionDates(auction.getStartTime(), updateAuctionDTO.getEndTime());
    }
    AuctionMapper.updateEntityFromDTO(auction, updateAuctionDTO);
    this.auctionRepository.save(auction);
    return AuctionMapper.toAuctionResponseDTO(this.findAuctionById(auctionId));
  }

  @Override
  public void deleteAuction(UUID auctionId) {
    AuctionEntity auction = this.findAuctionById(auctionId);
    auction.setDeletedAt(LocalDateTime.now());
    this.auctionRepository.save(auction);
  }

  @Override
  public AuctionEntity findAuctionById(UUID auctionId) {
    return this.auctionRepository
        .findByIdAndDeletedAtIsNull(auctionId)
        .orElseThrow(() -> new ResourceNotFoundException("Item not found for ID: " + auctionId));
  }

  @Scheduled(fixedRate = 6000)
  @Transactional
  public void updateAuctionStatuses() {
    var now = LocalDateTime.now();
    var auctionsToStart = this.auctionRepository.findByStatusAndStartTimeLessThan(AuctionStatus.PENDING, now);
    for (AuctionEntity auctionEntity : auctionsToStart) {
      auctionEntity.setStatus(AuctionStatus.ACTIVE);
      this.auctionRepository.save(auctionEntity);
    }
    var auctionsToEnd = this.auctionRepository.findByStatusAndEndTimeLessThan(AuctionStatus.ACTIVE, now);
    for (AuctionEntity auctionEntity : auctionsToEnd) {
      if (auctionEntity.getBids().size() <= 0) {
        auctionEntity.setStatus(AuctionStatus.CANCELED);
      } else {
        auctionEntity.setStatus(AuctionStatus.COMPLETED);
      }
      this.auctionRepository.save(auctionEntity);
    }
  }

  private void validateAuctionDates(LocalDateTime startingTime, LocalDateTime endingTime) {
    LocalDateTime now = LocalDateTime.now();

    if (startingTime.isBefore(now)) {
      throw new BadRequestException("Start time must not be in the past.");
    }
    if (endingTime.minusMinutes(1).isBefore(now)) {
      throw new BadRequestException("End time must be at least 1 minutes in the future.");
    }
    if (startingTime.isAfter(endingTime)) {
      throw new BadRequestException("Start time must be before end time.");
    }
    if (startingTime.plusMinutes(1).isAfter(endingTime)) {
      throw new BadRequestException("The time difference between start and end must be at least 1 minutes.");
    }
  }
}
