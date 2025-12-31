package com.easybid.review;

import java.util.List;
import java.util.UUID;

import com.easybid.review.dto.CreateReviewDTO;
import com.easybid.review.dto.ReviewResponseDTO;

public interface ReviewService {

  ReviewResponseDTO createReview(CreateReviewDTO createReviewDTO);

  ReviewResponseDTO getReviewById(UUID reviewId);

  List<ReviewResponseDTO> getAllReview();

  ReviewEntity findReviewById(UUID reviewId);
}
