package com.github.cythara;

class StandardTuning implements Tuning {

    enum Pitch implements com.github.cythara.Note {

        E2(82.41f),
        A2(110f),
        D3(146.83f),
        G3(196f),
        B3(246.94f),
        E4(329.63f);

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
