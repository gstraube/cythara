package com.github.cythara;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.github.cythara.StandardTuning.Pitch.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;

public class PitchComparatorTest {

    @Test
    public void retrieveNote() throws Exception {
        Map<Float, PitchDifference> expectations = new HashMap<>();
        expectations.put(20f, new PitchDifference(E2, -2451.3832933619105));
        expectations.put(500f, new PitchDifference(E4, 721.296654095616));
        expectations.put(197.67f, new PitchDifference(G3, 14.688333908767358));
        expectations.put(128.415f, new PitchDifference(D3, -231.99964198777823));

        for (Float pitch : expectations.keySet()) {
            PitchDifference actual = PitchComparator.retrieveNote(pitch);
            PitchDifference expected = expectations.get(pitch);

            Assert.assertThat(actual.closest, is(expected.closest));
            Assert.assertThat(actual.deviation, closeTo(expected.deviation, 0.001));
        }
    }
}
