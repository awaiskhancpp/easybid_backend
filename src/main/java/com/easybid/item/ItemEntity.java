package com.easybid.item;

import java.util.ArrayList;
import java.util.List;

import com.easybid.auction.AuctionEntity;
import com.easybid.category.CategoryEntity;
import com.easybid.common.BaseEntity;
import com.easybid.itemImage.ItemImageEntity;
import com.easybid.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "items")
public class ItemEntity extends BaseEntity {
  @NotBlank
  @Column(nullable = false, length = 255)
  private String name;

  @Column(length = 255)
  private String description;

  @NotNull(message = "StartingBid must not be null")
  @Column(nullable = false)
  @Min(0)
  private Double startingBid;

  @NotNull(message = "buyNowPrice must not be null")
  @Column(nullable = false)
  @Min(0)
  private Double buyNowPrice;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  @JsonBackReference("user-item")
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  @JsonBackReference("category-item")
  private CategoryEntity category;

  @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
  @JsonManagedReference("item-image")
  private List<ItemImageEntity> images = new ArrayList<>();

  @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
  @JsonManagedReference("item-auction")
  private List<AuctionEntity> auctions = new ArrayList<>();

}
