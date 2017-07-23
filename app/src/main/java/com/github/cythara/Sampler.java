package com.github.cythara;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Sampler {

    static PitchDifference calculateAverageDifference(List<PitchDifference> samples) {
        Note mostFrequentNote = extractMostFrequentNote(samples);

        double deviationSum = 0;
        int sameNoteCount = 0;
        for (PitchDifference pitchDifference : samples) {
            if (pitchDifference.closest == mostFrequentNote) {
                deviationSum += pitchDifference.deviation;
                sameNoteCount++;
            }
        }

        if (sameNoteCount > 0) {
            double averageDeviation = deviationSum / sameNoteCount;

            return new PitchDifference(mostFrequentNote, averageDeviation);
        }

        return null;
    }

    @Nullable
    static Note extractMostFrequentNote(List<PitchDifference> samples) {
        Map<Note, Integer> noteFrequencies = new HashMap<>();

        for (PitchDifference pitchDifference : samples) {
            Note closest = pitchDifference.closest;
            if (noteFrequencies.containsKey(closest)) {
                Integer count = noteFrequencies.get(closest);
                noteFrequencies.put(closest, count + 1);
            } else {
                noteFrequencies.put(closest, 1);
            }
        }

        Note mostFrequentNote = null;
        int mostOccurrences = 0;
        for (Note note : noteFrequencies.keySet()) {
            Integer occurrences = noteFrequencies.get(note);
            if (occurrences > mostOccurrences) {
                mostFrequentNote = note;
                mostOccurrences = occurrences;
            }
        }

        return mostFrequentNote;
    }
}
