package com.github.cythara.tuning;

import com.github.cythara.Note;
import com.github.cythara.Tuning;

public class UkuleleTuning implements Tuning {

    private enum Pitch implements Note {

        G4("G", "4", 391.995f),
        C4("C", "4", 261.626f),
        E4("E", "4", 329.628f),
        A4("A", "4", 440f);

        private String name;
        private final String sign;
        private final String octave;
        private final float frequency;

        Pitch(String name, String octave, float frequency) {
            this.name = name;
            this.octave = octave;
            this.sign = "";
            this.frequency = frequency;
        }

        public String getName() {
            return name;
        }

        public float getFrequency() {
            return frequency;
        }

        @Override
        public String getOctave() {
            return octave;
        }

        @Override
        public String getSign() {
            return sign;
        }
    }

    @Override
    public Note[] getNotes() {
        return Pitch.values();
    }

    @Override
    public Note findNote(String name) {
        return Pitch.valueOf(name);
    }
}
