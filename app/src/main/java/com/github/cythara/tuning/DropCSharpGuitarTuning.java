package com.github.cythara.tuning;

import com.github.cythara.Note;
import com.github.cythara.NoteName;
import com.github.cythara.Tuning;

import static com.github.cythara.NoteName.*;

public class DropCSharpGuitarTuning implements Tuning {

    @Override
    public Note[] getNotes() {
        return Pitch.values();
    }

    @Override
    public Note findNote(String name) {
        return Pitch.valueOf(name);
    }

    private enum Pitch implements Note {

        C2_SHARP(C, 2, "#"),
        A2(A, 2),
        D3(D, 3),
        G3(G, 3),
        B3(B, 3),
        E4(E, 4);

        private final String sign;
        private final int octave;
        private NoteName name;

        Pitch(NoteName name, int octave, String sign) {
            this.name = name;
            this.octave = octave;
            this.sign = sign;
        }

        Pitch(NoteName name, int octave) {
            this.name = name;
            this.octave = octave;
            this.sign = "";
        }

        public NoteName getName() {
            return name;
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
