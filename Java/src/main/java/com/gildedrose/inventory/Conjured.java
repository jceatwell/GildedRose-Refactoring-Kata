package com.gildedrose.inventory;

import com.gildedrose.Item;

public class Conjured extends InventoryItem {

    public static final String NAME = "Conjured";

    public Conjured(Item item) {
        super(item);
    }

    @Override
    protected void decreaseQuality() {
        item.quality = Math.max(item.quality - 2, 0);
    }
}
