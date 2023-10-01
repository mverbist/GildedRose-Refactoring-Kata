package com.gildedrose;

import org.junit.jupiter.api.Test;

import static com.gildedrose.GildedRose.*;
import static org.assertj.core.api.Assertions.*;

public class LimitQualityTest {

    @Test
    public void qualityShouldNeverBeGreaterThan50() {
        Item item = new Item("some item", 3, 51);
        assertThat(limitQuality.update(item).quality).isEqualTo(MAX_QUALITY);
    }

    @Test
    public void qualityLessThan50ShouldRemainUntouched() {
        Item item = new Item("some item", 3, 49);
        assertThat(limitQuality.update(item).quality).isEqualTo(49);
    }

}
