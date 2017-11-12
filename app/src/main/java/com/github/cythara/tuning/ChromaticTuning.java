package com.github.cythara.tuning;

import com.github.cythara.Note;
import com.github.cythara.Tuning;

public class ChromaticTuning implements Tuning {

    private enum Pitch implements Note {

        C_MINUS_1("C-1", 8.176f),
        C_MINUS_1_SHARP("C-1#", 8.662f),
        D_MINUS_1("D-1", 9.177f),
        D_MINUS_1_SHARP("D-1#", 9.723f),
        E_MINUS_1("E-1", 10.301f),
        F_MINUS_1("F-1", 10.914f),
        F_MINUS_1_SHARP("F-1", 11.563f),
        G_MINUS_1("G-1", 12.250f),
        G_MINUS_1_SHARP("G-1#", 12.979f),
        A_MINUS_1("A-1", 13.750f),
        A_MINUS_1_SHARP("A-1#", 14.568f),
        B_MINUS_1("B-1", 15.434f),

        C0("C0", 16.352f),
        C0_SHARP("C0#", 17.324f),
        D0("D0", 18.354f),
        D0_SHARP("D0#", 19.445f),
        E0("E0", 20.602f),
        F0("F0", 21.827f),
        F0_SHARP("F0", 23.125f),
        G0("G0", 24.500f),
        G0_SHARP("G0#", 25.957f),
        A0("A0", 27.500f),
        A0_SHARP("A0#", 29.135f),
        B0("B0", 30.868f),

        C1("C1", 32.703f),
        C1_SHARP("C1#", 34.648f),
        D1("D1", 36.708f),
        D1_SHARP("D1#", 38.891f),
        E1("E1", 41.203f),
        F1("F1", 43.654f),
        F1_SHARP("F1#", 46.249f),
        G1("G1", 48.999f),
        G1_SHARP("G1#", 51.913f),
        A1("A1", 55.000f),
        A1_SHARP("A1#", 58.270f),
        B1("B1", 61.735f),

        C2("C2", 65.406f),
        C2_SHARP("C2#", 69.296f),
        D2("D2", 73.416f),
        D2_SHARP("D2#", 77.782f),
        E2("E2", 82.407f),
        F2("F2", 87.307f),
        F2_SHARP("F2#", 92.499f),
        G2("G2", 97.999f),
        G2_SHARP("G2#", 103.83f),
        A2("A2", 110.00f),
        A2_SHARP("A2#", 116.54f),
        B2("B2", 123.47f),

        C3("C3", 130.81f),
        C3_SHARP("C3#", 138.59f),
        D3("D3", 146.83f),
        D3_SHARP("D3#", 155.56f),
        E3("E3", 164.81f),
        F3("F3", 174.61f),
        F3_SHARP("F3#", 185.00f),
        G3("G3", 196.00f),
        G3_SHARP("G3#", 207.65f),
        A3("A3", 220.00f),
        A3_SHARP("A3#", 233.08f),
        B3("B3", 246.94f),

        C4("C4", 261.63f),
        C4_SHARP("C4#", 277.18f),
        D4("D4", 293.66f),
        D4_SHARP("D4#", 311.13f),
        E4("E4", 329.63f),
        F4("F4", 349.23f),
        F4_SHARP("F4#", 369.99f),
        G4("G4", 392.00f),
        G4_SHARP("G4#", 415.30f),
        A4("A4", 440.00f),
        A4_SHARP("A4#", 466.16f),
        B4("B4", 493.88f),

        C5("C5", 523.25f),
        C5_SHARP("C5#", 554.37f),
        D5("D5", 587.33f),
        D5_SHARP("D5#", 622.25f),
        E5("E5", 659.26f),
        F5("F5", 698.46f),
        F5_SHARP("F5#", 739.99f),
        G5("G5", 783.99f),
        G5_SHARP("G5#", 830.61f),
        A5("A5", 880.00f),
        A5_SHARP("A5#", 932.33f),
        B5("B5", 987.77f),

        C6("C6", 1046.5f),
        C6_SHARP("C6#", 1108.7f),
        D6("D6", 1174.7f),
        D6_SHARP("D6#", 1244.5f),
        E6("E6", 1318.5f),
        F6("F6", 1396.9f),
        F6_SHARP("F6#", 1480.0f),
        G6("G6", 1568.0f),
        G6_SHARP("G6#", 1661.2f),
        A6("A6", 1760.0f),
        A6_SHARP("A6#", 1864.7f),
        B6("B6", 1975.5f),

        C7("C7", 2093.0f),
        C7_SHARP("C7#", 2217.5f),
        D7("D7", 2349.3f),
        D7_SHARP("D7#", 2489.0f),
        E7("E7", 2637.0f),
        F7("F7", 2793.8f),
        F7_SHARP("F7#", 2960.0f),
        G7("G7", 3136.0f),
        G7_SHARP("G7#", 3322.4f),
        A7("A7", 3520.0f),
        A7_SHARP("A7#", 3729.3f),
        B7("B7", 3951.1f),

        C8("C8", 4186.0f),
        C8_SHARP("C8#", 4434.9f),
        D8("D8", 4698.6f),
        D8_SHARP("D8#", 4978.0f),
        E8("E8", 5274.0f),
        F8("F8", 5587.7f),
        F8_SHARP("F8#", 5919.9f),
        G8("G8", 6271.9f),
        G8_SHARP("G8#", 6644.9f),
        A8("A8", 7040.0f),
        A8_SHARP("A8#", 7458.6f),
        B8("B8", 7902.1f),

        C9("C9", 8372.0f),
        C9_SHARP("C9#", 8869.8f),
        D9("D9", 9397.3f),
        D9_SHARP("D9#", 9956.1f),
        E9("E9", 10548.1f),
        F9("F9", 11175.3f),
        F9_SHARP("F9#", 11839.8f),
        G9("G9", 12543.9f);

        private String name;
        private final float frequency;

        Pitch(String name, float frequency) {
            this.name = name;
            this.frequency = frequency;
        }

        public String getName() {
            return name;
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
