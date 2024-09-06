package gildedrose

type Item struct {
	Name            string
	SellIn, Quality int
}

const (
	AgedBrie      = "Aged Brie"
	BackStagePass = "Backstage passes to a TAFKAL80ETC concert"
	Sulfuras      = "Sulfuras, Hand of Ragnaros"
)

func ProcessDailyItems(items []*Item) {
	for _, item := range items {
		updateItemQuality(item)
		updateExperation(item)
		if item.SellIn < 0 {
			processExpiredItem(item)
		}
	}

}

func updateItemQuality(item *Item) {

	if item.Name == "Aged Brie" {
		increaseQuality(item)
	} else if item.Name == BackStagePass {
		increaseQuality(item)
		if item.SellIn < 11 {
			increaseQuality(item)
		}
		if item.SellIn < 6 {
			increaseQuality(item)
		}

	} else if item.Name == Sulfuras {
		return
	} else {
		decreaseQuality(item)
	}
}

func decreaseQuality(item *Item) {
	if item.Quality > 0 {
		item.Quality = item.Quality - 1
	}
}

func updateExperation(item *Item) {
	if item.Name == Sulfuras {
		return
	} else {
		item.SellIn = item.SellIn - 1
	}
}

func processExpiredItem(item *Item) {
	if item.Name == AgedBrie {
		increaseQuality(item)
	} else if item.Name == BackStagePass {
		item.Quality = 0
	} else if item.Name == Sulfuras {
		return
	} else {
		decreaseQuality(item)
	}
}

func increaseQuality(item *Item) {
	if item.Quality < 50 {
		item.Quality = item.Quality + 1
	}
}
