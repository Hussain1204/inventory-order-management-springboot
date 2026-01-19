package com.Hussain.dreamshops.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddToCartRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;
}
