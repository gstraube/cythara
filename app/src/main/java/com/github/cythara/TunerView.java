package com.github.cythara;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class TunerView extends View {

    private Paint paint = new Paint();
    private PitchDifference pitchDifference;

    public TunerView(Context context) {
        super(context);
    }

    public TunerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.BLACK);
        paint.setTextSize(60F);

        float x = canvas.getWidth() / 2F;
        float y = canvas.getHeight() / 2F;

        if (pitchDifference != null) {
            canvas.drawText(pitchDifference.closest.name(), x, y, paint);
        }
    }

    public void setPitchDifference(PitchDifference pitchDifference) {
        this.pitchDifference = pitchDifference;
    }
}