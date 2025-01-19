package org.kcheremnov.models;

import lombok.Data;

@Data
public class ItemRequest {
    private int number;
    private int categoryId;
    private String description;
    private Long price;
}
