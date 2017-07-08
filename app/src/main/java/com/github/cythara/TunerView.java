package com.github.cythara;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class TunerView extends View {

    private static final double MAX_ALLOWED_DEVIATION = 3D;

    private Paint paint = new Paint();
    private Paint background = new Paint();
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
        paint.setTextSize(120F);

        float x = canvas.getWidth() / 2F;
        float y = canvas.getHeight() / 2F;

        if (pitchDifference != null) {
            setBackground(canvas);

            canvas.drawText(pitchDifference.closest.name(), x, y, paint);
        }
    }

    private void setBackground(Canvas canvas) {
        int color = Color.RED;
        if (pitchDifference.deviation <= MAX_ALLOWED_DEVIATION) {
            color = Color.GREEN;
        }
        background.setColor(color);

        background.setStyle(Paint.Style.FILL);
        background.setAlpha(70);

        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), background);
    }

    public void setPitchDifference(PitchDifference pitchDifference) {
        this.pitchDifference = pitchDifference;
    }
}