package com.github.cythara;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.FastYin;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

import static be.tarsos.dsp.io.android.AudioDispatcherFactory.fromDefaultMicrophone;
import static be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm.FFT_YIN;

public class MainActivity extends AppCompatActivity {

    private static final int SAMPLE_RATE = 44100;
    private static final int BUFFER_SIZE = FastYin.DEFAULT_BUFFER_SIZE;
    private static final int OVERLAP = FastYin.DEFAULT_OVERLAP;
    private static final double MAX_DEVIATION = 50;
    private static final int MIN_ITEMS_COUNT = 5;
    private static List<PitchDifference> pitchDifferences = new ArrayList<>();

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

                            String msg;

                            if (Math.abs(pitchDifference.deviation) < MAX_DEVIATION) {
                                msg = String.format(Locale.US, "Closest: %s Diff: %f Freq: %f",
                                        pitchDifference.closest.name(),
                                        pitchDifference.deviation, pitch);

                                Log.d("com.github.cythara", msg);

                                pitchDifferences.add(pitchDifference);

                                if (pitchDifferences.size() >= MIN_ITEMS_COUNT) {
                                    PitchDifference average =
                                            Sampler.calculateAverageDifference(pitchDifferences);

                                    Message message = new Message();
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("pitchDiff", average);
                                    message.setData(bundle);

                                    activity.updateHandler.sendMessage(message);

                                    pitchDifferences.clear();
                                }
                            }
                        }
                    }
                };

                PitchProcessor pitchProcessor = new PitchProcessor(FFT_YIN, SAMPLE_RATE,
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
                TunerView tunerView = (TunerView) activity.findViewById(R.id.pitch);

                PitchDifference pitchDiff = msg.getData().getParcelable("pitchDiff");

                tunerView.setPitchDifference(pitchDiff);
                tunerView.invalidate();
            }
        }
    }
}
