package com.github.cythara;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class TunerView extends View {

    private static final double MAX_ALLOWED_DEVIATION = 3D;

    private Paint textPaint = new Paint();
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

        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(120F);

        if (pitchDifference != null) {
            setBackground(canvas);

            drawText(canvas);
        }
    }

    private void drawText(Canvas canvas) {
        float x = canvas.getWidth() / 2F;
        float y = canvas.getHeight() / 2F;

        String note = pitchDifference.closest.name();
        float offset = textPaint.measureText(note) / 2F;

        canvas.drawText(note, x - offset, y, textPaint);
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