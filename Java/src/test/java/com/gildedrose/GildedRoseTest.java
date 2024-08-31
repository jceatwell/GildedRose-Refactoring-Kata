package com.gildedrose;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class GildedRoseTest {

    // Standard Items
    private final String STANDARD_ITEM = "Standard Item";
    private final String DEXTERITY_VEST = "+5 Dexterity Vest";

    // Conjured Items
    private final String ELIXIR_MONGOOSE = "Elixir of the Mongoose";

    // Special Items
    private final String AGED_BRIE = "Aged Brie";
    private final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";

    // Legendary Items
    private final String SULFURAS = "Sulfuras, Hand of Ragnaros";

    private final int MAX_QUALITY = 50;

    private static GildedRose createGildedRoseWithSingleItem(String itemName, int startingSellIn, int startingQuality) {
        final Item standardItem = new Item(itemName, startingSellIn, startingQuality);
        return new GildedRose(new Item[] {standardItem});
    }

    @DisplayName("Given Item show relevant name")
    @ParameterizedTest(name="Expected \"{0}\"")
    @CsvSource({
        STANDARD_ITEM +", 10, 20, '" + STANDARD_ITEM + ", 10, 20'",
        DEXTERITY_VEST + ", 10, 20, '" + DEXTERITY_VEST + ", 10, 20'",
    })
    void givenAnyItem_whenCheckingDescription_thenShowNameSellInAndQuality(String itemName, int startingSellIn, int startingQuality, String expectToString) {
        final Item item = new Item(itemName, startingSellIn, startingQuality);
        assertThat(item).hasToString(expectToString);
    }

    @DisplayName("Given Standard Item, when update occurs, sellIn and Quality reduce by 1")
    @ParameterizedTest(name=" Validate for Item \"{0}\"")
    @CsvSource({
        STANDARD_ITEM +", 1, 1",
        DEXTERITY_VEST + ", 10, 20",
        ELIXIR_MONGOOSE + ", 5, 7"
    })
    void givenStandardItem_whenDailyUpdate_thenQualityDegradesByOne(String itemName, int startingSellIn, int startingQuality) {
        GildedRose gildedRose = createGildedRoseWithSingleItem(itemName, startingSellIn, startingQuality);

        gildedRose.updateQuality();

        assertThat(gildedRose.items[0].sellIn).isEqualTo(startingSellIn - 1);
        assertThat(gildedRose.items[0].quality).isEqualTo(startingQuality - 1);
    }

    @DisplayName("Given Standard Item, when SellIn Less than 0, Quality reduce by 2")
    @ParameterizedTest(name=" Validate for Item \"{0}\"")
    @CsvSource({
        STANDARD_ITEM +", -1, 10",
        DEXTERITY_VEST + ", -1, 20",
        ELIXIR_MONGOOSE + ", -1, 7"
    })
    void givenStandardItem_whenSellByDatePassed_thenQualityDegradesByTwo(String itemName, int startingSellIn, int startingQuality) {
        GildedRose gildedRose = createGildedRoseWithSingleItem(itemName, startingSellIn, startingQuality);

        gildedRose.updateQuality();

        assertThat(gildedRose.items[0].sellIn).isEqualTo(startingSellIn - 1);
        assertThat(gildedRose.items[0].quality).isEqualTo(startingQuality - 2);
    }

    @DisplayName("Given Degradable, when degraded, Quality never negative")
    @ParameterizedTest(name=" Validate for Item \"{0}\"")
    @CsvSource({
        STANDARD_ITEM +", -1, 3",
        DEXTERITY_VEST + ", -1, 3",
        ELIXIR_MONGOOSE + ", -1, 3"
    })
    void givenDegradableItem_whenDailyUpdate_thenQualityNeverNegative(String itemName, int startingSellIn, int startingQuality) {
        GildedRose gildedRose = createGildedRoseWithSingleItem(itemName, startingSellIn, startingQuality);
        IntStream.range(0, 5)
            .forEach( i -> {
                gildedRose.updateQuality();
                assertThat(gildedRose.items[0].quality).isNotNegative();
            });
    }

    @DisplayName("Given Aged Brie, when update occurs, Quality never reduce")
    @ParameterizedTest(name="Given [{0},{1}] expect [{2},{3}]")
    @CsvSource({
        "10, 20, 9, 21",
        "-1, 20, -2, 22",
    })
    void givenAgedBrieItem_whenDailyUpdate_thenQualityIncreases(int startSellIn, int startQuality,
                                                                int expectedSellIn, int expectedQuality) {
        GildedRose gildedRose = createGildedRoseWithSingleItem(AGED_BRIE, startSellIn, startQuality);

        gildedRose.updateQuality();

        assertThat(gildedRose.items[0].sellIn).isEqualTo(expectedSellIn);
        assertThat(gildedRose.items[0].quality).isEqualTo(expectedQuality);
    }

    @Test
    void givenMultipleItem_whenDailyUpdate_eachItemWillDegrade() {
        Item firstItem = new Item(STANDARD_ITEM, 5, 4);
        Item secondItem = new Item(DEXTERITY_VEST, 3, 2);
        GildedRose subject = new GildedRose(new Item[] { firstItem, secondItem });

        subject.updateQuality();

        assertThat(firstItem.sellIn).isEqualTo(4);
        assertThat(firstItem.quality).isEqualTo(3);
        assertThat(secondItem.sellIn).isEqualTo(2);
        assertThat(secondItem.quality).isEqualTo(1);
    }

    @DisplayName("Given Special Item, when update occurs, sellIn and Quality quality cannot increase past 50")
    @ParameterizedTest(name="Validate for \"{0}\" for SellIn {1}")
    @CsvSource({
        AGED_BRIE +", 10, 49",
        AGED_BRIE +", -1, 49",
        BACKSTAGE_PASS +", 5, 48",
        BACKSTAGE_PASS +", 10, 49",
        BACKSTAGE_PASS +", 10, 50",
    })
    void givenItemWhichIncreasesInQuality_whenDailyUpdate_thenQualityCannotIncreasePast50(String temName, int startingSellIn, int startingQuality) {
        GildedRose gildedRose = createGildedRoseWithSingleItem(AGED_BRIE, startingSellIn, startingQuality);

        gildedRose.updateQuality();
        gildedRose.updateQuality();

        assertThat(gildedRose.items[0].quality).isEqualTo(MAX_QUALITY);
    }

    @DisplayName("Given Sulfuras, when update occurs, sellIn and Quality never reduce")
    @ParameterizedTest(name="Test for SellIn {0} and Quantity {1}")
    @CsvSource({
        "10, 20, 10, 20",
        "-1, 80, -1, 80",
        "-1, 0, -1, 0",
    })
    void givenSulfurasItem_whenDailyUpdate_ItemNeverHasToBeSoldOrQualityNeverDecreases(int startSellIn, int startQuality,
                                                                                      int expectedSellIn, int expectedQuality) {
        GildedRose gildedRose = createGildedRoseWithSingleItem(SULFURAS, startSellIn, startQuality);

        gildedRose.updateQuality();

        assertThat(gildedRose.items[0].sellIn).isEqualTo(expectedSellIn);
        assertThat(gildedRose.items[0].quality).isEqualTo(expectedQuality);
    }

    @DisplayName("Backstage pass validation")
    @ParameterizedTest(name="{0}")
    @CsvSource({
        "'Quality Increases as SellIn approaches 0', 15, 20, 14, 21",
        "'Quality +2, when SellIn 10 days or less', 10, 10, 9, 12",
        "'Quality +2, when SellIn 10, Max Quantity', 10, 49, 9, 50",
        "'Quality +3, when SellIn 5 days or less', 5, 10, 4, 13",
        "'Quality +3, when SellIn 5, Max Quantity', 5, 49, 4, 50",
        "'Quality drops to 0 after concert', 0, 10, -1, 0"
    })
    void givenSulfurasItem_whenDailyUpdate_ItemNeverHasToBeSoldOrQualityNeverDecreases(String testContext, int startSellIn, int startQuality,
                                                                                       int expectedSellIn, int expectedQuality) {
        GildedRose gildedRose = createGildedRoseWithSingleItem(BACKSTAGE_PASS, startSellIn, startQuality);

        gildedRose.updateQuality();

        assertThat(gildedRose.items[0].sellIn).isEqualTo(expectedSellIn);
        assertThat(gildedRose.items[0].quality).isEqualTo(expectedQuality);
    }
}
