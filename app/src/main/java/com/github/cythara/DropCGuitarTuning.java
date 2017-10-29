package com.github.cythara;

public class DropCGuitarTuning implements Tuning {

    private enum Pitch implements com.github.cythara.Note {

        C2(65.406f),
        G2(97.999f),
        C3(130.813f),
        F3(174.614f),
        A3(220f),
        D4(293.665f);

        private final float frequency;

        Pitch(float frequency) {
            this.frequency = frequency;
        }

        public String getName() {
            return this.name();
        }

        public float getFrequency() {
            return frequency;
        }
    }

    @Override
    public com.github.cythara.Note[] getNotes() {
        return Pitch.values();
    }

    @Override
    public com.github.cythara.Note findNote(String name) {
        return Pitch.valueOf(name);
    }
}
