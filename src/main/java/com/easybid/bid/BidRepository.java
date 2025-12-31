package com.easybid.bid;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BidRepository extends JpaRepository<BidEntity, UUID>, JpaSpecificationExecutor<BidEntity> {
}
