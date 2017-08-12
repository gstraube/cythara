package com.github.cythara;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Environment;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.support.test.rule.GrantPermissionRule.grant;

@RunWith(AndroidJUnit4.class)
public class TunerViewTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Rule
    public GrantPermissionRule writePermissionRule =
            grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);

    @Rule
    public GrantPermissionRule readPermissionRule =
            grant(Manifest.permission.READ_EXTERNAL_STORAGE);

    @Test
    public void exactly_matching_pitch_is_displayed() throws IOException {
        isDisplayedCorrectly(R.drawable.exact, new PitchDifference(Note.E4, 0));
    }

    @Test
    public void close_match_is_displayed_correctly() throws IOException {
        isDisplayedCorrectly(R.drawable.close, new PitchDifference(Note.G3, 2.4));
    }

    @Test
    public void exact_deviations_are_displayed_correctly() throws IOException {
        Map<Integer, Integer> deviationToReferenceId = new HashMap<>();

        deviationToReferenceId.put(-30, R.drawable.negative_30_cents);
        deviationToReferenceId.put(-20, R.drawable.negative_20_cents);
        deviationToReferenceId.put(-10, R.drawable.negative_10_cents);
        deviationToReferenceId.put(10, R.drawable.positive_10_cents);
        deviationToReferenceId.put(20, R.drawable.positive_20_cents);
        deviationToReferenceId.put(30, R.drawable.positive_30_cents);

        for (Integer deviation : deviationToReferenceId.keySet()) {
            isDisplayedCorrectly(deviationToReferenceId.get(deviation),
                    new PitchDifference(Note.B3, deviation));
        }
    }

    @Test
    public void non_exact_deviations_are_displayed_correctly() throws IOException {
        Map<Double, Integer> deviationToReferenceId = new HashMap<>();

        deviationToReferenceId.put(-6.4, R.drawable.negative_6_4_cents);
        deviationToReferenceId.put(15.41, R.drawable.positive_15_41_cents);
        deviationToReferenceId.put(5.1, R.drawable.positive_5_1_cents);
        deviationToReferenceId.put(-27.32, R.drawable.negative_27_32_cents);
        deviationToReferenceId.put(4.7, R.drawable.positive_4_7_cents);
        deviationToReferenceId.put(29.5, R.drawable.positive_29_5_cents);

        for (Double deviation : deviationToReferenceId.keySet()) {
            isDisplayedCorrectly(deviationToReferenceId.get(deviation),
                    new PitchDifference(Note.B3, deviation));
        }
    }

    public void isDisplayedCorrectly(int referenceId, PitchDifference pitchDifference)
            throws IOException {
        MainActivity mainActivity = mActivityRule.getActivity();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap reference = BitmapFactory.decodeResource(mainActivity.getResources(),
                referenceId, options);

        Bitmap generated = Bitmap.createBitmap(2048, 1024, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(generated);
        TunerView tunerView = (TunerView) mainActivity.findViewById(R.id.pitch);
        tunerView.setPitchDifference(pitchDifference);

        tunerView.draw(canvas);

        writeToFile(generated, "generated.png");
        writeToFile(reference, "reference.png");

        Assert.assertTrue(reference.sameAs(generated));
    }

    private void writeToFile(Bitmap bitmap, String name) throws IOException {
        File sdCard = Environment.getExternalStorageDirectory();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(sdCard.getAbsolutePath() + "/" + name);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}