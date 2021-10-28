package com.github.cythara;

import android.util.Log;

import com.github.cythara.scales.ASharpTuning;
import com.github.cythara.scales.ATuning;
import com.github.cythara.scales.BTuning;
import com.github.cythara.scales.CSharpTuning;
import com.github.cythara.scales.CTuning;
import com.github.cythara.scales.DSharpTuning;
import com.github.cythara.scales.DTuning;
import com.github.cythara.scales.ETuning;
import com.github.cythara.scales.FSharpTuning;
import com.github.cythara.scales.FTuning;
import com.github.cythara.scales.GSharpTuning;
import com.github.cythara.scales.GTuning;
import com.github.cythara.tuning.ChromaticTuning;


class ScaleTuningMapper {

    private static final int C_POSITION = 0;
    private static final int C_SHARP_POSITION = 1;
    private static final int D_POSITION = 2;
    private static final int D_SHARP_POSITION = 3;
    private static final int E_POSITION = 4;
    private static final int F_POSITION = 5;
    private static final int F_SHARP_POSITION = 6;
    private static final int G_POSITION = 7;
    private static final int G_SHARP_POSITION = 8;
    private static final int A_POSITION = 9;
    private static final int A_SHARP_POSITION = 10;
    private static final int B_POSITION = 11;

    static Tuning getTuningFromPosition(int position) {
        switch (position) {
            case C_POSITION:
                return new CTuning();
            case C_SHARP_POSITION:
                return new CSharpTuning();
            case D_POSITION:
                return new DTuning();
            case D_SHARP_POSITION:
                return new DSharpTuning();
            case E_POSITION:
                return new ETuning();
            case F_POSITION:
                return new FTuning();
            case F_SHARP_POSITION:
                return new FSharpTuning();
            case G_POSITION:
                return new GTuning();
            case G_SHARP_POSITION:
                return new GSharpTuning();
            case A_POSITION:
                return new ATuning();
            case A_SHARP_POSITION:
                return new ASharpTuning();
            case B_POSITION:
                return new BTuning();
            default:
                Log.w("com.github.cythara", "Unknown position for tuning dropdown list");
                return new ChromaticTuning();
        }
    }
}
