package com.github.cythara;

import com.github.cythara.tuning.NoteFrequencyCalculator;

import java.util.Arrays;

class PitchComparator {

    static PitchDifference retrieveNote(float pitch) {
        Tuning tuning = MainActivity.getCurrentTuning();
        int referencePitch = MainActivity.getReferencePitch();

        Note[] tuningNotes = tuning.getNotes();
        Note[] notes;

        if (MainActivity.isAutoModeEnabled()) {
            notes = tuningNotes;
        } else {
            notes = new Note[]{tuningNotes[MainActivity.getReferencePosition()]};
        }

        NoteFrequencyCalculator noteFrequencyCalculator =
                new NoteFrequencyCalculator(referencePitch);

        Arrays.sort(notes, (o1, o2) ->
                Double.compare(noteFrequencyCalculator.getFrequency(o1),
                        noteFrequencyCalculator.getFrequency(o2)));

        double minCentDifference = Float.POSITIVE_INFINITY;
        Note closest = notes[0];
        Note lastNote=notes[0];
        for (Note note : notes) {
            double frequency = noteFrequencyCalculator.getFrequency(note);
            if (pitch < frequency) {
                double centDifferenceCurrentNote = 1200d * log2(pitch / frequency);

                double frequencyLastNote=noteFrequencyCalculator.getFrequency(lastNote);
                double centDifferenceLastNote= 1200d * log2(pitch / frequencyLastNote);
                if (Math.abs(centDifferenceCurrentNote) < Math.abs(centDifferenceLastNote)) {
                    minCentDifference = centDifferenceCurrentNote;
                    closest = note;
                }else{
                    minCentDifference = centDifferenceLastNote;
                    closest = lastNote;
                }
                break;
            }
            lastNote=note;
        }
        return new PitchDifference(closest, minCentDifference);
    }

    private static double log2(double number) {
        return Math.log(number) / Math.log(2);
    }
}
