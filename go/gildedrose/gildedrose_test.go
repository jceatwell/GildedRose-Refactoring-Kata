package gildedrose

import (
	"testing"
)

func TestQualityDecreasesByOneForNormalItems(t *testing.T) {
	items := []*Item{
		&Item{Name: "Normal Item", SellIn: 10, Quality: 20},
	}

	ProcessDailyItems(items)

	if items[0].Quality != 19 {
		t.Errorf("Expected Quality to be 19, but got %d", items[0].Quality)
	}
}

func Test_QualityDoesNotGoBelowZero(t *testing.T) {
	items := []*Item{
		{Name: "Normal Item", SellIn: 10, Quality: 0},
		{Name: "Aged Brie", SellIn: 2, Quality: 0},
		{Name: "Backstage passes to a TAFKAL80ETC concert", SellIn: 15, Quality: 0},
		{Name: "Sulfuras, Hand of Ragnaros", SellIn: 0, Quality: 80},
		{Name: "Sulfuras, Hand of Ragnaros", SellIn: -1, Quality: 0},
	}

	ProcessDailyItems(items)

	for _, item := range items {
		if item.Quality < 0 {
			t.Errorf("Expected Quality to be >= 0, but got %d for item %s", item.Quality, item.Name)
		}
	}
}

func Test_MyStuct(t *testing.T) {

	t.Run("Test Item Creation", func(t *testing.T) {
		item := Item{
			Name:    "Aged Brie",
			SellIn:  2,
			Quality: 10,
		}

		if item.Name != "Aged Brie" || item.SellIn != 2 || item.Quality != 10 {
			t.Error("Expected Aged Brie, 2, 10, got", item)
		}
	})
}

func Test_QualityDecreasesTwiceAsFastOnceSellByDateHasPassed(t *testing.T) {
	items := []*Item{
		&Item{Name: "Normal Item", SellIn: 0, Quality: 20},
	}

	ProcessDailyItems(items)

	if items[0].Quality != 18 {
		t.Errorf("Expected Quality to be 18, but got %d", items[0].Quality)
	}
}

func Test_QualityDoesNotGoBelowZeroEvenWhenDecreasingTwiceAsFast(t *testing.T) {
	items := []*Item{
		{Name: "Normal Item", SellIn: 0, Quality: 0},
	}

	ProcessDailyItems(items)

	if items[0].Quality != 0 {
		t.Errorf("Expected Quality to be 0, but got %d", items[0].Quality)
	}
}

func Test_AgedBrieQualityIncreasesAsTimeGoesBy(t *testing.T) {
	items := []*Item{
		{Name: "Aged Brie", SellIn: 2, Quality: 0},
		{Name: "Aged Brie", SellIn: -1, Quality: 48},
	}

	ProcessDailyItems(items)

	if items[0].Quality != 1 {
		t.Errorf("Expected Quality to be 1, but got %d", items[0].Quality)
	}

	if items[1].Quality != 50 {
		t.Errorf("Expected Quality to be 1, but got %d", items[0].Quality)
	}

}

func Test_QualityDoesNotGoAboveFifty(t *testing.T) {
	items := []*Item{
		{Name: "Aged Brie", SellIn: 2, Quality: 50},
	}

	ProcessDailyItems(items)

	if items[0].Quality != 50 {
		t.Errorf("Expected Quality to be 50, but got %d", items[0].Quality)
	}
}

func Test_SulfurasQualityAndSellInDoNotChange(t *testing.T) {
	items := []*Item{
		{Name: "Sulfuras, Hand of Ragnaros", SellIn: 0, Quality: 80},
	}

	ProcessDailyItems(items)

	if items[0].Quality != 80 || items[0].SellIn != 0 {
		t.Errorf("Expected Quality to be 80, SellIn to be 0, but got Quality %d, SellIn %d", items[0].Quality, items[0].SellIn)
	}
}

func Test_BackstagePassQualityIncreasesAsTimeGoesBy(t *testing.T) {
	items := []*Item{
		{Name: "Backstage passes to a TAFKAL80ETC concert", SellIn: 15, Quality: 20},
	}

	ProcessDailyItems(items)

	if items[0].Quality != 21 {
		t.Errorf("Expected Quality to be 21, but got %d", items[0].Quality)
	}
}

func Test_BackstagePassQualityIncreasesByTwoWhenSellInIsTenDaysOrLess(t *testing.T) {
	items := []*Item{
		{Name: "Backstage passes to a TAFKAL80ETC concert", SellIn: 10, Quality: 20},
	}

	ProcessDailyItems(items)

	if items[0].Quality != 22 {
		t.Errorf("Expected Quality to be 22, but got %d", items[0].Quality)
	}
}

func Test_BackstagePassQualityIncreasesByThreeWhenSellInIsFiveDaysOrLess(t *testing.T) {
	items := []*Item{
		{Name: "Backstage passes to a TAFKAL80ETC concert", SellIn: 5, Quality: 20},
	}

	ProcessDailyItems(items)

	if items[0].Quality != 23 {
		t.Errorf("Expected Quality to be 23, but got %d", items[0].Quality)
	}
}

func Test_BackstagePassQualityDropsToZeroWhenSellInIsLessThanZero(t *testing.T) {
	items := []*Item{
		{Name: "Backstage passes to a TAFKAL80ETC concert", SellIn: 0, Quality: 20},
	}

	ProcessDailyItems(items)

	if items[0].Quality != 0 {
		t.Errorf("Expected Quality to be 0, but got %d", items[0].Quality)
	}
}
