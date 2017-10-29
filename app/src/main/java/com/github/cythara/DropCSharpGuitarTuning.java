package com.github.cythara;

public class DropCSharpGuitarTuning implements Tuning {

    private enum Pitch implements com.github.cythara.Note {

        C2_SHARP("C2#", 69.30f),
        A2("A2", 110f),
        D3("D3", 146.832f),
        G3("G3", 195.998f),
        B3("B3", 246.942f),
        E4("E4", 329.628f);

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
    public com.github.cythara.Note[] getNotes() {
        return Pitch.values();
    }

    @Override
    public com.github.cythara.Note findNote(String name) {
        return Pitch.valueOf(name);
    }
}
