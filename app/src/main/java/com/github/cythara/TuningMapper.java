package com.github.cythara;

import android.util.Log;

class TuningMapper {

    private static final int GUITAR_TUNING_POSITION = 0;
    private static final int DROP_D_TUNING_POSITION = 1;
    private static final int DROP_C_TUNING_POSITION = 2;
    private static final int DROP_C_SHARP_TUNING_POSITION = 3;
    private static final int OPEN_G_TUNING = 4;
    private static final int BASS_TUNING_POSITION = 5;
    private static final int UKULELE_TUNING_POSITION = 6;
    private static final int D_TUNING_POSITION = 7;

    static Tuning getTuningFromPosition(int position) {
        switch (position) {
            case GUITAR_TUNING_POSITION:
                return new GuitarTuning();
            case DROP_D_TUNING_POSITION:
                return new DropDGuitarTuning();
            case DROP_C_TUNING_POSITION:
                return new DropCGuitarTuning();
            case DROP_C_SHARP_TUNING_POSITION:
                return new DropCSharpGuitarTuning();
            case OPEN_G_TUNING:
                return new OpenGGuitarTuning();
            case BASS_TUNING_POSITION:
                return new BassTuning();
            case UKULELE_TUNING_POSITION:
                return new UkuleleTuning();
            case D_TUNING_POSITION:
                return new UkuleleDTuning();
            default:
                Log.w("com.github.cythara", "Unknown position for tuning dropdown list");
                return new GuitarTuning();
        }
    }
}
