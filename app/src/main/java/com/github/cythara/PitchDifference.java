package com.github.cythara;

class PitchDifference {

    final Note closest;
    final double deviation;

    PitchDifference(Note closest, double deviation) {
        this.closest = closest;
        this.deviation = deviation;
    }
}
