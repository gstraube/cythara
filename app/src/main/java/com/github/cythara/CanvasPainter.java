package com.github.cythara;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;

import static com.github.cythara.ListenerFragment.*;

class CanvasPainter {

    private static final double TOLERANCE = 5D;
    private static final int MAX_DEVIATION = 60;
    private static final int NUMBER_OF_MARKS = 7;
    private final Context context;

    private Canvas canvas;

    private TextPaint textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
    private TextPaint numbersPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
    private Paint gaugePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint symbolPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
    private Paint background = new Paint();

    private PitchDifference pitchDifference;

    private float gaugeWidth;
    private float x;
    private float y;

    static CanvasPainter with(Context context) {
        return new CanvasPainter(context);
    }

    private CanvasPainter(Context context) {
        this.context = context;
    }

    CanvasPainter paint(PitchDifference pitchDifference) {
        this.pitchDifference = pitchDifference;

        return this;
    }

    void on(Canvas canvas) {
        this.canvas = canvas;

        gaugeWidth = 0.45F * canvas.getWidth();
        x = canvas.getWidth() / 2F;
        y = canvas.getHeight() / 2F;

        textPaint.setColor(Color.BLACK);
        int textSize = context.getResources().getDimensionPixelSize(R.dimen.noteTextSize);
        textPaint.setTextSize(textSize);

        drawGauge();

        if (pitchDifference != null && Math.abs(pitchDifference.deviation) <= MAX_DEVIATION) {
            setBackground();

            drawIndicator();

            drawText();
        } else {
            drawListeningIndicator();
        }
    }

    private void drawGauge() {
        gaugePaint.setColor(Color.BLACK);

        int gaugeSize = context.getResources().getDimensionPixelSize(R.dimen.gaugeSize);
        gaugePaint.setStrokeWidth(gaugeSize);

        int textSize = context.getResources().getDimensionPixelSize(R.dimen.numbersTextSize);
        numbersPaint.setTextSize(textSize);

        canvas.drawLine(x - gaugeWidth, y, x + gaugeWidth, y, gaugePaint);

        float spaceWidth = gaugeWidth / 3F;

        int stepWidth = MAX_DEVIATION / ((NUMBER_OF_MARKS - 1) / 2);
        for (int i = 0; i <= MAX_DEVIATION; i = i + stepWidth) {
            float factor = i / stepWidth;
            drawMark(y, x + factor * spaceWidth, i);
            drawMark(y, x - factor * spaceWidth, -i);
        }

        drawSymbols(spaceWidth);
    }

    private void drawListeningIndicator() {
        int resourceId = R.drawable.ic_line_style_icons_mic;

        if (IS_RECORDING) {
            resourceId = R.drawable.ic_line_style_icons_mic_active;
        }

        Drawable drawable = ContextCompat.getDrawable(context, resourceId);

        int x = (int) (canvas.getWidth() / 2F);
        int y = (int) (canvas.getHeight() - canvas.getHeight() / 3F);

        int width = drawable.getIntrinsicWidth() * 2;
        int height = drawable.getIntrinsicHeight() * 2;
        drawable.setBounds(x - width / 2, y,
                x + width / 2, y + height);


        drawable.draw(canvas);
    }

    private void drawSymbols(float spaceWidth) {
        String sharp = "♯";
        String flat = "♭";

        int symbolsTextSize = context.getResources().getDimensionPixelSize(R.dimen.symbolsTextSize);
        symbolPaint.setTextSize(symbolsTextSize);

        float yPos = canvas.getHeight() / 4F;
        canvas.drawText(sharp, x + 3 * spaceWidth - symbolPaint.measureText(sharp) / 2F, yPos,
                symbolPaint);

        canvas.drawText(flat, x - 3 * spaceWidth - symbolPaint.measureText(flat) / 2F, yPos,
                symbolPaint);
    }

    private void drawIndicator() {
        float deviation = (float) pitchDifference.deviation;

        float xPos = x + (deviation * gaugeWidth / MAX_DEVIATION);
        String text = "|";
        canvas.drawText(text, xPos - numbersPaint.measureText(text) / 2F, y + 30,
                numbersPaint);
    }

    private void drawMark(float y, float xPos, int mark) {
        String prefix = "";
        if (mark > 0) {
            prefix = "+";
        }
        String text = prefix + String.valueOf(mark);

        canvas.drawLine(xPos, y - 10, xPos, y + 10, gaugePaint);
        canvas.drawText(text, xPos - numbersPaint.measureText(text) / 2F, y - 30, numbersPaint);
    }

    private void drawText() {
        float x = canvas.getWidth() / 2F;
        float y = canvas.getHeight() - canvas.getHeight() / 4F;

        String note = pitchDifference.closest.getName();
        float offset = textPaint.measureText(note) / 2F;

        canvas.drawText(note, x - offset, y, textPaint);
    }

    private void setBackground() {
        int color = Color.RED;
        if (Math.abs(pitchDifference.deviation) <= TOLERANCE) {
            color = Color.GREEN;
        }
        background.setColor(color);

        background.setStyle(Paint.Style.FILL);
        background.setAlpha(70);

        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), background);
    }
}