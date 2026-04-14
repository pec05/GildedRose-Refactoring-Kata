package com.gildedrose;

class GildedRose {

    static final String AGED_BRIE = "Aged Brie";
    static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    static final String CONJURED = "Conjured Mana Cake";

    static final int MAX_QUALITY = 50;
    static final int MIN_QUALITY = 0;

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {

        for (Item item : items) {
            updateItem(item);
        }
    }

    private void updateItem(Item item) {
        if (isSulfuras(item)) {
            return;
        }

        decrementSellIn(item);

        if (isAgedBrie(item)) {
            updateAgedBrie(item);
        } else if (isBackstagePass(item)) {
            updateBackstagePass(item);
        } else if (isConjured(item)) {
            updateConjured(item);
        } else {
            updateNormal(item);
        }
    }

    private void updateAgedBrie(Item item) {
        increaseQuality(item, 1);
        if (isPastSellDate(item)) {
            increaseQuality(item, 1);
        }
    }

    private void updateBackstagePass(Item item) {
        if (isPastSellDate(item)) {
            item.quality = MIN_QUALITY;
            return;
        }
        increaseQuality(item, 1);
        if (item.sellIn < 10) increaseQuality(item, 1);
        if (item.sellIn < 5)  increaseQuality(item, 1);
    }

    private void updateConjured(Item item) {
        decreaseQuality(item, 2);
        if (isPastSellDate(item)) {
            decreaseQuality(item, 2);
        }
    }

    private void updateNormal(Item item) {
        decreaseQuality(item, 1);
        if (isPastSellDate(item)) {
            decreaseQuality(item, 1);
        }
    }

    private void decrementSellIn(Item item) {
        item.sellIn--;
    }

    private boolean isPastSellDate(Item item) {
        return item.sellIn < 0;
    }

    private void increaseQuality(Item item, int amount) {
        item.quality = Math.min(item.quality + amount, MAX_QUALITY);
    }

    private void decreaseQuality(Item item, int amount) {
        item.quality = Math.max(item.quality - amount, MIN_QUALITY);
    }

    private boolean isSulfuras(Item item) {
        return item.name.equals(SULFURAS);
    }
    private boolean isAgedBrie(Item item) {
        return item.name.equals(AGED_BRIE);
    }
    private boolean isBackstagePass(Item item) {
        return item.name.equals(BACKSTAGE_PASS);
    }
    private boolean isConjured(Item item) {
        return item.name.startsWith(CONJURED);
    }
}
