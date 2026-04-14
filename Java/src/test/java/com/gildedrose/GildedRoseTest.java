package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    private Item updateOnce(String name, int sellIn, int quality) {
        Item[] items = new Item[]{ new Item(name, sellIn, quality) };
        new GildedRose(items).updateQuality();
        return items[0];
    }
    // items normaux

    @Test
    void normalItem_qualityDecreasesBy1_beforeSellDate() {
        Item item = updateOnce("Elixir of the Mongoose", 5, 10);
        assertEquals(9, item.quality);
        assertEquals(4, item.sellIn);
    }

    @Test
    void normalItem_qualityDecreasesBy2_afterSellDate() {
        Item item = updateOnce("Elixir of the Mongoose", 0, 10);
        assertEquals(8, item.quality);
    }

    @Test
    void normalItem_qualityNeverNegative() {
        Item item = updateOnce("Elixir of the Mongoose", 5, 0);
        assertEquals(0, item.quality);
    }

    @Test
    void normalItem_qualityNeverNegative_pastSellDate() {
        Item item = updateOnce("Elixir of the Mongoose", -1, 0);
        assertEquals(0, item.quality);
    }

    // Aged Brie
    @Test
    void agedBrie_qualityIncreasesBy1_beforeSellDate() {
        Item item = updateOnce("Aged Brie", 5, 10);
        assertEquals(11, item.quality);
    }

    @Test
    void agedBrie_qualityIncreasesBy2_afterSellDate() {
        Item item = updateOnce("Aged Brie", 0, 10);
        assertEquals(12, item.quality);
    }

    @Test
    void agedBrie_qualityNeverExceeds50() {
        Item item = updateOnce("Aged Brie", 5, 50);
        assertEquals(50, item.quality);
    }

    @Test
    void agedBrie_qualityNeverExceeds50_pastSellDate() {
        Item item = updateOnce("Aged Brie", -1, 49);
        assertEquals(50, item.quality);
    }

    // Sulfuras

    @Test
    void sulfuras_qualityNeverChanges() {
        Item item = updateOnce("Sulfuras, Hand of Ragnaros", 5, 80);
        assertEquals(80, item.quality);
    }

    @Test
    void sulfuras_sellInNeverChanges() {
        Item item = updateOnce("Sulfuras, Hand of Ragnaros", 5, 80);
        assertEquals(5, item.sellIn);
    }

    @Test
    void sulfuras_withNegativeSellIn_neverChanges() {
        Item item = updateOnce("Sulfuras, Hand of Ragnaros", -1, 80);
        assertEquals(80, item.quality);
        assertEquals(-1, item.sellIn);
    }

    // Backstage passes

    @Test
    void backstagePass_qualityIncreasesBy1_moreThan10Days() {
        Item item = updateOnce("Backstage passes to a TAFKAL80ETC concert", 15, 20);
        assertEquals(21, item.quality);
    }

    @Test
    void backstagePass_qualityIncreasesBy2_exactly10Days() {
        Item item = updateOnce("Backstage passes to a TAFKAL80ETC concert", 10, 20);
        assertEquals(22, item.quality);
    }

    @Test
    void backstagePass_qualityIncreasesBy2_between6and10Days() {
        Item item = updateOnce("Backstage passes to a TAFKAL80ETC concert", 8, 20);
        assertEquals(22, item.quality);
    }

    @Test
    void backstagePass_qualityIncreasesBy3_exactly5Days() {
        Item item = updateOnce("Backstage passes to a TAFKAL80ETC concert", 5, 20);
        assertEquals(23, item.quality);
    }

    @Test
    void backstagePass_qualityIncreasesBy3_between1and5Days() {
        Item item = updateOnce("Backstage passes to a TAFKAL80ETC concert", 3, 20);
        assertEquals(23, item.quality);
    }

    @Test
    void backstagePass_qualityDropsTo0_afterConcert() {
        Item item = updateOnce("Backstage passes to a TAFKAL80ETC concert", 0, 40);
        assertEquals(0, item.quality);
    }

    @Test
    void backstagePass_qualityNeverExceeds50() {
        Item item = updateOnce("Backstage passes to a TAFKAL80ETC concert", 5, 49);
        assertEquals(50, item.quality);
    }

    // Conjured

    @Test
    void conjured_qualityDecreasesBy2_beforeSellDate() {
        Item item = updateOnce("Conjured Mana Cake", 5, 10);
        assertEquals(8, item.quality);
    }

    @Test
    void conjured_qualityDecreasesBy4_afterSellDate() {
        Item item = updateOnce("Conjured Mana Cake", 0, 10);
        assertEquals(6, item.quality);
    }

    @Test
    void conjured_qualityNeverNegative() {
        Item item = updateOnce("Conjured Mana Cake", 5, 1);
        assertEquals(0, item.quality);
    }

    @Test
    void conjured_qualityNeverNegative_pastSellDate() {
        Item item = updateOnce("Conjured Mana Cake", -1, 3);
        assertEquals(0, item.quality);
    }

    // SellIn décrémente pour tous sauf Sulfuras

    @Test
    void sellIn_decrementsByOne_forNormalItem() {
        Item item = updateOnce("+5 Dexterity Vest", 10, 20);
        assertEquals(9, item.sellIn);
    }

    @Test
    void sellIn_decrementsByOne_forConjured() {
        Item item = updateOnce("Conjured Mana Cake", 3, 6);
        assertEquals(2, item.sellIn);
    }
}
