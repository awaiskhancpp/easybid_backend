package com.easybid.review;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.easybid.exceptions.ResourceNotFoundException;
import com.easybid.item.ItemService;
import com.easybid.mapper.ReviewMapper;
import com.easybid.review.dto.CreateReviewDTO;
import com.easybid.review.dto.ReviewResponseDTO;
import com.easybid.user.UserService;

@Service
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository reviewRepository;
  private final ItemService itemService;
  private final UserService userService;

  public ReviewServiceImpl(ReviewRepository reviewRepository,
      ItemService itemService,
      UserService userService) {
    this.reviewRepository = reviewRepository;
    this.userService = userService;
    this.itemService = itemService;
  }

  @Override
  public ReviewResponseDTO createReview(CreateReviewDTO createReviewDTO) {
    ReviewEntity review = new ReviewEntity();
    var item = this.itemService.findItemById(createReviewDTO.getItemId());
    var reviewer = this.userService.findUserById(createReviewDTO.getReviewerId());
    var reviewee = this.userService.findUserById(createReviewDTO.getRevieweeId());
    review.setItem(item);
    review.setReviewer(reviewer);
    review.setReviewee(reviewee);
    review.setRating(createReviewDTO.getRating());
    review.setComment(createReviewDTO.getComment());
    return ReviewMapper.toReviewResponseDTO(review);
  }

  @Override
  public ReviewResponseDTO getReviewById(UUID reviewId) {
    return ReviewMapper.toReviewResponseDTO(this.findReviewById(reviewId));
  }

  @Override
  public List<ReviewResponseDTO> getAllReview() {
    var reviews = this.reviewRepository.findAll();
    return reviews.stream().map(ReviewMapper::toReviewResponseDTO).collect(Collectors.toList());
  }

  @Override
  public ReviewEntity findReviewById(UUID reviewId) {
    return this.reviewRepository.findById(reviewId)
        .orElseThrow(() -> new ResourceNotFoundException("Item Image not found for ID: " + reviewId));

  }

}
