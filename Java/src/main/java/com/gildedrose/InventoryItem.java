package com.gildedrose;

public class InventoryItem {

    public static final String BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";
    public static final String AGED_BRIE = "Aged Brie";

    private final Item item;

    public InventoryItem(Item item) {
        this.item = item;
    }

    protected void processExpired(Item item) {
        if (item.name.equals(AGED_BRIE)) {
            increaseQuality(item);
        } else if (item.name.equals(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT)) {
            item.quality = 0;
        } else if (!item.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
            decreaseQuality(item);
        }
    }

    protected boolean isExpired(Item item) {
        return item.sellIn < 0;
    }

    protected void updateQuality(Item item) {
        if (item.name.equals(AGED_BRIE)) {
            increaseQuality(item);
        } else if (item.name.equals(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT)) {
            increaseQuality(item);
            if (item.sellIn < 11) {
                increaseQuality(item);
            }

            if (item.sellIn < 6) {
                increaseQuality(item);
            }
        } else if (!item.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
            decreaseQuality(item);
        }
    }

    public void dailyUpdate(Item item) {
        updateQuality(item);
        updateExpiration(item);
        if (isExpired(item)) {
            processExpired(item);
        }
    }

    protected void decreaseQuality(Item item) {
        if (item.quality > 0) {
            item.quality --;
        }
    }

    protected void increaseQuality(Item item) {
        if (item.quality < 50) {
            item.quality = item.quality + 1;
        }
    }

    protected void updateExpiration(Item item) {
        if (item.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
            return;
        }
        item.sellIn--;
    }
}
