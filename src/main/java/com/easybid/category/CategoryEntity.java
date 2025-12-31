package com.easybid.category;

import java.util.List;

import com.easybid.common.BaseEntity;
import com.easybid.item.ItemEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Schema()
@Table(name = "categories")
public class CategoryEntity extends BaseEntity {

  @Column(nullable = false, length = 255)
  private String name;

  @Column(length = 255)
  private String description;

  @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
  private Boolean isActive;

  @OneToMany(mappedBy = "category")
  @JsonManagedReference("category-item")
  private List<ItemEntity> items;

}
