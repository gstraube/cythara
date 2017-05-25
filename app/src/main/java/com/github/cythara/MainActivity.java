package com.github.cythara;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Locale;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.McLeodPitchMethod;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

import static be.tarsos.dsp.io.android.AudioDispatcherFactory.fromDefaultMicrophone;
import static be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm.MPM;

public class MainActivity extends AppCompatActivity {

    private static final int SAMPLE_RATE = 44100;
    private static final int BUFFER_SIZE = McLeodPitchMethod.DEFAULT_BUFFER_SIZE * 7;
    private static final int OVERLAP = McLeodPitchMethod.DEFAULT_OVERLAP;

    final Handler updateHandler = new UpdateHandler(this);
    final PitchListener pitchListener = new PitchListener(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(pitchListener).start();
    }

    private static class PitchListener implements Runnable {

        private final WeakReference<MainActivity> mainActivity;

        PitchListener(MainActivity activity) {
            mainActivity = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            final MainActivity activity = mainActivity.get();

            if (activity != null) {
                PitchDetectionHandler pitchDetectionHandler = new PitchDetectionHandler() {
                    @Override
                    public void handlePitch(PitchDetectionResult pitchDetectionResult,
                                            AudioEvent audioEvent) {
                        float pitch = pitchDetectionResult.getPitch();

                        if (pitch != -1) {
                            PitchDifference pitchDifference = PitchComparator.retrieveNote(pitch);

                            String msg = String.format(Locale.US, "Closest: %s Diff: %f Freq: %f",
                                    pitchDifference.closest.getGuitarString(),
                                    pitchDifference.deviation, pitch);

                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("pitch", msg);
                            message.setData(bundle);
                            activity.updateHandler.sendMessage(message);

                            Log.d("com.github.cythara", msg);
                        }
                    }
                };

                PitchProcessor pitchProcessor = new PitchProcessor(MPM, SAMPLE_RATE,
                        BUFFER_SIZE, pitchDetectionHandler);

                AudioDispatcher audioDispatcher = fromDefaultMicrophone(SAMPLE_RATE,
                        BUFFER_SIZE, OVERLAP);

                audioDispatcher.addAudioProcessor(pitchProcessor);

                audioDispatcher.run();
            }
        }
    }

    private static class UpdateHandler extends Handler {

        private final WeakReference<MainActivity> mainActivity;

        UpdateHandler(MainActivity activity) {
            mainActivity = new WeakReference<>(activity);
        }

        public void handleMessage(Message msg) {
            MainActivity activity = mainActivity.get();

            if (activity != null) {
                TextView pitchText = (TextView) activity.findViewById(R.id.pitch);

                pitchText.setText(msg.getData().getString("pitch"));
            }
        }
    }
}
