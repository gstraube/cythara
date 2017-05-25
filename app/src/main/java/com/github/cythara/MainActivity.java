package com.github.cythara;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

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

    final Handler myHandler = new Handler() {

        public void handleMessage(Message msg) {
            final TextView pitchText = (TextView) findViewById(R.id.pitch);

            pitchText.setText(msg.getData().getString("pitch"));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                PitchDetectionHandler pitchDetectionHandler = new PitchDetectionHandler() {
                    @Override
                    public void handlePitch(PitchDetectionResult pitchDetectionResult,
                                            AudioEvent audioEvent) {
                        float pitch = pitchDetectionResult.getPitch();

                        if (pitch != -1) {
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("pitch", String.valueOf(pitch));
                            message.setData(bundle);
                            myHandler.sendMessage(message);

                            Log.d("com.github.cythara", "Pitch: " + pitch);
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
        };

        new Thread(runnable).start();
    }
}
