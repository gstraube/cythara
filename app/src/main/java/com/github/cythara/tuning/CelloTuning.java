package com.github.cythara.tuning;

import com.github.cythara.NoteName;
import com.github.cythara.Tuning;

import static com.github.cythara.NoteName.A;
import static com.github.cythara.NoteName.C;
import static com.github.cythara.NoteName.D;
import static com.github.cythara.NoteName.G;

public class CelloTuning implements Tuning {

    @Override
    public com.github.cythara.Note[] getNotes() {
        return Pitch.values();
    }

    @Override
    public com.github.cythara.Note findNote(String name) {
        return Pitch.valueOf(name);
    }

    private enum Pitch implements com.github.cythara.Note {

        C2(C, 2, 65.4f),
        G2(G, 2, 98f),
        D3(D, 3, 146.8f),
        A3(A, 3, 220f);

        private final String sign;
        private final int octave;
        private final float frequency;
        private NoteName name;

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
}
