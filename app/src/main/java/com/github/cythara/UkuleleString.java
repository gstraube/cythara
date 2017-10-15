package com.github.cythara;

enum UkuleleString implements Note {

    G4(391.995f),
    C4(261.626f),
    E4(329.628f),
    A4(440f);

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
