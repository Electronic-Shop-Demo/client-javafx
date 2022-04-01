package com.mairwunnx.dto.request.products;

import com.mairwunnx.dto.request.Request;

@Request
public enum ProductsSortTypeRequest {
    DEFAULT,
    TITLE_ASC,
    TITLE_DESC,
    PRICE_ASC,
    PRICE_DESC
}
