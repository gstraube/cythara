package com.github.cythara.tuning;

import com.github.cythara.NoteName;
import com.github.cythara.Tuning;

import static com.github.cythara.NoteName.*;

public class DropCSharpGuitarTuning implements Tuning {

    private enum Pitch implements com.github.cythara.Note {

        C2_SHARP(C, 2, "#", 69.30f),
        A2(A, 2, 110f),
        D3(D, 3, 146.832f),
        G3(G, 3, 195.998f),
        B3(B, 3, 246.942f),
        E4(E, 4, 329.628f);

        private NoteName name;
        private final String sign;
        private final int octave;
        private final float frequency;

        Pitch(NoteName name, int octave, String sign, float frequency) {
            this.name = name;
            this.octave = octave;
            this.sign = sign;
            this.frequency = frequency;
        }

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
