package com.github.cythara.tuning;

import com.github.cythara.Note;
import com.github.cythara.Tuning;

public class UkuleleDTuning implements Tuning {

    private enum Pitch implements com.github.cythara.Note {

        A4("A", "4", 440f),
        D4("D", "4", 293.665f),
        F3_SHARP("F", "3", "#", 369.99f),
        B4("B", "4", 493.88f);

        private String name;
        private final String sign;
        private final String octave;
        private final float frequency;

        Pitch(String name, String octave, String sign, float frequency) {
            this.name = name;
            this.octave = octave;
            this.sign = sign;
            this.frequency = frequency;
        }

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
