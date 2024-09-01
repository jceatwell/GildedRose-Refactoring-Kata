package com.gildedrose.inventory;

import com.gildedrose.Item;

public class Sulfuras extends InventoryItem {
    public static final String NAME = "Sulfuras, Hand of Ragnaros";

    public Sulfuras(Item item) {
        super(item);
    }

    @Override
    protected void updateQuality() {
        // Being a legendary item, never decreases in Quality
    }

    @Override
    protected void processExpired() {
        // Being a legendary item, never has to be sold
    }

    @Override
    protected void updateExpiration() {
        // Being a legendary item, never expires
    }
}
