package com.easybid.common;

import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for Base responses for every Response")
public abstract class BaseResponseDto {

  @Schema(description = "UUID", example = "d5bf2bee-a553-47a6-ae42-002d2c65129c")
  private UUID id;

  @Schema(description = "CreatedAt time", example = "2024-12-01T18:20:44.589797")
  private LocalDateTime createdAt;

  @Schema(description = "UpdatedAt time", example = "2024-12-01T18:20:44.589797")
  private LocalDateTime updatedAt;

  @Schema(description = "deletedAt for soft delete", example = "2024-12-01T18:20:44.589797")
  private LocalDateTime deletedAt;

}
