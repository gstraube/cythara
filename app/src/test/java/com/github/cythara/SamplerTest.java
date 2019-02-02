package com.github.cythara;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.github.cythara.Sampler.*;
import static com.github.cythara.tuning.GuitarTuning.Pitch.B3;
import static com.github.cythara.tuning.GuitarTuning.Pitch.E2;
import static com.github.cythara.tuning.GuitarTuning.Pitch.G3;
import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.*;

public class SamplerTest {

    @Test
    public void the_average_difference_is_calculated_correctly() {
        List<PitchDifference> samples = new ArrayList<>();

        samples.add(new PitchDifference(E2, 2.46D));
        samples.add(new PitchDifference(E2, -10.3D));
        samples.add(new PitchDifference(E2, 5.71D));
        samples.add(new PitchDifference(E2, 12.532D));
        samples.add(new PitchDifference(E2, -0.414D));

        PitchDifference pitchDifference = calculateAverageDifference(samples);

        double average = (2.46D - 10.3D + 5.71D + 12.532D - 0.414D) / 5D;

        assertNotNull(pitchDifference);
        assertThat(pitchDifference.closest.getName(), is(E2.getName()));
        assertThat(pitchDifference.deviation, closeTo(average, 0.001));
    }

    @Test
    public void samples_are_filtered_correctly() {
        List<PitchDifference> samples = new ArrayList<>();

        samples.add(new PitchDifference(E2, 2D));
        samples.add(new PitchDifference(E2, 2D));
        samples.add(new PitchDifference(B3, 3D));
        samples.add(new PitchDifference(E2, 2D));
        samples.add(new PitchDifference(G3, 4D));
        samples.add(new PitchDifference(B3, 3D));

        List<PitchDifference> filteredSamples = filterByNote(samples, B3);

        for (PitchDifference sample : filteredSamples) {
            assertThat(sample.closest.getName(), is(B3.getName()));
        }
    }

    @Test
    public void the_most_frequent_note_is_extracted_correctly() {
        List<PitchDifference> samples = new ArrayList<>();

        samples.add(new PitchDifference(E2, 2D));
        samples.add(new PitchDifference(E2, 2D));
        samples.add(new PitchDifference(B3, 3D));
        samples.add(new PitchDifference(E2, 2D));
        samples.add(new PitchDifference(G3, 4D));
        samples.add(new PitchDifference(B3, 3D));

        Note note = extractMostFrequentNote(samples);

        assertThat(note.getName(), is(E2.getName()));
    }

    @Test
    public void if_there_are_notes_with_the_same_number_of_occurrences_one_of_them_is_returned() {
        List<PitchDifference> samples = new ArrayList<>();

        samples.add(new PitchDifference(G3, 2D));
        samples.add(new PitchDifference(E2, 2D));
        samples.add(new PitchDifference(B3, 3D));
        samples.add(new PitchDifference(E2, 2D));
        samples.add(new PitchDifference(B3, 3D));

        Note note = extractMostFrequentNote(samples);

        assertThat(note.getName(), either(is(E2.getName())).or(is(B3.getName())));
    }
}
