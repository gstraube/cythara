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
    public void positive_10_cents_deviation_is_displayed() throws IOException {
        isDisplayedCorrectly(R.drawable.positive_10_cents,
                new PitchDifference(Note.B3, 10));
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

        Assert.assertTrue(reference.sameAs(generated));

        writeToFile(generated, "generated.png");
        writeToFile(reference, "reference.png");
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