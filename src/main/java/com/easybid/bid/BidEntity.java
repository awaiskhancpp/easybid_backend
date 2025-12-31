package com.easybid.bid;

import com.easybid.auction.AuctionEntity;
import com.easybid.common.BaseEntity;
import com.easybid.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "bids")
public class BidEntity extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  @JsonBackReference("user-bid")
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name = "auction_id", nullable = false)
  @JsonBackReference("auction-bid")
  private AuctionEntity auction;

  @Column(nullable = false)
  private Double amount;
}
