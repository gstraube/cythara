package com.github.cythara;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Environment;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

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

import static com.github.cythara.tuning.GuitarTuning.Pitch.*;
import static java.lang.String.format;

@RunWith(AndroidJUnit4.class)
public class TunerViewTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Rule
    public GrantPermissionRule recordAudioRule =
            GrantPermissionRule.grant(Manifest.permission.RECORD_AUDIO);

    @Rule
    public GrantPermissionRule writePermissionRule =
            GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);

    @Rule
    public GrantPermissionRule readPermissionRule =
            GrantPermissionRule.grant(Manifest.permission.READ_EXTERNAL_STORAGE);

    @Test
    public void exactly_matching_pitch_is_displayed() throws IOException {
        isDisplayedCorrectly(R.drawable.exact, "exact", new PitchDifference(E4, 0));
    }

    @Test
    public void close_match_is_displayed_correctly() throws IOException {
        isDisplayedCorrectly(R.drawable.close, "close", new PitchDifference(G3, 2.4));
    }

    @Test
    public void exact_deviations_are_displayed_correctly() throws IOException {
        Map<Integer, DrawableResource> deviationToReferenceId = new HashMap<>();

        deviationToReferenceId.put(-30, new DrawableResource(R.drawable.negative_30_cents,
                "negative_30_cents"));
        deviationToReferenceId.put(-20, new DrawableResource(R.drawable.negative_20_cents,
                "negative_20_cents"));
        deviationToReferenceId.put(-10, new DrawableResource(R.drawable.negative_10_cents,
                "negative_10_cents"));
        deviationToReferenceId.put(10, new DrawableResource(R.drawable.positive_10_cents,
                "positive_10_cents"));
        deviationToReferenceId.put(20, new DrawableResource(R.drawable.positive_20_cents,
                "positive_20_cents"));
        deviationToReferenceId.put(30, new DrawableResource(R.drawable.positive_30_cents,
                "positive_30_cents"));

        for (Integer deviation : deviationToReferenceId.keySet()) {
            DrawableResource drawableResource = deviationToReferenceId.get(deviation);
            isDisplayedCorrectly(drawableResource.id, drawableResource.name,
                    new PitchDifference(B3, deviation));
        }
    }

    @Test
    public void non_exact_deviations_are_displayed_correctly() throws IOException {
        Map<Double, DrawableResource> deviationToReferenceId = new HashMap<>();

        deviationToReferenceId.put(-6.4, new DrawableResource(R.drawable.negative_10_cents,
                "negative_10_cents"));
        deviationToReferenceId.put(15.41, new DrawableResource(R.drawable.positive_20_cents,
                "positive_20_cents"));
        deviationToReferenceId.put(5.1, new DrawableResource(R.drawable.positive_10_cents,
                "positive_10_cents"));
        deviationToReferenceId.put(-27.32, new DrawableResource(R.drawable.negative_30_cents,
                "negative_30_cents"));
        deviationToReferenceId.put(4.7, new DrawableResource(R.drawable.positive_10_cents,
                "positive_10_cents"));
        deviationToReferenceId.put(29.5, new DrawableResource(R.drawable.positive_30_cents,
                "positive_30_cents"));

        for (Double deviation : deviationToReferenceId.keySet()) {
            DrawableResource drawableResource = deviationToReferenceId.get(deviation);
            isDisplayedCorrectly(drawableResource.id, drawableResource.name,
                    new PitchDifference(B3, deviation));
        }
    }

    @Test
    public void values_outside_of_boundaries_are_not_displayed() throws IOException {
        isDisplayedCorrectly(R.drawable.blank, "blank",
                new PitchDifference(D3, 65));
        isDisplayedCorrectly(R.drawable.blank, "blank",
                new PitchDifference(D3, -70));
    }

    public void isDisplayedCorrectly(int referenceId, String fileName,
                                     PitchDifference pitchDifference)
            throws IOException {
        MainActivity mainActivity = mActivityRule.getActivity();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap reference = BitmapFactory.decodeResource(mainActivity.getResources(),
                referenceId, options);

        Bitmap generated = Bitmap.createBitmap(2048, 1024, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(generated);
        TunerView tunerView = mainActivity.findViewById(R.id.pitch);
        tunerView.setPitchDifference(pitchDifference);

        tunerView.draw(canvas);

        writeToFile(generated, format("%s.png", fileName));

        Assert.assertTrue(reference.sameAs(generated));
    }

    private void writeToFile(Bitmap bitmap, String name) throws IOException {
        File sdCard = Environment.getExternalStorageDirectory();
        try (FileOutputStream out = new FileOutputStream(sdCard.getAbsolutePath() + "/" + name)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static class DrawableResource {
        int id;
        String name;

        DrawableResource(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
