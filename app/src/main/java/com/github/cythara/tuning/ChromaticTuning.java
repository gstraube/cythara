package com.github.cythara.tuning;

import com.github.cythara.Note;
import com.github.cythara.NoteName;
import com.github.cythara.Tuning;

import static com.github.cythara.NoteName.*;

public class ChromaticTuning implements Tuning {

    @Override
    public Note[] getNotes() {
        return Pitch.values();
    }

    @Override
    public Note findNote(String name) {
        return Pitch.valueOf(name);
    }

    private enum Pitch implements Note {

        C_MINUS_1(C, -1, 8.176f),
        C_MINUS_1_SHARP(C, -1, "#", 8.662f),
        D_MINUS_1(D, -1, 9.177f),
        D_MINUS_1_SHARP(D, -1, "#", 9.723f),
        E_MINUS_1(E, -1, 10.301f),
        F_MINUS_1(F, -1, 10.914f),
        F_MINUS_1_SHARP(F, -1, "#", 11.563f),
        G_MINUS_1(G, -1, 12.250f),
        G_MINUS_1_SHARP(G, -1, "#", 12.979f),
        A_MINUS_1(A, -1, 13.750f),
        A_MINUS_1_SHARP(A, -1, "#", 14.568f),
        B_MINUS_1(B, -1, 15.434f),

        C0(C, 0, 16.352f),
        C0_SHARP(C, 0, "#", 17.324f),
        D0(D, 0, 18.354f),
        D0_SHARP(D, 0, "#", 19.445f),
        E0(E, 0, 20.602f),
        F0(F, 0, 21.827f),
        F0_SHARP(F, 0, "#", 23.125f),
        G0(G, 0, 24.500f),
        G0_SHARP(G, 0, "#", 25.957f),
        A0(A, 0, 27.500f),
        A0_SHARP(A, 0, "#", 29.135f),
        B0(B, 0, 30.868f),

        C1(C, 1, 32.703f),
        C1_SHARP(C, 1, "#", 34.648f),
        D1(D, 1, 36.708f),
        D1_SHARP(D, 1, "#", 38.891f),
        E1(E, 1, 41.203f),
        F1(F, 1, 43.654f),
        F1_SHARP(F, 1, "#", 46.249f),
        G1(G, 1, 48.999f),
        G1_SHARP(G, 1, "#", 51.913f),
        A1(A, 1, 55.000f),
        A1_SHARP(A, 1, "#", 58.270f),
        B1(B, 1, 61.735f),

        C2(C, 2, 65.406f),
        C2_SHARP(C, 2, "#", 69.296f),
        D2(D, 2, 73.416f),
        D2_SHARP(D, 2, "#", 77.782f),
        E2(E, 2, 82.407f),
        F2(F, 2, 87.307f),
        F2_SHARP(F, 2, "#", 92.499f),
        G2(G, 2, 97.999f),
        G2_SHARP(G, 2, "#", 103.83f),
        A2(A, 2, 110.00f),
        A2_SHARP(A, 2, "#", 116.54f),
        B2(B, 2, 123.47f),

        C3(C, 3, 130.81f),
        C3_SHARP(C, 3, "#", 138.59f),
        D3(D, 3, 146.83f),
        D3_SHARP(D, 3, "#", 155.56f),
        E3(E, 3, 164.81f),
        F3(F, 3, 174.61f),
        F3_SHARP(F, 3, "#", 185.00f),
        G3(G, 3, 196.00f),
        G3_SHARP(G, 3, "#", 207.65f),
        A3(A, 3, 220.00f),
        A3_SHARP(A, 3, "#", 233.08f),
        B3(B, 3, 246.94f),

        C4(C, 4, 261.63f),
        C4_SHARP(C, 4, "#", 277.18f),
        D4(D, 4, 293.66f),
        D4_SHARP(D, 4, "#", 311.13f),
        E4(E, 4, 329.63f),
        F4(F, 4, 349.23f),
        F4_SHARP(F, 4, "#", 369.99f),
        G4(G, 4, 392.00f),
        G4_SHARP(G, 4, "#", 415.30f),
        A4(A, 4, 440.00f),
        A4_SHARP(A, 4, "#", 466.16f),
        B4(B, 4, 493.88f),

        C5(C, 5, 523.25f),
        C5_SHARP(C, 5, "#", 554.37f),
        D5(D, 5, 587.33f),
        D5_SHARP(D, 5, "#", 622.25f),
        E5(E, 5, 659.26f),
        F5(F, 5, 698.46f),
        F5_SHARP(F, 5, "#", 739.99f),
        G5(G, 5, 783.99f),
        G5_SHARP(G, 5, "#", 830.61f),
        A5(A, 5, 880.00f),
        A5_SHARP(A, 5, "#", 932.33f),
        B5(B, 5, 987.77f),

        C6(C, 6, 1046.5f),
        C6_SHARP(C, 6, "#", 1108.7f),
        D6(D, 6, 1174.7f),
        D6_SHARP(D, 6, "#", 1244.5f),
        E6(E, 6, 1318.5f),
        F6(F, 6, 1396.9f),
        F6_SHARP(F, 6, "#", 1480.0f),
        G6(G, 6, 1568.0f),
        G6_SHARP(G, 6, "#", 1661.2f),
        A6(A, 6, 1760.0f),
        A6_SHARP(A, 6, "#", 1864.7f),
        B6(B, 6, 1975.5f),

        C7(C, 7, 2093.0f),
        C7_SHARP(C, 7, "#", 2217.5f),
        D7(D, 7, 2349.3f),
        D7_SHARP(D, 7, "#", 2489.0f),
        E7(E, 7, 2637.0f),
        F7(F, 7, 2793.8f),
        F7_SHARP(F, 7, "#", 2960.0f),
        G7(G, 7, 3136.0f),
        G7_SHARP(G, 7, "#", 3322.4f),
        A7(A, 7, 3520.0f),
        A7_SHARP(A, 7, "#", 3729.3f),
        B7(B, 7, 3951.1f),

        C8(C, 8, 4186.0f),
        C8_SHARP(C, 8, "#", 4434.9f),
        D8(D, 8, 4698.6f),
        D8_SHARP(D, 8, "#", 4978.0f),
        E8(E, 8, 5274.0f),
        F8(F, 8, 5587.7f),
        F8_SHARP(F, 8, "#", 5919.9f),
        G8(G, 8, 6271.9f),
        G8_SHARP(G, 8, "#", 6644.9f),
        A8(A, 8, 7040.0f),
        A8_SHARP(A, 8, "#", 7458.6f),
        B8(B, 8, 7902.1f),

        C9(C, 9, 8372.0f),
        C9_SHARP(C, 9, "#", 8869.8f),
        D9(D, 9, 9397.3f),
        D9_SHARP(D, 9, "#", 9956.1f),
        E9(E, 9, 10548.1f),
        F9(F, 9, 11175.3f),
        F9_SHARP(F, 9, "#", 11839.8f),
        G9(G, 9, 12543.9f);

        private final String sign;
        private final int octave;
        private final float frequency;
        private NoteName name;

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
}
