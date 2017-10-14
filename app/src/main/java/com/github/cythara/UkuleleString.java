package com.github.cythara;

enum UkuleleString implements Note {

    G(391.995f),
    C(261.626f),
    E(329.628f),
    A(440f);

    private final float frequency;

    UkuleleString(float frequency) {
        this.frequency = frequency;
    }

    public String getName() {
        return this.name();
    }

    public float getFrequency() {
        return frequency;
    }
}
