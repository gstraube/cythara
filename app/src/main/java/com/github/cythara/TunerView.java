package com.github.cythara;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class TunerView extends View {

    private CanvasPainter canvasPainter;
    private PitchDifference pitchDifference;

    public TunerView(Context context) {
        super(context);
        canvasPainter = CanvasPainter.with(getContext());
    }

    public TunerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        canvasPainter = CanvasPainter.with(getContext());
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasPainter.paint(pitchDifference).on(canvas);
    }

    public void setPitchDifference(PitchDifference pitchDifference) {
        this.pitchDifference = pitchDifference;
    }
}