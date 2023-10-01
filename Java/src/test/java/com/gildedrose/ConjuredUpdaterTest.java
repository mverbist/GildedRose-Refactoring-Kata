package com.gildedrose;

import org.junit.jupiter.api.Test;

import static com.gildedrose.GildedRose.conjuredUpdater;
import static org.assertj.core.api.Assertions.assertThat;

public class ConjuredUpdaterTest {

    int sellIn = 2;
    int quality = 4;
    @Test
    public void updateShouldReduceTheSellInBy1() {
        Item item = new Item("some item", sellIn, quality);
        assertThat(conjuredUpdater.update(item).sellIn).isEqualTo(sellIn - 1);
    }

    @Test
    public void updateShouldReduceTheQualityBy2() {
        Item item = new Item("some item", sellIn, quality);
        assertThat(conjuredUpdater.update(item).quality).isEqualTo(quality - 2);
    }

}
