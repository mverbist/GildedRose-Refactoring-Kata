package com.gildedrose;

import net.jqwik.api.*;
import net.jqwik.api.arbitraries.IntegerArbitrary;

class GildedRoseTest {

    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String AGED_BRIE = "Aged Brie";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String CONJURED_MANA_CAKE = "Conjured Mana Cake";
    public static final Integer MAX_QUALITY = 50;

    @Property
    boolean theQualityOfAnItemIsNeverNegative(@ForAll("sortOfItems") Item anItem) {
        GildedRose gildedRose = new GildedRose(new Item[]{anItem});
        gildedRose.updateQuality();
        return gildedRose.items[0].quality >= 0;
    }

    @Property
    boolean theQualityOfAnItemIsNeverMoreThanMAX_QUALITY(@ForAll("sortOfItems") Item anItem) {
        GildedRose gildedRose = new GildedRose(new Item[]{anItem});
        gildedRose.updateQuality();
        return gildedRose.items[0].quality <= MAX_QUALITY;
    }

    @Property
    boolean theSellInDateOfItemsDecreases(@ForAll("sortOfItems") Item anItem) {
        GildedRose gildedRose = new GildedRose(new Item[]{anItem});
        int sellInBeforeUpdate = anItem.sellIn;
        gildedRose.updateQuality();
        int sellInAfterUpdate = gildedRose.items[0].sellIn;
        return sellInAfterUpdate <= sellInBeforeUpdate;
    }

    @Property
    boolean theQualityOfItemsDecreases(@ForAll("sortOfItems") Item anItem) {
        GildedRose gildedRose = new GildedRose(new Item[]{anItem});
        int qualityBeforeUpdate = anItem.quality;
        gildedRose.updateQuality();
        int qualityAfterUpdate = gildedRose.items[0].quality;
        return qualityAfterUpdate <= qualityBeforeUpdate;
    }

    @Property
    boolean theQualityOfAnAgedBrieIncreases(@ForAll("agedBries") Item anAgedBrie) {
        GildedRose gildedRose = new GildedRose(new Item[]{anAgedBrie});
        int qualityBeforeUpdate = anAgedBrie.quality;
        gildedRose.updateQuality();
        int qualityAfterUpdate = gildedRose.items[0].quality;
        return qualityAfterUpdate >= qualityBeforeUpdate;
    }

    @Property
    boolean theQualityOfABackstagePassIncreasesBeforeTheSellDate(@ForAll("backstagePassesBeforeSellDate") Item aBackstagePass) {
        GildedRose gildedRose = new GildedRose(new Item[]{aBackstagePass});
        int qualityBeforeUpdate = aBackstagePass.quality;
        gildedRose.updateQuality();
        int qualityAfterUpdate = gildedRose.items[0].quality;
        return qualityAfterUpdate == Math.min(qualityBeforeUpdate + 1, MAX_QUALITY);
    }

    @Property
    boolean theQualityOfABackstagePassIncreasesBy2WhenLessThan11DaysBeforeTheSellDate(@ForAll("backstagePassesLessThan11DaysBeforeSellDate") Item aBackstagePass) {
        GildedRose gildedRose = new GildedRose(new Item[]{aBackstagePass});
        int qualityBeforeUpdate = aBackstagePass.quality;
        gildedRose.updateQuality();
        int qualityAfterUpdate = gildedRose.items[0].quality;
        return qualityAfterUpdate == Math.min(qualityBeforeUpdate + 2, MAX_QUALITY);
    }

    @Property
    boolean theQualityOfABackstagePassIncreasesBy3WhenLessThan6DaysBeforeTheSellDate(@ForAll("backstagePassesLessThan6DaysBeforeSellDate") Item aBackstagePass) {
        GildedRose gildedRose = new GildedRose(new Item[]{aBackstagePass});
        int qualityBeforeUpdate = aBackstagePass.quality;
        gildedRose.updateQuality();
        int qualityAfterUpdate = gildedRose.items[0].quality;
        return qualityAfterUpdate == Math.min(qualityBeforeUpdate + 3, MAX_QUALITY);
    }

    @Property
    boolean theQualityOfABackstagePassDropsToZeroAfterTheConcert(@ForAll("backstagePassesAfterSellDate") Item aBackstagePass) {
        GildedRose gildedRose = new GildedRose(new Item[]{aBackstagePass});
        gildedRose.updateQuality();
        int qualityAfterUpdate = gildedRose.items[0].quality;
        return qualityAfterUpdate == 0;
    }

