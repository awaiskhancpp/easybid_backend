package com.easybid.auction;

import java.time.LocalDateTime;
import java.util.List;

import com.easybid.bid.BidEntity;
import com.easybid.common.BaseEntity;
import com.easybid.enums.AuctionStatus;
import com.easybid.enums.AuctionType;
import com.easybid.enums.IncrementType;
import com.easybid.item.ItemEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "auctions")
public class AuctionEntity extends BaseEntity {

  @NotNull
  @ManyToOne
  @JoinColumn(name = "item_id", nullable = false)
  @JsonBackReference("item-auction")
  private ItemEntity item;

  @NotNull
  @Column(nullable = false)
  private LocalDateTime startTime;

  @NotNull
  @Column(nullable = false)
  private LocalDateTime endTime;

  @NotNull
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private AuctionType type;

  @NotNull
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private AuctionStatus status;

  @NotNull
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private IncrementType incrementType;

  @Column()
  private Double incrementAmount;

  @Column()
  private Double incrementPercentage;

  @OneToOne
  @JoinColumn(name = "highest_bid_id")
  @JsonManagedReference("highest-bid")
  private BidEntity highestBid;

  @OneToMany(mappedBy = "auction")
  @JsonManagedReference("auction-bid")
  private List<BidEntity> bids;

}
