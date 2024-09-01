package com.gildedrose;

class GildedRose {

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateInventory() {
        for (Item item : items) {
            InventoryItem inventoryItem = new InventoryItem(item);
            updateItem(item);
        }
    }

    private void updateItem(Item item) {
        updateQuality(item);
        updateExpiration(item);
        if (isExpired(item)) {
            processExpired(item);
        }
    }

    private void updateQuality(Item item) {
        if (item.name.equals(InventoryItem.AGED_BRIE)) {
            increaseQuality(item);
        } else if (item.name.equals(InventoryItem.BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT)) {
            increaseQuality(item);
            if (item.sellIn < 11) {
                increaseQuality(item);
            }

            if (item.sellIn < 6) {
                increaseQuality(item);
            }
        } else if (!item.name.equals(InventoryItem.SULFURAS_HAND_OF_RAGNAROS)) {
            decreaseQuality(item);
        }
    }

    private void updateExpiration(Item item) {
        if (item.name.equals(InventoryItem.SULFURAS_HAND_OF_RAGNAROS)) {
            return;
        }
        item.sellIn = item.sellIn - 1;
    }

    private boolean isExpired(Item item) {
        return item.sellIn < 0;
    }

    private void processExpired(Item item) {
        if (item.name.equals(InventoryItem.AGED_BRIE)) {
            increaseQuality(item);
        } else if (item.name.equals(InventoryItem.BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT)) {
            item.quality = 0;
        } else if (!item.name.equals(InventoryItem.SULFURAS_HAND_OF_RAGNAROS)) {
            decreaseQuality(item);
        }
    }

    private void increaseQuality(Item item) {
        if (item.quality < 50) {
            item.quality = item.quality + 1;
        }
    }

    private void decreaseQuality(Item item) {
        if (item.quality > 0) {
            item.quality = item.quality - 1;
        }
    }
}
