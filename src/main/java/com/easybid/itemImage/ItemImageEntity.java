package com.easybid.itemImage;

import com.easybid.common.BaseEntity;
import com.easybid.item.ItemEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "items_images")
public class ItemImageEntity extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "item_id", nullable = false)
  @JsonBackReference("item-image")
  private ItemEntity item;

  @Column(nullable = false, length = 255)
  private String url;
}
