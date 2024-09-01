package com.gildedrose.inventory;

import com.gildedrose.*;

public class InventoryItem {

    protected final Item item;

    public static InventoryItem create(Item item) {
        if (item.name.equals(AgedBrie.NAME)) {
            return new AgedBrie(item);
        }
        if (item.name.equals(BackstagePasses.NAME)) {
            return new BackstagePasses(item);
        }
        if (item.name.equals(Sulfuras.NAME)) {
            return new Sulfuras(item);
        }
        if (item.name.equals(Conjured.NAME)) {
            return new Conjured(item);
        }
        return new InventoryItem(item);
    }

    public InventoryItem(Item item) {
        this.item = item;
    }

    protected boolean isExpired() {
        return item.sellIn < 0;
    }

    protected void updateQuality() {
        decreaseQuality();
    }

    protected void processExpired() {
        decreaseQuality();
    }

    public void dailyUpdate() {
        updateQuality();
        updateExpiration();
        if (isExpired()) {
            processExpired();
        }
    }

    protected void decreaseQuality() {
        if (item.quality > 0) {
            item.quality--;
        }
    }

    protected void increaseQuality() {
        if (item.quality < 50) {
            item.quality++;
        }
    }

    protected void updateExpiration() {
        item.sellIn--;
    }
}
