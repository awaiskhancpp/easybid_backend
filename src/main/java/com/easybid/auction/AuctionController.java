package com.easybid.auction;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.easybid.auction.dto.AuctionResponseDTO;
import com.easybid.auction.dto.CreateAuctionDTO;
import com.easybid.auction.dto.UpdateAuctionDTO;

@RequestMapping("/api/v1")
@RestController
@Controller
public class AuctionController {
  private final AuctionService auctionService;

  public AuctionController(final AuctionService auctionService) {
    this.auctionService = auctionService;
  }

  @PostMapping("/auctions")
  public ResponseEntity<AuctionResponseDTO> createAuction(@RequestBody @Validated CreateAuctionDTO createAuctionDTO) {
    AuctionResponseDTO auctionRes = this.auctionService.createAuction(createAuctionDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(auctionRes);
  }

  @GetMapping("/auctions")
  public ResponseEntity<List<AuctionResponseDTO>> getAuctions(
      @RequestParam(value = "itemId", required = false) UUID itemId,
      @RequestParam(value = "userId", required = false) UUID userId,
      @RequestParam(value = "buyerId", required = false) UUID buyerId,
      @RequestParam(value = "winner", required = false) Boolean winner) {
    List<AuctionResponseDTO> auctionRes = this.auctionService.getAuctions(itemId, userId, buyerId, winner);
    return ResponseEntity.ok(auctionRes);
  }

  @GetMapping("/auctions/{auctionId}")
  public ResponseEntity<AuctionResponseDTO> getAuctionById(@PathVariable UUID auctionId) {
    AuctionResponseDTO auctionRes = this.auctionService.getAuctionById(auctionId);
    return ResponseEntity.ok(auctionRes);
  }

  @PatchMapping("/auctions/{auctionId}")
  public ResponseEntity<AuctionResponseDTO> updateAuction(@PathVariable UUID auctionId,
      @RequestBody @Validated UpdateAuctionDTO updateAuctionDTO) {
    AuctionResponseDTO auctionRes = this.auctionService.updateAuction(auctionId, updateAuctionDTO);
    return ResponseEntity.ok(auctionRes);
  }

  @DeleteMapping("/auctions/{auctionId}")
  public ResponseEntity<String> deleteAuction(@PathVariable UUID auctionId) {
    this.auctionService.deleteAuction(auctionId);
    return ResponseEntity.ok("Auction Deleted Successfully");
  }

}
