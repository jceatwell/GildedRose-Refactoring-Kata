package com.gildedrose.inventory;

import com.gildedrose.Item;

public class AgedBrie extends InventoryItem {
    public static final String NAME = "Aged Brie";

    public AgedBrie(Item item) {
        super(item);
    }

    @Override
    protected void processExpired() {
        increaseQuality();
    }

    @Override
    protected void updateQuality() {
        increaseQuality();
    }
}
