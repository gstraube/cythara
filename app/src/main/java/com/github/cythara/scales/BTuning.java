package com.github.cythara.scales;

import com.github.cythara.Note;
import com.github.cythara.NoteName;
import com.github.cythara.Tuning;

import static com.github.cythara.NoteName.*;

public class BTuning implements Tuning {

    @Override
    public Note[] getNotes() {
        return Pitch.values();
    }

    @Override
    public Note findNote(String name) {
        return Pitch.valueOf(name);
    }

    private enum Pitch implements Note {
        C_MINUS_1_SHARP(C,-1,"#"),
        D_MINUS_1_SHARP(D,-1,"#"),
        E_MINUS_1(E,-1),
        F_MINUS_1_SHARP(F,-1,"#"),
        G_MINUS_1_SHARP(G,-1,"#"),
        A_MINUS_1_SHARP(A,-1,"#"),
        B_MINUS_1(B,-1),
        C0_SHARP(C,0,"#"),
        D0_SHARP(D,0,"#"),
        E0(E,0),
        F0_SHARP(F,0,"#"),
        G0_SHARP(G,0,"#"),
        A0_SHARP(A,0,"#"),
        B0(B,0),
        C1_SHARP(C,1,"#"),
        D1_SHARP(D,1,"#"),
        E1(E,1),
        F1_SHARP(F,1,"#"),
        G1_SHARP(G,1,"#"),
        A1_SHARP(A,1,"#"),
        B1(B,1),
        C2_SHARP(C,2,"#"),
        D2_SHARP(D,2,"#"),
        E2(E,2),
        F2_SHARP(F,2,"#"),
        G2_SHARP(G,2,"#"),
        A2_SHARP(A,2,"#"),
        B2(B,2),
        C3_SHARP(C,3,"#"),
        D3_SHARP(D,3,"#"),
        E3(E,3),
        F3_SHARP(F,3,"#"),
        G3_SHARP(G,3,"#"),
        A3_SHARP(A,3,"#"),
        B3(B,3),
        C4_SHARP(C,4,"#"),
        D4_SHARP(D,4,"#"),
        E4(E,4),
        F4_SHARP(F,4,"#"),
        G4_SHARP(G,4,"#"),
        A4_SHARP(A,4,"#"),
        B4(B,4),
        C5_SHARP(C,5,"#"),
        D5_SHARP(D,5,"#"),
        E5(E,5),
        F5_SHARP(F,5,"#"),
        G5_SHARP(G,5,"#"),
        A5_SHARP(A,5,"#"),
        B5(B,5),
        C6_SHARP(C,6,"#"),
        D6_SHARP(D,6,"#"),
        E6(E,6),
        F6_SHARP(F,6,"#"),
        G6_SHARP(G,6,"#"),
        A6_SHARP(A,6,"#"),
        B6(B,6),
        C7_SHARP(C,7,"#"),
        D7_SHARP(D,7,"#"),
        E7(E,7),
        F7_SHARP(F,7,"#"),
        G7_SHARP(G,7,"#"),
        A7_SHARP(A,7,"#"),
        B7(B,7),
        C8_SHARP(C,8,"#"),
        D8_SHARP(D,8,"#"),
        E8(E,8),
        F8_SHARP(F,8,"#"),
        G8_SHARP(G,8,"#"),
        A8_SHARP(A,8,"#"),
        B8(B,8),
        C9_SHARP(C,9,"#"),
        D9_SHARP(D,9,"#"),
        E9(E,9),
        F9_SHARP(F,9,"#"),
        G9_SHARP(G,9,"#"),
        A9_SHARP(A,9,"#"),
        B9(B,9);
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
