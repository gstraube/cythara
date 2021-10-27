package com.github.cythara;

import static com.github.cythara.MainActivity.getReferencePitch;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

import com.github.cythara.glView.MyGLRenderer;
import com.github.cythara.glView.MyGLSurfaceView;
import com.github.cythara.tuning.NoteFrequencyCalculator;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchProcessor;
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm;

public class ListenerFragment extends Fragment {

    private static final int SAMPLE_RATE = 44100;
    private static final int BUFFER_SIZE = 1024 * 4;
    private static final int OVERLAP = 768 * 4;
    private static final int MIN_ITEMS_COUNT = 15;
    static boolean IS_RECORDING;
    private static List<PitchDifference> pitchDifferences = new ArrayList<>();
    private static TaskCallbacks taskCallbacks;
    private PitchListener pitchListener;

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

        pitchListener = new PitchListener();
        pitchListener.execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        taskCallbacks = null;
        pitchListener.cancel(true);
    }

    @Override
    public void onPause() {
        super.onPause();

        pitchListener.cancel(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (pitchListener.isCancelled()) {
            pitchListener = new PitchListener();
            pitchListener.execute();
        }
    }

    interface TaskCallbacks {

        void onProgressUpdate(PitchDifference percent);
    }

    private static class PitchListener extends AsyncTask<Void, PitchDifference, Void> {

        private AudioDispatcher audioDispatcher;

        @Override
        protected Void doInBackground(Void... params) {
            PitchDetectionHandler pitchDetectionHandler = (pitchDetectionResult, audioEvent) -> {

                if (isCancelled()) {
                    stopAudioDispatcher();
                    return;
                }

                if (!IS_RECORDING) {
                    IS_RECORDING = true;
                    publishProgress();
                }

                float pitch = pitchDetectionResult.getPitch();

                if (pitch >= 10) {
                    PitchDifference pitchDifference = PitchComparator.retrieveNote(pitch);

                    pitchDifferences.add(pitchDifference);

                    if (pitchDifferences.size() >= MIN_ITEMS_COUNT) {
                        PitchDifference average = Sampler.calculateAverageDifference(pitchDifferences);
                        if (!average.equals(null)) {
                            int notePosition = MainActivity.noteFrequencyCalculator.getPosition(average.closest);
                            float averagePitch = notePosition + (float) average.deviation * 0.01f;
                            float pitchDifferenceCalc = (averagePitch - MyGLRenderer.averagePitch) * 0.1f;
                            if (Math.abs(pitchDifferenceCalc) > 0.1) {
                                if (Math.abs(pitchDifferenceCalc) > 10) {
                                    pitchDifferenceCalc /= 2;
                                }
                                for (int i = 0; i < 10; i++) {
                                    try {
                                        Thread.sleep(10);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    MyGLRenderer.averagePitch += pitchDifferenceCalc;
                                }
                            }

                            publishProgress(average);
                            pitchDifferences.clear();
                        }
                    }
                }
            };
            PitchProcessor pitchProcessor = new PitchProcessor(PitchEstimationAlgorithm.FFT_YIN,
                    SAMPLE_RATE,
                    BUFFER_SIZE, pitchDetectionHandler);

            audioDispatcher = AudioDispatcherFactory.fromDefaultMicrophone(SAMPLE_RATE,
                    BUFFER_SIZE, OVERLAP);

            audioDispatcher.addAudioProcessor(pitchProcessor);

            audioDispatcher.run();

            return null;
        }

        @Override
        protected void onCancelled(Void result) {
            stopAudioDispatcher();
        }

        @Override
        protected void onProgressUpdate(PitchDifference... pitchDifference) {
            if (taskCallbacks != null) {
                if (pitchDifference.length > 0) {
                    taskCallbacks.onProgressUpdate(pitchDifference[0]);
                } else {
                    taskCallbacks.onProgressUpdate(null);
                }
            }
        }

        private void stopAudioDispatcher() {
            if (audioDispatcher != null && !audioDispatcher.isStopped()) {
                audioDispatcher.stop();
                IS_RECORDING = false;
            }
        }
    }
}
