package com.easybid.item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.easybid.category.CategoryEntity;
import com.easybid.category.CategoryService;
import com.easybid.exceptions.ResourceNotFoundException;
import com.easybid.item.dto.CreateItemDTO;
import com.easybid.item.dto.ItemResponseDTO;
import com.easybid.item.dto.UpdateItemDTO;
import com.easybid.itemImage.ItemImageEntity;
import com.easybid.itemImage.ItemImageService;
import com.easybid.mapper.ItemMapper;
import com.easybid.user.UserEntity;
import com.easybid.user.UserService;

@Service
public class ItemServiceImpl implements ItemService {

  private final ItemRepository itemRepository;
  private final ItemImageService itemImageService;
  private final UserService userService;
  private final CategoryService categoryService;

  public ItemServiceImpl(final ItemRepository itemRepository,
      final ItemImageService itemImageService,
      final UserService userService,
      final CategoryService categoryService) {
    this.itemRepository = itemRepository;
    this.itemImageService = itemImageService;
    this.userService = userService;
    this.categoryService = categoryService;
  }

  @Override
  @Transactional
  public ItemResponseDTO createItem(final CreateItemDTO createItemDTO, final MultipartFile[] files) {
    final ItemEntity item = new ItemEntity();
    UserEntity user = this.userService.findUserById(createItemDTO.getUserId());
    CategoryEntity category = this.categoryService.findCategoryById(createItemDTO.getCategoryId());
    item.setName(createItemDTO.getName());
    item.setDescription(createItemDTO.getDescription());
    item.setBuyNowPrice(createItemDTO.getBuyNowPrice());
    item.setStartingBid(createItemDTO.getStartingBid());
    item.setUser(user);
    item.setCategory(category);
    this.itemRepository.save(item);
    this.itemImageService.createItemImage(item, files);
    return ItemMapper.toItemResponseDTO(this.findItemById(item.getId()));
  }

  @Override
  public List<ItemResponseDTO> getFilteredItems(UUID userId, Boolean auctioned) {
    List<ItemEntity> items = itemRepository.findItemsByUserAndAuctionStatus(userId, auctioned);

    return items.stream()
        .map(ItemMapper::toItemResponseDTO)
        .collect(Collectors.toList());
  }

  @Override
  public ItemResponseDTO getItemById(final UUID itemId) {
    return ItemMapper.toItemResponseDTO(this.findItemById(itemId));
  }

  @Override
  @Transactional
  public ItemResponseDTO updateItem(final UUID itemId, final UpdateItemDTO updateItemDTO, final MultipartFile[] files) {
    final ItemEntity item = this.findItemById(itemId);
    if (updateItemDTO.getCategoryId() != null) {
      CategoryEntity category = this.categoryService.findCategoryById(updateItemDTO.getCategoryId());
      item.setCategory(category);
    }
    if (files != null && files.length > 0) {
      List<ItemImageEntity> imageEntities = new ArrayList<>(item.getImages());
      for (ItemImageEntity imageEntity : imageEntities) {
        this.itemImageService.deleteItemImage(imageEntity);
      }
      item.setImages(new ArrayList<>());
      this.itemImageService.createItemImage(item, files);
    }
    ItemMapper.updateEntityFromDTO(item, updateItemDTO);
    this.itemRepository.save(item);
    return ItemMapper.toItemResponseDTO(item);
  }

  @Override
  public void deleteItem(final UUID itemId) {
    final ItemEntity item = this.findItemById(itemId);
    item.setDeletedAt(LocalDateTime.now());
    this.itemRepository.save(item);
  }

  @Override
  public ItemEntity findItemById(final UUID itemId) {
    return this.itemRepository.findByIdAndDeletedAtIsNull(itemId)
        .orElseThrow(() -> new ResourceNotFoundException("Item not found for ID: " + itemId));
  }

}
