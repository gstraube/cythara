package com.github.cythara.tuning;

import com.github.cythara.NoteName;
import com.github.cythara.Tuning;

import static com.github.cythara.NoteName.*;

public class DropCGuitarTuning implements Tuning {

    private enum Pitch implements com.github.cythara.Note {

        C2(C, 2, 65.406f),
        G2(G, 2, 97.999f),
        C3(C, 3, 130.813f),
        F3(F, 3, 174.614f),
        A3(A, 3, 220f),
        D4(D, 4, 293.665f);

        private NoteName name;
        private final String sign;
        private final int octave;
        private final float frequency;

        Pitch(NoteName name, int octave, float frequency) {
            this.name = name;
            this.octave = octave;
            this.sign = "";
            this.frequency = frequency;
        }

        public NoteName getName() {
            return name;
        }

        public float getFrequency() {
            return frequency;
        }

        @Override
        public int getOctave() {
            return octave;
        }

        @Override
        public String getSign() {
            return sign;
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
