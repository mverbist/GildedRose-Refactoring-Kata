package com.gildedrose;

import java.util.Arrays;

class GildedRose {
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String AGED_BRIE = "Aged Brie";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String CONJURED = "Conjured Mana Cake";

    /** the maximum value that the {@link Item#quality quality} of an {@code Item} can have */
    public static final Integer MAX_QUALITY = 50;

    public static ItemUpdater limitQuality = (Item inItem) -> {
        inItem.quality = Math.min(Math.max(inItem.quality, 0), 50);
        return inItem;
    };

    /** Regular {@link ItemUpdater}: reduces the {@link Item#sellIn sellIn} date by 1 and the {@link Item#quality quality} by 1 */
    public static ItemUpdater regularUpdater = (Item inItem) -> {
        inItem.sellIn = inItem.sellIn - 1;
        inItem.quality = inItem.quality - 1;
        return inItem;
    };

    /** {@link ItemUpdater} for {@link #AGED_BRIE aged brie}: reduces the {@link Item#sellIn sellIn} date by 1 and increases the {@link Item#quality quality} by 1 */
    public static ItemUpdater agedBrieUpdater = (Item inItem) -> {
        inItem.sellIn = inItem.sellIn - 1;
        inItem.quality = inItem.quality + 1;
        return inItem;
    };

    /** {@link ItemUpdater} for {@link #BACKSTAGE_PASSES backstage passes}.
     * <li>reduces the {@link Item#sellIn sellIn} date by 1</li>
     * <li>if the {@code sellIn} date originally < 1, set the {@link Item#quality quality} to 0</li>
     * <li>if the {@code sellIn} date originally >= 1, increases the {@link Item#quality quality} by 1</li>
     * <li>if the {@code sellIn} date originally >= 1 and <= 10, increases the {@link Item#quality quality} by 2</li>
     * <li>if the {@code sellIn} date originally >= 1 and <= 5 , increases the {@link Item#quality quality} by 3</li>
     */
    public static ItemUpdater backstagePassUpdater = (Item inItem) -> {
        inItem.sellIn = inItem.sellIn - 1;
        if (inItem.sellIn < 0) inItem.quality = 0;
        if (inItem.sellIn >= 0) inItem.quality = inItem.quality + 1;
        if (inItem.sellIn >= 0 && inItem.sellIn < 10) inItem.quality = inItem.quality + 1;
        if (inItem.sellIn >= 0 && inItem.sellIn < 5) inItem.quality = inItem.quality + 1;
        return inItem;
    };

    /** {@link ItemUpdater} for {@link #SULFURAS sulfuras}: never changes the {@link Item#sellIn sellIn} or the {@link Item#quality quality} */
    public static ItemUpdater sulfurasUpdater = (Item inItem) -> {
        return inItem;
    };

    /** {@link ItemUpdater} for {@link #CONJURED conjured} items: reduces the {@link Item#sellIn sellIn} date by 1 and the {@link Item#quality quality} by 2 */
    public static ItemUpdater conjuredUpdater = (Item inItem) -> {
        inItem.sellIn = inItem.sellIn - 1;
        inItem.quality = inItem.quality - 2;
        return inItem;
    };

    private static ItemUpdater updater(Item item) {
        ItemUpdater updater = null;
        switch (item.name) {
            case BACKSTAGE_PASSES:
                updater = backstagePassUpdater;
                break;
            case AGED_BRIE:
                updater = agedBrieUpdater;
                break;
            case SULFURAS:
                updater = sulfurasUpdater;
                break;
            case CONJURED:
                updater = conjuredUpdater;
                break;
            default:
                updater = regularUpdater;
                break;

        }
        return updater;
    }

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
    public void updateItems() {
        Arrays.stream(items).forEach(item -> {
            ItemUpdater updater = updater(item);
            limitQuality.update(updater.update(item));
        });
    }
}
