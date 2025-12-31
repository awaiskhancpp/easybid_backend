package com.easybid.bid;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

public class BidSpecifications {
  public static Specification<BidEntity> filterByAuctionId(UUID auctionId) {
    return (root, query, criteriaBuilder) -> auctionId == null ? null
        : criteriaBuilder.equal(root.get("auction").get("id"), auctionId);
  }

  public static Specification<BidEntity> filterByUserId(UUID userId) {
    return (root, query, criteriaBuilder) -> userId == null ? null
        : criteriaBuilder.equal(root.get("user").get("id"), userId);
  }
}
