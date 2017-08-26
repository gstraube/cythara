package com.github.cythara;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.FastYin;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

import static be.tarsos.dsp.io.android.AudioDispatcherFactory.fromDefaultMicrophone;
import static be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm.FFT_YIN;

public class ListenerFragment extends Fragment {

    interface TaskCallbacks {
        void onProgressUpdate(PitchDifference percent);
    }

    private static final int SAMPLE_RATE = 44100;
    private static final int BUFFER_SIZE = FastYin.DEFAULT_BUFFER_SIZE;
    private static final int OVERLAP = FastYin.DEFAULT_OVERLAP;
    private static final int MIN_ITEMS_COUNT = 75;
    private static List<PitchDifference> pitchDifferences = new ArrayList<>();

    private TaskCallbacks taskCallbacks;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        taskCallbacks = (TaskCallbacks) context;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            taskCallbacks = (TaskCallbacks) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        PitchListener pitchListener = new PitchListener();
        pitchListener.execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        taskCallbacks = null;
    }

    private class PitchListener extends AsyncTask<Void, PitchDifference, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            PitchDetectionHandler pitchDetectionHandler = new PitchDetectionHandler() {
                @Override
                public void handlePitch(PitchDetectionResult pitchDetectionResult,
                                        AudioEvent audioEvent) {
                    float pitch = pitchDetectionResult.getPitch();

                    if (pitch != -1) {
                        PitchDifference pitchDifference = PitchComparator.retrieveNote(pitch);


                        pitchDifferences.add(pitchDifference);

                        if (pitchDifferences.size() >= MIN_ITEMS_COUNT) {
                            PitchDifference average =
                                    Sampler.calculateAverageDifference(pitchDifferences);

                            publishProgress(average);

                            pitchDifferences.clear();
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

            return null;
        }

        @Override
        protected void onProgressUpdate(PitchDifference... pitchDifference) {
            if (taskCallbacks != null) {
                taskCallbacks.onProgressUpdate(pitchDifference[0]);
            }
        }
    }
}
