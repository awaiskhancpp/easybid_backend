package com.easybid.bid;

import java.util.List;
import java.util.UUID;

import com.easybid.bid.dto.BidResponseDTO;
import com.easybid.bid.dto.CreateBidDTO;

public interface BidService {

  BidResponseDTO createBid(CreateBidDTO createBidDTO);

  List<BidResponseDTO> getFilteredBids(UUID auctionId, UUID userId);

  BidResponseDTO getBidById(UUID bidId);

  BidEntity findBidById(UUID bidId);
}
