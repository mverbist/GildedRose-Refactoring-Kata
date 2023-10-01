package com.gildedrose;

import org.junit.jupiter.api.Test;

import static com.gildedrose.GildedRose.*;
import static org.assertj.core.api.Assertions.*;

public class RegularUpdaterTest {

    int sellIn = 2;
    int quality = 4;
    @Test
    public void updateShouldReduceTheSellInBy1() {
        Item item = new Item("some item", sellIn, quality);
        assertThat(regularUpdater.update(item).sellIn).isEqualTo(sellIn - 1);
    }

    @Test
    public void updateShouldReduceTheQualityBy1() {
        Item item = new Item("some item", sellIn, quality);
        assertThat(regularUpdater.update(item).quality).isEqualTo(quality - 1);
    }

}
