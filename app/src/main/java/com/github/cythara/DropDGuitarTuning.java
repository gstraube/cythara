package com.github.cythara;

class DropDGuitarTuning implements Tuning {

    private enum Pitch implements com.github.cythara.Note {

        D2(73.416f),
        A2(110f),
        D3(146.832f),
        G3(195.998f),
        B3(246.942f),
        E4(329.628f);

        private final float frequency;

        Pitch(float frequency) {
            this.frequency = frequency;
        }

        public String getName() {
            return this.name();
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
