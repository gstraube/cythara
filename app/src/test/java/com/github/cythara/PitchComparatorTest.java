package com.github.cythara;

import com.github.cythara.tuning.GuitarTuning;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

import static com.github.cythara.tuning.GuitarTuning.Pitch.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MainActivity.class)
public class PitchComparatorTest {

    @Test
    public void retrieveNote() {
        PowerMockito.mockStatic(MainActivity.class);
        Mockito.when(MainActivity.getCurrentTuning()).thenReturn(new GuitarTuning());
        Mockito.when(MainActivity.getReferencePitch()).thenReturn(440);
        Mockito.when(MainActivity.isAutoModeEnabled()).thenReturn(true);

        Map<Float, PitchDifference> expectations = new HashMap<>();
        expectations.put(20f, new PitchDifference(E2, -2451.3202694972874));
        expectations.put(332f, new PitchDifference(E4, 12.415661386718076));
        expectations.put(197.67f, new PitchDifference(G3, 14.705999652460953));
        expectations.put(128.415f, new PitchDifference(D3, -232.0232233030192));

        for (Float pitch : expectations.keySet()) {
            PitchDifference actual = PitchComparator.retrieveNote(pitch);
            PitchDifference expected = expectations.get(pitch);

            assertNotNull(expected);
            assertThat(actual.closest, is(expected.closest));
            assertThat(actual.deviation, closeTo(expected.deviation, 0.01));
        }
    }
}
