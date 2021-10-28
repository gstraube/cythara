package com.github.cythara;

import android.util.Log;

import com.github.cythara.scales.BFlatTuning;
import com.github.cythara.scales.ATuning;
import com.github.cythara.scales.BTuning;
import com.github.cythara.scales.DFlatTuning;
import com.github.cythara.scales.CTuning;
import com.github.cythara.scales.EFlatTuning;
import com.github.cythara.scales.DTuning;
import com.github.cythara.scales.ETuning;
import com.github.cythara.scales.GFlatTuning;
import com.github.cythara.scales.FTuning;
import com.github.cythara.scales.AFlatTuning;
import com.github.cythara.scales.GTuning;
import com.github.cythara.tuning.ChromaticTuning;


class ScaleMapper {
    private static final int NONE=-1;
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
    private static final int B_FLAT_POSITION = 10;
    private static final int B_POSITION = 11;

    static Tuning getScaleFromPosition(int position) {
        switch (position) {
            case NONE:
                return new ChromaticTuning();
            case C_POSITION:
                return new CTuning();
            case C_SHARP_POSITION:
                return new DFlatTuning();
            case D_POSITION:
                return new DTuning();
            case D_SHARP_POSITION:
                return new EFlatTuning();
            case E_POSITION:
                return new ETuning();
            case F_POSITION:
                return new FTuning();
            case F_SHARP_POSITION:
                return new GFlatTuning();
            case G_POSITION:
                return new GTuning();
            case G_SHARP_POSITION:
                return new AFlatTuning();
            case A_POSITION:
                return new ATuning();
            case B_FLAT_POSITION:
                return new BFlatTuning();
            case B_POSITION:
                return new BTuning();
            default:
                Log.w("com.github.cythara", "Unknown position for tuning dropdown list");
                return new ChromaticTuning();
        }
    }
}
