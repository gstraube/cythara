package com.github.cythara;

import java.util.Arrays;

class PitchComparator {

    static PitchDifference retrieveNote(float pitch) {
        Tuning tuning = MainActivity.getCurrentTuning();

        Note[] notes = tuning.getNotes();
        Arrays.sort(notes, (o1, o2) -> Float.compare(o1.getFrequency(), o2.getFrequency()));

        double minCentDifference = Float.POSITIVE_INFINITY;
        Note closest = notes[0];
        for (Note note : notes) {
            double centDifference = 1200d * log2(pitch / note.getFrequency());

            if (Math.abs(centDifference) < Math.abs(minCentDifference)) {
                minCentDifference = centDifference;
                closest = note;
            }
        }

        return new PitchDifference(closest, minCentDifference);
    }

    private static double log2(float number) {
        return Math.log(number) / Math.log(2);
    }
}
