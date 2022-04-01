package com.mairwunnx.application;

import com.mairwunnx.dto.response.products.ProductResponse;
import javafx.scene.control.ListCell;

public class ProductListCell extends ListCell<ProductResponse> {
    @Override
    protected void updateItem(final ProductResponse item, final boolean empty) {
        super.updateItem(item, empty);

        if (!empty && item != null) {
            final ProductCellController data = new ProductCellController();
            data.setInfo(item);
            setGraphic(data.getParent());
        }
    }
}
