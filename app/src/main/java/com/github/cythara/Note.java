package com.github.cythara;

enum Note {

    E2("E", 82.41f),
    A2("A", 110f),
    D3("D", 146.83f),
    G3("G", 196f),
    B3("B", 246.94f),
    E4("E", 329.63f);

    private final String guitarString;
    private final float frequency;

    Note(String guitarString, float frequency) {
        this.guitarString = guitarString;
        this.frequency = frequency;
    }

    public String getGuitarString() {
        return guitarString;
    }

    public float getFrequency() {
        return frequency;
    }
}
