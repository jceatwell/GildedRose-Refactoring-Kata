package com.gildedrose;

public class BackstagePasses extends InventoryItem {
    public static final String NAME = "Backstage passes to a TAFKAL80ETC concert";

    public BackstagePasses(Item item) {
        super(item);
    }

    @Override
    protected void updateQuality() {
        if (item.sellIn < 6) {
            increaseQuality(3);
        } else if (item.sellIn < 11) {
            increaseQuality(2);
        } else {
            increaseQuality(1);
        }
    }

    @Override
    protected void processExpired() {
        item.quality = 0;
    }
}
