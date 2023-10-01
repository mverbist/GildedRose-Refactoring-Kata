package com.gildedrose;

import org.junit.jupiter.api.Test;

import static com.gildedrose.GildedRose.sulfurasUpdater;
import static org.assertj.core.api.Assertions.assertThat;

public class SulfurasUpdaterTest {
    int sellIn = 2;
    int quality = 4;
    @Test
    public void updateShouldLeaveTheItemUnchanged() {
        Item item = new Item("some item", sellIn, quality);
        assertThat(sulfurasUpdater.update(item).sellIn).isEqualTo(sellIn);
        assertThat(sulfurasUpdater.update(item).quality).isEqualTo(quality);
    }

}
