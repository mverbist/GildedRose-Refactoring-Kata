package com.gildedrose;

class GildedRose {
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String AGED_BRIE = "Aged Brie";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String CONJURED_MANA_CAKE = "Conjured Mana Cake";

    /** the maximum value that the {@link Item#quality quality} of an {@code Item} can have */
    public static final Integer MAX_QUALITY = 50;

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    /**
     * Updates the {@link Item#quality quality} and the {@link Item#sellIn sellIn} of {@link Item}s.
     *
     * This is called at the ed of each day.
     *
     * It updates the {@link #items} in the stock of the {@link GildedRose} Inn.
     *
     * It follows these general rules for updating the {@code items}:
     * <li>it lowers the value of the {@code item.sellIn} by 1, because that denotes the number of days by which we have to sell the item</li>
     * <li>it lowers the value of the {@code item.quality} by 1, because that denotes how valuable the item is</li>
     *
     * For some types of {@code items}, it follows these special rules:
     * <li>Once the sell by date has passed, Quality degrades twice as fast
     * <li>The Quality of an item is never negative
     * <li>"Aged Brie" actually increases in Quality the older it gets
     * <li>The Quality of an item is never more than {@link #MAX_QUALITY}
     * <li>"Sulfuras", being a legendary item, never has to be sold or decreases in Quality
     * <li>"Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
     * 	Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
     * 	Quality drops to 0 after the concert
     *
     */
    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            if (!items[i].name.equals(AGED_BRIE)
                    && !items[i].name.equals(BACKSTAGE_PASSES)) {
                if (items[i].quality > 0) {
                    if (!items[i].name.equals(SULFURAS)) {
                        items[i].quality = items[i].quality - 1;
                    }
                }
            } else {
                if (items[i].quality < MAX_QUALITY) {
                    items[i].quality = items[i].quality + 1;

                    if (items[i].name.equals(BACKSTAGE_PASSES)) {
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < MAX_QUALITY) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }

                        if (items[i].sellIn < 6) {
                            if (items[i].quality < MAX_QUALITY) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                    }
                }
            }

            if (!items[i].name.equals(SULFURAS)) {
                items[i].sellIn = items[i].sellIn - 1;
            }

            if (items[i].sellIn < 0) {
                if (!items[i].name.equals(AGED_BRIE)) {
                    if (!items[i].name.equals(BACKSTAGE_PASSES)) {
                        if (items[i].quality > 0) {
                            if (!items[i].name.equals(SULFURAS)) {
                                items[i].quality = items[i].quality - 1;
                            }
                        }
                    } else {
                        items[i].quality = items[i].quality - items[i].quality;
                    }
                } else {
                    if (items[i].quality < MAX_QUALITY) {
                        items[i].quality = items[i].quality + 1;
                    }
                }
            }
        }
    }
}
