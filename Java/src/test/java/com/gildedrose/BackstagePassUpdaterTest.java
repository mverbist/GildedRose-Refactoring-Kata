package com.gildedrose;

import org.junit.jupiter.api.Test;

import static com.gildedrose.GildedRose.backstagePassUpdater;
import static org.assertj.core.api.Assertions.assertThat;

public class BackstagePassUpdaterTest {

    int sellIn = 2;
    int quality = 4;
    @Test
    public void updateShouldReduceTheSellInBy1() {
        Item item = new Item("some item", sellIn, quality);
        assertThat(backstagePassUpdater.update(item).sellIn).isEqualTo(sellIn - 1);
    }

    @Test
    public void updateShouldSetQualityToZeroAfterConcert() {
        Item item = new Item("some item", 0, quality);
        assertThat(backstagePassUpdater.update(item).quality).isEqualTo(0);
    }

    @Test
    public void updateShouldIncreaseTheQualityBy1IfSellDateGreaterThan10() {
        Item item = new Item("some item", 11, quality);
        assertThat(backstagePassUpdater.update(item).quality).isEqualTo(quality + 1);
    }

    @Test
    public void updateShouldIncreaseTheQualityBy1IfSellDateBetween5And10() {
        Item item = new Item("some item", 6, quality);
        assertThat(backstagePassUpdater.update(item).quality).isEqualTo(quality + 2);
    }

    @Test
    public void updateShouldIncreaseTheQualityBy3IfSellDateBetween1And5() {
        Item item = new Item("some item", 1, quality);
        assertThat(backstagePassUpdater.update(item).quality).isEqualTo(quality + 3);
    }

}
