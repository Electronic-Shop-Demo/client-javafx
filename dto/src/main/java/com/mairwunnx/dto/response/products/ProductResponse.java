package com.mairwunnx.dto.response.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mairwunnx.dto.response.Response;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Response
@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductResponse(
    @NotNull UUID id,
    @NotNull String title,
    @NotNull String description,
    long count,
    long price,
    @NotNull String image,
    boolean isRemoved
) {
}
