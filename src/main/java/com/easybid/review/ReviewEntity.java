package com.easybid.review;

import com.easybid.common.BaseEntity;
import com.easybid.item.ItemEntity;
import com.easybid.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "reviews")
public class ReviewEntity extends BaseEntity {

  @NotNull
  @ManyToOne
  @JoinColumn(name = "item_id", nullable = false)
  @JsonBackReference("item-review")
  private ItemEntity item;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "reviewer_id", nullable = false)
  @JsonBackReference("user-review")
  private UserEntity reviewer;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "reviewee_id", nullable = false)
  @JsonBackReference("user-reviewed")
  private UserEntity reviewee;

  @Column(nullable = false, length = 255)
  @NotNull
  private String comment;

  @Column(nullable = false)
  @NotNull
  @Min(value = 1, message = "Rating must be at least 1")
  @Max(value = 5, message = "Rating must be at most 5")
  private Integer rating;
}