    @Property
    boolean theSulfurasNeverHaveToBeSold(@ForAll("sulfuras") Item aSulfura) {
        GildedRose gildedRose = new GildedRose(new Item[]{aSulfura});
        int sellInBeforeUpdate = aSulfura.sellIn;
        gildedRose.updateQuality();
        int sellInAfterUpdate = gildedRose.items[0].sellIn;
        return sellInAfterUpdate >= sellInBeforeUpdate;
    }

    @Property
    boolean theQualityOfSulfurasNeverChanges(@ForAll("sulfuras") Item aSulfura) {
        GildedRose gildedRose = new GildedRose(new Item[]{aSulfura});
        int qualityBeforeUpdate = aSulfura.quality;
        gildedRose.updateQuality();
        int qualityAfterUpdate = gildedRose.items[0].quality;
        return qualityAfterUpdate == qualityBeforeUpdate;
    }

    @Property
    boolean theQualityOfConjuredItemsDecreasesBy2(@ForAll("conjuredItems") Item aConjuredItem) {
        GildedRose gildedRose = new GildedRose(new Item[]{aConjuredItem});
        int qualityBeforeUpdate = aConjuredItem.quality;
        gildedRose.updateQuality();
        int qualityAfterUpdate = gildedRose.items[0].quality;
        return qualityAfterUpdate == Math.max(qualityBeforeUpdate - 2, 0);
    }

    /** We limit this to 100 years, because the existing code contains a bug at Negative overflow for {@code sellIn} */
    @Provide
    IntegerArbitrary allSortOfSellIns() {
        return Arbitraries.integers().greaterOrEqual(-36500);
    }

    @Provide
    Arbitrary<Integer> pastSellIns() {
        return allSortOfSellIns().lessOrEqual(0);
    }

    @Provide
    Arbitrary<Integer> futureSellIns() {
        return Arbitraries.integers().greaterOrEqual(1);
    }

    @Provide
    Arbitrary<Integer> futureSellInsLessThan11Days() {
        return Arbitraries.integers().between(6, 10);
    }

    @Provide
    Arbitrary<Integer> futureSellInsLessThan6Days() {
        return Arbitraries.integers().between(1, 5);
    }

    @Provide
    Arbitrary<Integer> allSortOfQualities() {
        return Arbitraries.integers().between(0, MAX_QUALITY);
    }

    @Provide
    Arbitrary<Item> sortOfItems() {
        Arbitrary<String> names = Arbitraries.strings().withCharRange('a', 'z').ofMinLength(3).ofMaxLength(21);
        return Combinators.combine(names, allSortOfSellIns(), allSortOfQualities())
            .as((name, sellIn, quality) -> new Item(name, sellIn, quality));
    }

    @Provide
    Arbitrary<Item> agedBries() {
        return Combinators.combine(allSortOfSellIns(), allSortOfQualities())
            .as((sellIn, quality) -> new Item(AGED_BRIE, sellIn, quality));
    }

    @Provide
    Arbitrary<Item> backstagePasses() {
        return Combinators.combine(allSortOfSellIns(), allSortOfQualities())
            .as((sellIn, quality) -> new Item(BACKSTAGE_PASSES, sellIn, quality));
    }

    @Provide
    Arbitrary<Item> backstagePassesBeforeSellDate() {
        return Combinators.combine(futureSellIns().filter(sellIn -> sellIn > 10), allSortOfQualities())
            .as((sellIn, quality) -> new Item(BACKSTAGE_PASSES, sellIn, quality));
    }

    @Provide
    Arbitrary<Item> backstagePassesLessThan11DaysBeforeSellDate() {
        return Combinators.combine(futureSellInsLessThan11Days(), allSortOfQualities())
            .as((sellIn, quality) -> new Item(BACKSTAGE_PASSES, sellIn, quality));
    }

    @Provide
    Arbitrary<Item> backstagePassesLessThan6DaysBeforeSellDate() {
        return Combinators.combine(futureSellInsLessThan6Days(), allSortOfQualities())
            .as((sellIn, quality) -> new Item(BACKSTAGE_PASSES, sellIn, quality));
    }

    @Provide
    Arbitrary<Item> backstagePassesAfterSellDate() {
        return Combinators.combine(pastSellIns(), allSortOfQualities())
            .as((sellIn, quality) -> new Item(BACKSTAGE_PASSES, sellIn, quality));
    }

    @Provide
    Arbitrary<Item> sulfuras() {
        return Combinators.combine(allSortOfSellIns(), allSortOfQualities())
            .as((sellIn, quality) -> new Item(SULFURAS, sellIn, quality));
    }

    @Provide
    Arbitrary<Item> conjuredItems() {
        return Combinators.combine(allSortOfSellIns(), allSortOfQualities())
            .as((sellIn, quality) -> new Item(CONJURED_MANA_CAKE, sellIn, quality));
    }

}
