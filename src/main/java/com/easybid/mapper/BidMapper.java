package com.easybid.mapper;

import com.easybid.bid.BidEntity;
import com.easybid.bid.dto.BidResponseDTO;

public class BidMapper {

  public static BidResponseDTO toBidResponseDTO(BidEntity bidEntity) {
    BidResponseDTO bid = new BidResponseDTO();
    bid.setId(bidEntity.getId());
    bid.setUser(UserMapper.toUserResponseDTO(bidEntity.getUser()));
    bid.setAmount(bidEntity.getAmount());
    bid.setAuction(bidEntity.getAuction());
    bid.setCreatedAt(bidEntity.getCreatedAt());
    bid.setUpdatedAt(bidEntity.getCreatedAt());
    return bid;
  }

}
