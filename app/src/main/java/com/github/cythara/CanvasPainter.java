package com.github.cythara;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;

import java.util.Locale;
import java.util.Objects;

import androidx.core.content.ContextCompat;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static com.github.cythara.MainActivity.*;

class CanvasPainter {

    private static final double TOLERANCE = 10D;
    private static final int MAX_DEVIATION = 60;
    private static final int NUMBER_OF_MARKS_PER_SIDE = 6;
    private final Context context;

    private Canvas canvas;

    private TextPaint textPaint = new TextPaint(ANTI_ALIAS_FLAG);
    private TextPaint numbersPaint = new TextPaint(ANTI_ALIAS_FLAG);
    private Paint gaugePaint = new Paint(ANTI_ALIAS_FLAG);
    private Paint symbolPaint = new TextPaint(ANTI_ALIAS_FLAG);

    private int redBackground;
    private int greenBackground;
    private int textColor;

    private PitchDifference pitchDifference;

    private float gaugeWidth;
    private float x;
    private float y;
    private boolean useScientificNotation;
    private int referencePitch;

    private CanvasPainter(Context context) {
        this.context = context;
    }

    static CanvasPainter with(Context context) {
        return new CanvasPainter(context);
    }

    CanvasPainter paint(PitchDifference pitchDifference) {
        this.pitchDifference = pitchDifference;

        return this;
    }

    void on(Canvas canvas) {
        SharedPreferences preferences = context.getSharedPreferences(
                PREFS_FILE, Context.MODE_PRIVATE);

        useScientificNotation = preferences.getBoolean(
                USE_SCIENTIFIC_NOTATION, true);

        referencePitch = preferences.getInt(
                REFERENCE_PITCH, 440);

        this.canvas = canvas;

        redBackground = R.color.red_light;
        greenBackground = R.color.green_light;
        textColor = Color.BLACK;
        if (isDarkModeEnabled()) {
            int color = context.getResources().getColor(R.color.colorPrimaryDark);
            this.canvas.drawColor(color);

            redBackground = R.color.red_dark;
            greenBackground = R.color.green_dark;
            textColor = context.getResources().getColor(R.color.colorTextDarkCanvas);
        }

        gaugeWidth = 0.45F * canvas.getWidth();
        x = canvas.getWidth() / 2F;
        y = canvas.getHeight() / 2F;

        textPaint.setColor(textColor);
        int textSize = context.getResources().getDimensionPixelSize(R.dimen.noteTextSize);
        textPaint.setTextSize(textSize);

        drawGauge();

        if (!isAutoModeEnabled()) {
            Note[] tuningNotes = getCurrentTuning().getNotes();
            Note note = tuningNotes[getReferencePosition()];
            drawText(x, y / 4F, note, symbolPaint);
        }

        if (pitchDifference != null) {
            int abs = Math.abs(getNearestDeviation());
            boolean shouldDraw = abs <= MAX_DEVIATION ||
                    (abs <= MAX_DEVIATION * 2 && !isAutoModeEnabled());
            if (shouldDraw) {
                setBackground();

                drawGauge();

                drawIndicator();

                if (!isAutoModeEnabled()) {
                    drawDeviation();
                }

                float x = canvas.getWidth() / 2F;
                float y = canvas.getHeight() * 0.75f;

                drawText(x, y, pitchDifference.closest, textPaint);
            } else {
                drawListeningIndicator();
            }
        } else {
            drawListeningIndicator();
        }
    }

    private void drawDeviation() {
        long rounded = Math.round(pitchDifference.deviation);
        String text = String.valueOf(rounded);

        Rect bounds = new Rect();
        symbolPaint.getTextBounds(text, 0, text.length(), bounds);
        int width = bounds.width();

        float xPos = x - width / 2F;
        float yPos = canvas.getHeight() / 3F;

        canvas.drawText(text, xPos, yPos, symbolPaint);
    }

    private void drawGauge() {
        gaugePaint.setColor(textColor);

        int gaugeSize = context.getResources().getDimensionPixelSize(R.dimen.gaugeSize);
        gaugePaint.setStrokeWidth(gaugeSize);

        int textSize = context.getResources().getDimensionPixelSize(R.dimen.numbersTextSize);
        numbersPaint.setTextSize(textSize);
        numbersPaint.setColor(textColor);

        canvas.drawLine(x - gaugeWidth, y, x + gaugeWidth, y, gaugePaint);

        float spaceWidth = gaugeWidth / NUMBER_OF_MARKS_PER_SIDE;

        int stepWidth = MAX_DEVIATION / NUMBER_OF_MARKS_PER_SIDE;
        for (int i = 0; i <= MAX_DEVIATION; i = i + stepWidth) {
            float factor = i / stepWidth;
            drawMark(y, x + factor * spaceWidth, i);
            drawMark(y, x - factor * spaceWidth, -i);
        }

        drawSymbols(spaceWidth);

        displayReferencePitch();
    }

