package com.github.cythara.tuning;

import com.github.cythara.Note;
import com.github.cythara.Tuning;

public class UkuleleTuning implements Tuning {

    private enum Pitch implements Note {

        G4(391.995f),
        C4(261.626f),
        E4(329.628f),
        A4(440f);

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
    public Note[] getNotes() {
        return Pitch.values();
    }

    @Override
    public Note findNote(String name) {
        return Pitch.valueOf(name);
    }
}
