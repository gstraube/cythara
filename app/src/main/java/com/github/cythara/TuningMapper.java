package com.github.cythara;

import android.util.Log;

class TuningMapper {

    private static final int GUITAR_TUNING_POSITION = 0;
    private static final int DROP_D_TUNING_POSITION = 1;
    private static final int UKULELE_TUNING_POSITION = 2;

    static Tuning getTuningFromPosition(int position) {
        switch (position) {
            case GUITAR_TUNING_POSITION:
                return new StandardTuning();
            case DROP_D_TUNING_POSITION:
                return new DropDTuning();
            case UKULELE_TUNING_POSITION:
                return new UkuleleTuning();
            default:
                Log.w("com.github.cythara", "Unknown position for tuning dropdown list");
                return new StandardTuning();
        }
    }
}
