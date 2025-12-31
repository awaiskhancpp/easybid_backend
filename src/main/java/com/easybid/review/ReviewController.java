package com.easybid.review;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.easybid.review.dto.CreateReviewDTO;
import com.easybid.review.dto.ReviewResponseDTO;

@RestController
@RequestMapping("/api/v1")
@Controller
public class ReviewController {
  private final ReviewService reviewService;

  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @PostMapping("/reviews")
  public ResponseEntity<ReviewResponseDTO> createReview(@RequestBody CreateReviewDTO createReviewDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(createReviewDTO));
  }

  @GetMapping("/reviews")
  public ResponseEntity<List<ReviewResponseDTO>> getAllReview() {
    return ResponseEntity.ok(reviewService.getAllReview());
  }

  @GetMapping("/reviews/{userId}")
  public ResponseEntity<ReviewResponseDTO> getReviewById(@RequestParam UUID userId) {
    return ResponseEntity.ok(reviewService.getReviewById(userId));
  }

}
