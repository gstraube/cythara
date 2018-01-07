package com.github.cythara.tuning;

import com.github.cythara.NoteName;
import com.github.cythara.Tuning;

import static com.github.cythara.NoteName.*;

public class BassTuning implements Tuning {

    private enum Pitch implements com.github.cythara.Note {

        E1(E, "1", 41.204f),
        A1(A, "1", 55f),
        D2(D, "2", 73.416f),
        G2(G, "2", 97.999f);

        private NoteName name;
        private final String sign;
        private final String octave;
        private final float frequency;

        Pitch(NoteName name, String octave, float frequency) {
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
        public String getOctave() {
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
