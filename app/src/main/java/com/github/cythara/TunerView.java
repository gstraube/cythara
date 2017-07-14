package com.github.cythara;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class TunerView extends View {

    private static final double MAX_ALLOWED_DEVIATION = 3D;

    private TextPaint textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
    private TextPaint numbersPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
    private Paint gaugePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
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
        int textSize = getResources().getDimensionPixelSize(R.dimen.noteTextSize);
        textPaint.setTextSize(textSize);

        if (pitchDifference != null) {
            setBackground(canvas);

            drawGauge(canvas);

            drawText(canvas);
        }
    }

    private void drawGauge(Canvas canvas) {
        float x = canvas.getWidth() / 2F;
        float y = canvas.getHeight() / 2F;

        gaugePaint.setColor(Color.BLACK);

        int gaugeSize = getResources().getDimensionPixelSize(R.dimen.gaugeSize);
        gaugePaint.setStrokeWidth(gaugeSize);

        int textSize = getResources().getDimensionPixelSize(R.dimen.numbersTextSize);
        numbersPaint.setTextSize(textSize);

        float gaugeWidth = 0.45F * canvas.getWidth();

        canvas.drawLine(x - gaugeWidth, y, x + gaugeWidth, y, gaugePaint);

        float spaceWidth = gaugeWidth / 3F;

        for (int i = 0; i <= 30; i = i + 10) {
                float xPos = x + (i / 10F) * spaceWidth;
                canvas.drawLine(xPos, y - 10, xPos, y + 10, gaugePaint);
                String text = String.valueOf(i);
                canvas.drawText(text, xPos - numbersPaint.measureText(text) / 2F, y - 30,
                        numbersPaint);
        }

        for (int i = 30; i >= 0; i = i - 10) {
                float xPos = x - (i / 10F) * spaceWidth;
                canvas.drawLine(xPos, y - 10, xPos, y + 10, gaugePaint);
                String text = String.valueOf(i);
                canvas.drawText(text, xPos - numbersPaint.measureText(text) / 2F, y - 30,
                        numbersPaint);
        }

        float deviation = (float) pitchDifference.deviation;

        float xPos = x + (deviation * gaugeWidth / 30F);
        String text = "|";
        canvas.drawText(text, xPos - numbersPaint.measureText(text) / 2F, y + 30,
                numbersPaint);
    }

    private void drawText(Canvas canvas) {
        float x = canvas.getWidth() / 2F;
        float y = canvas.getHeight() - canvas.getHeight() / 3F;

        String note = pitchDifference.closest.name();
        float offset = textPaint.measureText(note) / 2F;

        canvas.drawText(note, x - offset, y, textPaint);
    }

    private void setBackground(Canvas canvas) {
        int color = Color.RED;
        if (Math.abs(pitchDifference.deviation) <= MAX_ALLOWED_DEVIATION) {
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