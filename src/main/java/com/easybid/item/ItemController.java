package com.easybid.item;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.easybid.item.dto.CreateItemDTO;
import com.easybid.item.dto.ItemResponseDTO;
import com.easybid.item.dto.UpdateItemDTO;

@RequestMapping("/api/v1")
@RestController
@Controller
public class ItemController {
  private final ItemService itemService;

  public ItemController(final ItemService itemService) {
    this.itemService = itemService;
  }

  @PostMapping(value = "/items", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  public ResponseEntity<ItemResponseDTO> createItem(
      CreateItemDTO createItemDTO,
      @RequestPart("files") MultipartFile[] files) {
    ItemResponseDTO responseDTO = this.itemService.createItem(createItemDTO,
        files);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

  @GetMapping("/items")
  public ResponseEntity<List<ItemResponseDTO>> getFilteredItems(
      @RequestParam(required = false) UUID userId,
      @RequestParam(required = false) Boolean auctioned) {
    List<ItemResponseDTO> items = itemService.getFilteredItems(userId, auctioned);
    return ResponseEntity.ok(items);
  }

  @GetMapping("/items/{itemId}")
  public ResponseEntity<ItemResponseDTO> getItemById(@PathVariable UUID itemId) {
    ItemResponseDTO item = this.itemService.getItemById(itemId);
    return ResponseEntity.ok(item);
  }

  @PatchMapping(value = "/items/{itemId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  public ResponseEntity<ItemResponseDTO> updateItem(
      @PathVariable("itemId") UUID itemId,
      UpdateItemDTO updateItemDTO,
      @RequestPart(value = "files", required = false) MultipartFile[] files) {
    ItemResponseDTO item = this.itemService.updateItem(itemId, updateItemDTO,
        files);
    return ResponseEntity.ok(item);
  }

  @DeleteMapping("/items/{itemId}")
  public ResponseEntity<String> deleteItem(@PathVariable UUID itemId) {
    this.itemService.deleteItem(itemId);
    return ResponseEntity.ok("Item Deleted Successfully");
  }

}
