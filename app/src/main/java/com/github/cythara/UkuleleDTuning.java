package com.github.cythara;

public class UkuleleDTuning implements Tuning {

    private enum Pitch implements com.github.cythara.Note {

        A4("A4", 440f),
        D4("D4", 293.665f),
        F3_SHARP("F3#", 369.99f),
        B4("B4", 493.88f);

        private String name;
        private final float frequency;

        Pitch(String name, float frequency) {
            this.name = name;
            this.frequency = frequency;
        }

        public String getName() {
            return name;
        }

        public float getFrequency() {
            return frequency;
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
