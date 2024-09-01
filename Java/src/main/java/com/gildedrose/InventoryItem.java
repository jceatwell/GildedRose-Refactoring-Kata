package com.gildedrose;

public class InventoryItem {

    public static final String BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";
    public static final String AGED_BRIE = "Aged Brie";

    private final Item item;

    public InventoryItem(Item item) {
        this.item = item;
    }

    protected void processExpired() {
        if (item.name.equals(AGED_BRIE)) {
            increaseQuality();
        } else if (item.name.equals(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT)) {
            item.quality = 0;
        } else if (!item.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
            decreaseQuality();
        }
    }

    protected boolean isExpired() {
        return item.sellIn < 0;
    }

    protected void updateQuality() {
        if (item.name.equals(AGED_BRIE)) {
            increaseQuality();
        } else if (item.name.equals(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT)) {
            increaseQuality();
            if (item.sellIn < 11) {
                increaseQuality();
            }

            if (item.sellIn < 6) {
                increaseQuality();
            }
        } else if (!item.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
            decreaseQuality();
        }
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
            item.quality --;
        }
    }

    protected void increaseQuality() {
        if (item.quality < 50) {
            item.quality = item.quality + 1;
        }
    }

    protected void updateExpiration() {
        if (item.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
            return;
        }
        item.sellIn--;
    }
}
