package com.github.cythara;

import java.util.Arrays;
import java.util.Comparator;

class PitchComparator {

    static PitchDifference retrieveNote(float pitch) {
        Tuning tuning = new GuitarTuning();

        Note[] notes = tuning.getNotes();
        Arrays.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                return Float.compare(o1.getFrequency(), o2.getFrequency());
            }
        });

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
