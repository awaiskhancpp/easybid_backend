package com.easybid.auction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.easybid.enums.AuctionStatus;

@Repository
public interface AuctionRepository extends JpaRepository<AuctionEntity, UUID> {

  List<AuctionEntity> findByItemIdOrderByCreatedAtDesc(UUID itemId);

  List<AuctionEntity> findByDeletedAtIsNullOrderByCreatedAtDesc();

  @Query("SELECT a FROM AuctionEntity a WHERE a.item.user.id = :userId AND a.deletedAt IS NULL")
  List<AuctionEntity> findByItemUserId(@Param("userId") UUID userId);

  @Query("SELECT a FROM AuctionEntity a WHERE a.item.id = :itemId AND a.item.user.id = :userId AND a.deletedAt IS NULL")
  List<AuctionEntity> findByItemIdAndUserId(@Param("itemId") UUID itemId, @Param("userId") UUID userId);

  @Query("SELECT a FROM AuctionEntity a JOIN a.bids b WHERE b.user.id = :buyerId AND a.deletedAt IS NULL")
  List<AuctionEntity> findAllAuctionBuyerParticipate(@Param("buyerId") UUID buyerId);

  @Query("""
        SELECT a
        FROM AuctionEntity a
        WHERE (:winner = true AND a.highestBid.user.id = :buyerId)
           OR (:winner = false AND EXISTS (
               SELECT b FROM a.bids b WHERE b.user.id = :buyerId
           ))
           AND a.deletedAt IS NULL
      """)
  List<AuctionEntity> findAllAuctionsByBuyerAndWinnerStatus(
      @Param("buyerId") UUID buyerId,
      @Param("winner") boolean winner);

  Optional<AuctionEntity> findByIdAndDeletedAtIsNull(UUID auctionId);

  List<AuctionEntity> findByStatusAndStartTimeLessThan(AuctionStatus status, LocalDateTime time);

  List<AuctionEntity> findByStatusAndEndTimeLessThan(AuctionStatus status, LocalDateTime time);

}
