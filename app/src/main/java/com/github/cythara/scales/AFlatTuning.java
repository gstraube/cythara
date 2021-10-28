package com.github.cythara.scales;

import com.github.cythara.Note;
import com.github.cythara.NoteName;
import com.github.cythara.Tuning;

import static com.github.cythara.NoteName.*;

public class AFlatTuning implements Tuning {

    @Override
    public Note[] getNotes() {
        return Pitch.values();
    }

    @Override
    public Note findNote(String name) {
        return Pitch.valueOf(name);
    }

    private enum Pitch implements Note {
        C_MINUS_1(C,-1),
        C_MINUS_1_SHARP(C,-1,"#"),
        D_MINUS_1_SHARP(D,-1,"#"),
        F_MINUS_1(F,-1),
        G_MINUS_1(G,-1),
        G_MINUS_1_SHARP(G,-1,"#"),
        A_MINUS_1_SHARP(A,-1,"#"),
        C0(C,0),
        C0_SHARP(C,0,"#"),
        D0_SHARP(D,0,"#"),
        F0(F,0),
        G0(G,0),
        G0_SHARP(G,0,"#"),
        A0_SHARP(A,0,"#"),
        C1(C,1),
        C1_SHARP(C,1,"#"),
        D1_SHARP(D,1,"#"),
        F1(F,1),
        G1(G,1),
        G1_SHARP(G,1,"#"),
        A1_SHARP(A,1,"#"),
        C2(C,2),
        C2_SHARP(C,2,"#"),
        D2_SHARP(D,2,"#"),
        F2(F,2),
        G2(G,2),
        G2_SHARP(G,2,"#"),
        A2_SHARP(A,2,"#"),
        C3(C,3),
        C3_SHARP(C,3,"#"),
        D3_SHARP(D,3,"#"),
        F3(F,3),
        G3(G,3),
        G3_SHARP(G,3,"#"),
        A3_SHARP(A,3,"#"),
        C4(C,4),
        C4_SHARP(C,4,"#"),
        D4_SHARP(D,4,"#"),
        F4(F,4),
        G4(G,4),
        G4_SHARP(G,4,"#"),
        A4_SHARP(A,4,"#"),
        C5(C,5),
        C5_SHARP(C,5,"#"),
        D5_SHARP(D,5,"#"),
        F5(F,5),
        G5(G,5),
        G5_SHARP(G,5,"#"),
        A5_SHARP(A,5,"#"),
        C6(C,6),
        C6_SHARP(C,6,"#"),
        D6_SHARP(D,6,"#"),
        F6(F,6),
        G6(G,6),
        G6_SHARP(G,6,"#"),
        A6_SHARP(A,6,"#"),
        C7(C,7),
        C7_SHARP(C,7,"#"),
        D7_SHARP(D,7,"#"),
        F7(F,7),
        G7(G,7),
        G7_SHARP(G,7,"#"),
        A7_SHARP(A,7,"#"),
        C8(C,8),
        C8_SHARP(C,8,"#"),
        D8_SHARP(D,8,"#"),
        F8(F,8),
        G8(G,8),
        G8_SHARP(G,8,"#"),
        A8_SHARP(A,8,"#"),
        C9(C,9),
        C9_SHARP(C,9,"#"),
        D9_SHARP(D,9,"#"),
        F9(F,9),
        G9(G,9),
        G9_SHARP(G,9,"#"),
        A9_SHARP(A,9,"#");

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
