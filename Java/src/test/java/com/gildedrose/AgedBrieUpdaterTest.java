package com.gildedrose;

import org.junit.jupiter.api.Test;

import static com.gildedrose.GildedRose.*;
import static org.assertj.core.api.Assertions.*;
public class AgedBrieUpdaterTest {

    int sellIn = 2;
    int quality = 4;
    @Test
    public void updateShouldReduceTheSellInBy1() {
        Item item = new Item("some item", sellIn, quality);
        assertThat(agedBrieUpdater.update(item).sellIn).isEqualTo(sellIn - 1);
    }

    @Test
    public void updateShouldIncreaseTheQualityBy1() {
        Item item = new Item("some item", sellIn, quality);
        assertThat(agedBrieUpdater.update(item).quality).isEqualTo(quality + 1);
    }
}
