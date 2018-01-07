package com.github.cythara;

public interface Note {

    NoteName getName();
    String getOctave();
    String getSign();
    float getFrequency();
}
