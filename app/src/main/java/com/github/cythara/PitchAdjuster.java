package com.github.cythara;

class PitchAdjuster {

    private static final float A440 = 440f;
    private final float adjustmentFactor;

    PitchAdjuster() {
        adjustmentFactor = 1f;
    }

    PitchAdjuster(float referencePitch) {
        adjustmentFactor = referencePitch / A440;
    }

    float adjustPitch(float pitch) {
        return pitch / adjustmentFactor;
    }
}
