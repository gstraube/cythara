package com.github.cythara;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import com.github.cythara.tuning.GuitarTuning;
import com.jaredrummler.materialspinner.MaterialSpinner;

import static android.widget.ArrayAdapter.createFromResource;
import static com.github.cythara.TuningMapper.getTuningFromPosition;

public class MainActivity extends AppCompatActivity implements ListenerFragment.TaskCallbacks,
        MaterialSpinner.OnItemSelectedListener {

    public static final int RECORD_AUDIO_PERMISSION = 0;
    private static final String TAG_LISTENER_FRAGMENT = "listener_fragment";
    static Tuning tuning = new GuitarTuning();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_PERMISSION);
        } else {
            startRecording();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialSpinner spinner = findViewById(R.id.tuning);
        ArrayAdapter<CharSequence> adapter = createFromResource(this,
                R.array.tunings, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setTitle(R.string.app_name);
        myToolbar.showOverflowMenu();
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_privacy_policy: {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://gstraube.github.io/privacy_policy.html"));
                startActivity(browserIntent);

                break;
            }
        }

        return false;
    }

    @Override
    public void onProgressUpdate(PitchDifference pitchDifference) {
        TunerView tunerView = this.findViewById(R.id.pitch);

        tunerView.setPitchDifference(pitchDifference);
        tunerView.invalidate();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case RECORD_AUDIO_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startRecording();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Permission required");
                    alertDialog.setMessage("Microphone permission is required. App will be closed");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        finishAffinity();
                                    } else {
                                        finish();
                                    }
                                }
                            });
                    alertDialog.show();
                }
            }
        }
    }

    private void startRecording() {
        FragmentManager fragmentManager = getFragmentManager();
        ListenerFragment listenerFragment = (ListenerFragment)
                fragmentManager.findFragmentByTag(TAG_LISTENER_FRAGMENT);

        if (listenerFragment == null) {
            listenerFragment = new ListenerFragment();
            fragmentManager
                    .beginTransaction()
                    .add(listenerFragment, TAG_LISTENER_FRAGMENT)
                    .commit();
        }
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        tuning = getTuningFromPosition(position);
    }
}
