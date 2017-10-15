package com.github.cythara;

enum GuitarString implements Note {

    E2(82.41f),
    A2(110f),
    D3(146.83f),
    G3(196f),
    B3(246.94f),
    E4(329.63f);

    private final float frequency;

    GuitarString(float frequency) {
        this.frequency = frequency;
    }

    public String getName() {
        return this.name();
    }

    public float getFrequency() {
        return frequency;
    }
}
