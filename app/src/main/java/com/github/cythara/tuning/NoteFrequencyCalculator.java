package com.github.cythara.tuning;

import com.github.cythara.Note;

import java.util.Arrays;
import java.util.List;

public class NoteFrequencyCalculator {

    private static List<String> notes =
            Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B");
    private float referenceFrequency;

    public NoteFrequencyCalculator(float referenceFrequency) {
        this.referenceFrequency = referenceFrequency;
    }

    public double getFrequency(Note note) {
        int semitonesPerOctave = 12;
        int referenceOctave = 4;
        double distance = semitonesPerOctave * (note.getOctave() - referenceOctave);

        distance += notes.indexOf(note.getName() + note.getSign()) - notes.indexOf("A");

        return referenceFrequency * Math.pow(2, distance / 12);
    }
}