package com.github.cythara;

import com.github.cythara.tuning.GuitarTuning;

import org.junit.Assert;
import org.junit.Test;

import static com.github.cythara.NoteName.E;
import static com.github.cythara.NoteName.G;
import static com.github.cythara.tuning.GuitarTuning.Pitch;
import static org.hamcrest.Matchers.is;

public class PitchAdjusterTest {

    @Test
    public void pitch_is_unchanged_for_A440() {
        PitchAdjuster pitchAdjuster = new PitchAdjuster();

        for (Pitch pitch : GuitarTuning.Pitch.values()) {
            Assert.assertThat(pitchAdjuster.adjustPitch(pitch.getFrequency()),
                    is(pitch.getFrequency()));
        }
    }

    @Test
    public void pitch_is_adjusted() {
        PitchAdjuster pitchAdjuster = new PitchAdjuster(442f);
        Assert.assertThat(pitchAdjuster.adjustPitch(662.25f), is(659.2534f));

        pitchAdjuster = new PitchAdjuster(434f);
        Assert.assertThat(pitchAdjuster.adjustPitch(172.23f), is(174.61105f));
    }

    @Test
    public void correct_note_is_retrieved_for_adjusted_pitch() {
        PitchAdjuster pitchAdjuster = new PitchAdjuster(446f);
        float adjustedPitch = pitchAdjuster.adjustPitch(198.67f);

        Assert.assertThat(PitchComparator.retrieveNote(adjustedPitch).closest.getName(),
                is(G));
        Assert.assertThat(PitchComparator.retrieveNote(adjustedPitch).closest.getSign(),
                is(""));
        Assert.assertThat(PitchComparator.retrieveNote(adjustedPitch).closest.getOctave(),
                is(3));

        pitchAdjuster = new PitchAdjuster(432f);
        adjustedPitch = pitchAdjuster.adjustPitch(80.91f);


        Assert.assertThat(PitchComparator.retrieveNote(adjustedPitch).closest.getName(),
                is(E));
        Assert.assertThat(PitchComparator.retrieveNote(adjustedPitch).closest.getSign(),
                is(""));
        Assert.assertThat(PitchComparator.retrieveNote(adjustedPitch).closest.getOctave(),
                is(2));
    }
}
