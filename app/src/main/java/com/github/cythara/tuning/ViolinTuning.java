package com.github.cythara.tuning;

import com.github.cythara.NoteName;
import com.github.cythara.Tuning;

import static com.github.cythara.NoteName.*;

public class ViolinTuning implements Tuning {

    private enum Pitch implements com.github.cythara.Note {

        G3(G, "3", 196f),
        D4(D, "4", 293.66f),
        A4(A, "4", 440f),
        E5(E, "5", 659.26f);

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
