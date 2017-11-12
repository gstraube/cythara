package com.github.cythara.tuning;

import com.github.cythara.Tuning;

public class BassTuning implements Tuning {

    private enum Pitch implements com.github.cythara.Note {

        E1(41.204f),
        A1(55f),
        D2(73.416f),
        G2(97.999f);

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
