package com.github.cythara.tuning;

import java.util.Arrays;
import java.util.List;

public class NoteFrequency {

    private static List<String> notes =
            Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B");

    public static double getFrequency(String note, int octave) {
        int semitonesPerOctave = 12;
        int referenceOctave = 4;
        double distance = semitonesPerOctave * (octave - referenceOctave);

        distance += notes.indexOf(note) - notes.indexOf("A");

        float referenceFrequency = 440;
        return referenceFrequency * Math.pow(2, distance / 12);
    }
}