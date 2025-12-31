package com.easybid.bid;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.easybid.bid.dto.BidResponseDTO;
import com.easybid.bid.dto.CreateBidDTO;

@Controller
@RestController
@RequestMapping("/api/v1")
public class BidController {
  private final BidService bidService;

  public BidController(final BidService bidService) {
    this.bidService = bidService;
  }

  @PostMapping("/bids")
  public ResponseEntity<BidResponseDTO> createBid(@RequestBody @Validated CreateBidDTO createBidDTO) {
    BidResponseDTO bid = this.bidService.createBid(createBidDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(bid);
  }

  @GetMapping("/bids")
  public ResponseEntity<List<BidResponseDTO>> getFilteredBids(
      @RequestParam(required = false) UUID auctionId,
      @RequestParam(required = false) UUID userId) {
    List<BidResponseDTO> bids = bidService.getFilteredBids(auctionId, userId);
    return ResponseEntity.ok(bids);
  }

  @GetMapping("/bids/{bidId}")
  public ResponseEntity<BidResponseDTO> getBidById(@PathVariable UUID bidId) {
    BidResponseDTO bid = this.bidService.getBidById(bidId);
    return ResponseEntity.ok(bid);
  }

}
