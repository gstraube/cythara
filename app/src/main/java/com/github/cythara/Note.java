package com.github.cythara;

enum Note {

    E6(82.41f),
    A5(110f),
    D4(146.83f),
    G3(196f),
    B2(246.94f),
    E1(329.63f);

    private final float frequency;

    Note(float frequency) {
        this.frequency = frequency;
    }

    public float getFrequency() {
        return frequency;
    }
}
