package com.easybid.bid;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.easybid.auction.AuctionEntity;
import com.easybid.auction.AuctionRepository;
import com.easybid.auction.AuctionService;
import com.easybid.bid.dto.BidResponseDTO;
import com.easybid.bid.dto.CreateBidDTO;
import com.easybid.enums.AuctionType;
import com.easybid.enums.IncrementType;
import com.easybid.exceptions.BadRequestException;
import com.easybid.exceptions.ResourceNotFoundException;
import com.easybid.item.ItemEntity;
import com.easybid.mapper.BidMapper;
import com.easybid.user.UserEntity;
import com.easybid.user.UserService;

@Service
public class BidServiceImpl implements BidService {
  private final BidRepository bidRepository;
  private final UserService userService;
  private final AuctionService auctionService;
  private final AuctionRepository auctionRepository;

  public BidServiceImpl(final BidRepository bidRepository,
      final UserService userService,
      final AuctionService auctionService,
      final AuctionRepository auctionRepository) {
    this.bidRepository = bidRepository;
    this.userService = userService;
    this.auctionService = auctionService;
    this.auctionRepository = auctionRepository;
  }

  @Override
  public BidResponseDTO createBid(CreateBidDTO createBidDTO) {
    UserEntity user = userService.findUserById(createBidDTO.getUserId());
    AuctionEntity auction = auctionService.findAuctionById(createBidDTO.getAuctionId());
    ItemEntity auctionItem = auction.getItem();
    AuctionType auctionType = auction.getType();
    BidEntity highestBid = auction.getHighestBid();
    validateBidAmount(createBidDTO.getAmount(), auctionType, auctionItem, highestBid);
    BidEntity bid = new BidEntity();
    bid.setUser(user);
    bid.setAuction(auction);
    calculateBidAmount(bid, auctionType, auction, auctionItem, highestBid, createBidDTO.getAmount());
    bidRepository.save(bid);
    auction.setHighestBid(bid);
    this.auctionRepository.save(auction);
    return BidMapper.toBidResponseDTO(bid);
  }

  @Override
  public List<BidResponseDTO> getFilteredBids(UUID auctionId, UUID userId) {
    if (userId != null) {
      userService.findUserById(userId);
    }

    if (auctionId != null) {
      auctionService.findAuctionById(auctionId);
    }

    Specification<BidEntity> spec = Specification.where(BidSpecifications.filterByAuctionId(auctionId))
        .and(BidSpecifications.filterByUserId(userId));

    return bidRepository.findAll(spec, Sort.by(Sort.Direction.DESC, "amount"))
        .stream()
        .map(BidMapper::toBidResponseDTO)
        .collect(Collectors.toList());
  }

  @Override
  public BidResponseDTO getBidById(UUID bidId) {
    return BidMapper.toBidResponseDTO(this.findBidById(bidId));
  }

  @Override
  public BidEntity findBidById(UUID bidId) {
    return this.bidRepository.findById(bidId)
        .orElseThrow(() -> new ResourceNotFoundException("Bid not found for ID: " + bidId));
  }

  private void validateBidAmount(Double amount, AuctionType auctionType, ItemEntity auctionItem, BidEntity highestBid) {
    if (auctionType == AuctionType.FREE) {
      if (amount == null) {
        throw new BadRequestException("Bid amount is required for FREE type auctions.");
      }
      if (highestBid == null && amount < auctionItem.getStartingBid()) {
        throw new BadRequestException("Bid amount must be greater than or equal to the starting bid.");
      }
      if (highestBid != null && amount <= highestBid.getAmount()) {
        throw new BadRequestException("Bid amount must be higher than the current highest bid.");
      }
    }
  }

  private void calculateBidAmount(BidEntity bid, AuctionType auctionType, AuctionEntity auction,
      ItemEntity auctionItem, BidEntity highestBid, Double providedAmount) {
    if (auctionType == AuctionType.FREE) {
      bid.setAmount(providedAmount);
    } else if (auctionType == AuctionType.FIXED) {
      Double incrementValue = auction.getIncrementType() == IncrementType.AMOUNT
          ? auction.getIncrementAmount()
          : auctionItem.getStartingBid() * auction.getIncrementPercentage();
      if (highestBid == null) {
        bid.setAmount(auctionItem.getStartingBid() + incrementValue);
      } else {
        bid.setAmount(highestBid.getAmount() + incrementValue);
      }
    }
  }

}
