package com.easybid.mapper;

import com.easybid.review.ReviewEntity;
import com.easybid.review.dto.ReviewResponseDTO;

public class ReviewMapper {
  public static ReviewResponseDTO toReviewResponseDTO(ReviewEntity entity) {
    ReviewResponseDTO dto = new ReviewResponseDTO();
    dto.setId(entity.getId());
    dto.setCreatedAt(entity.getCreatedAt());
    dto.setUpdatedAt(entity.getUpdatedAt());
    dto.setComment(entity.getComment());
    dto.setRating(entity.getRating());
    dto.setReviewer(UserMapper.toUserResponseDTO(entity.getReviewer()));
    dto.setReviewee(UserMapper.toUserResponseDTO(entity.getReviewee()));
    dto.setItem(ItemMapper.toItemResponseDTO(entity.getItem()));
    dto.setCreatedAt(entity.getCreatedAt());
    dto.setUpdatedAt(entity.getUpdatedAt());
    return dto;
  }

}