    private void displayReferencePitch() {
        float y = canvas.getHeight() * 0.9f;

        Note note = new Note() {
            @Override
            public NoteName getName() {
                return NoteName.A;
            }

            @Override
            public int getOctave() {
                return 4;
            }

            @Override
            public String getSign() {
                return "";
            }
        };

        TextPaint paint = new TextPaint(ANTI_ALIAS_FLAG);
        paint.setColor(textColor);
        int size = (int) (textPaint.getTextSize() / 2);
        paint.setTextSize(size);

        float offset = paint.measureText(getNote(note.getName()) + getOctave(4)) * 0.75f;

        drawText(x - gaugeWidth, y, note, paint);
        canvas.drawText(String.format(Locale.ENGLISH, "= %d Hz", referencePitch),
                x - gaugeWidth + offset, y, paint);
    }

    private void drawListeningIndicator() {
        int resourceId = R.drawable.ic_line_style_icons_mic;

        if (ListenerFragment.IS_RECORDING) {
            resourceId = R.drawable.ic_line_style_icons_mic_active;
        }

        Drawable drawable = ContextCompat.getDrawable(context, resourceId);

        int x = (int) (canvas.getWidth() / 2F);
        int y = (int) (canvas.getHeight() - canvas.getHeight() / 3F);

        int width = Objects.requireNonNull(drawable).getIntrinsicWidth() * 2;
        int height = drawable.getIntrinsicHeight() * 2;
        drawable.setBounds(
                x - width / 2, y,
                x + width / 2,
                y + height);


        drawable.draw(canvas);
    }

    private void drawSymbols(float spaceWidth) {
        String sharp = "♯";
        String flat = "♭";

        int symbolsTextSize = context.getResources().getDimensionPixelSize(R.dimen.symbolsTextSize);
        symbolPaint.setTextSize(symbolsTextSize);
        symbolPaint.setColor(textColor);

        float yPos = canvas.getHeight() / 4F;
        canvas.drawText(sharp,
                x + NUMBER_OF_MARKS_PER_SIDE * spaceWidth - symbolPaint.measureText(sharp) / 2F,
                yPos, symbolPaint);

        canvas.drawText(flat,
                x - NUMBER_OF_MARKS_PER_SIDE * spaceWidth - symbolPaint.measureText(flat) / 2F,
                yPos,
                symbolPaint);
    }

    private void drawIndicator() {
        float xPos = x + (getNearestDeviation() * gaugeWidth / MAX_DEVIATION);
        float yPosition = y * 1.15f;

        Matrix matrix = new Matrix();
        float scalingFactor = numbersPaint.getTextSize() / 3;
        matrix.setScale(scalingFactor, scalingFactor);

        Path indicator = new Path();
        indicator.moveTo(0, -2);
        indicator.lineTo(1, 0);
        indicator.lineTo(-1, 0);
        indicator.close();

        indicator.transform(matrix);

        indicator.offset(xPos, yPosition);
        canvas.drawPath(indicator, gaugePaint);
    }

    private void drawMark(float y, float xPos, int mark) {
        String prefix = "";
        if (mark > 0) {
            prefix = "+";
        }
        String text = prefix + mark;

        int yOffset = (int) (numbersPaint.getTextSize() / 6);
        if (mark % 10 == 0) {
            yOffset *= 2;
        }
        if (mark % 20 == 0) {
            canvas.drawText(text, xPos - numbersPaint.measureText(text) / 2F,
                    y - numbersPaint.getTextSize(), numbersPaint);
            yOffset *= 2;
        }

        canvas.drawLine(xPos, y - yOffset, xPos, y + yOffset, gaugePaint);
    }

    private void drawText(float x, float y, Note note, Paint textPaint) {
        String noteText = getNote(note.getName());
        float offset = textPaint.measureText(noteText) / 2F;

        String sign = note.getSign();
        String octave = String.valueOf(getOctave(note.getOctave()));

        TextPaint paint = new TextPaint(ANTI_ALIAS_FLAG);
        paint.setColor(textColor);
        int textSize = (int) (textPaint.getTextSize() / 2);
        paint.setTextSize(textSize);

        float factor = 0.75f;
        if (useScientificNotation) {
            factor = 1.5f;
        }

        canvas.drawText(sign, x + offset * 1.25f, y - offset * factor, paint);
        canvas.drawText(octave, x + offset * 1.25f, y + offset * 0.5f, paint);

        canvas.drawText(noteText, x - offset, y, textPaint);
    }

    private int getOctave(int octave) {
        if (useScientificNotation) {
            return octave;
        }

        /*
            The octave number in the (French notation) of Solfège is one less than the
            corresponding octave number in the scientific pitch notation.
            There is also no octave with the number zero
            (see https://fr.wikipedia.org/wiki/Octave_(musique)#Solf%C3%A8ge).
         */
        if (octave <= 1) {
            return octave - 2;
        }

        return octave - 1;
    }

    private String getNote(NoteName name) {
        if (useScientificNotation) {
            return name.getScientific();
        }

        return name.getSol();
    }

    private void setBackground() {
        int color = redBackground;
        String text = "✗";
        if (Math.abs(getNearestDeviation()) <= TOLERANCE) {
            color = greenBackground;
            text = "✓";
        }

        canvas.drawColor(context.getResources().getColor(color));

        canvas.drawText(text,
                x + gaugeWidth - symbolPaint.measureText(text),
                canvas.getHeight() * 0.9f, symbolPaint);
    }

    private int getNearestDeviation() {
        float deviation = (float) pitchDifference.deviation;
        int rounded = Math.round(deviation);

        return Math.round(rounded / 10f) * 10;
    }
}
