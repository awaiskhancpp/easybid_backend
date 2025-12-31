package com.easybid.item;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, UUID> {

  List<ItemEntity> findByDeletedAtIsNull();

  Optional<ItemEntity> findByIdAndDeletedAtIsNull(UUID itemId);

  @Query("""
        SELECT i
        FROM ItemEntity i
        WHERE (:userId IS NULL OR i.user.id = :userId)
          AND i.deletedAt IS NULL
          AND (:auctioned IS NULL OR
               (:auctioned = TRUE AND EXISTS (
                   SELECT a
                   FROM AuctionEntity a
                   WHERE a.item = i
                     AND a.status IN ('PENDING', 'ACTIVE', 'COMPLETE'))) OR
               (:auctioned = FALSE AND (SIZE(i.auctions) = 0 OR
                   EXISTS (
                       SELECT a
                       FROM AuctionEntity a
                       WHERE a.item = i
                         AND a.status = 'CANCELED'))))
      """)
  List<ItemEntity> findItemsByUserAndAuctionStatus(
      @Param("userId") UUID userId,
      @Param("auctioned") Boolean auctioned);

}
