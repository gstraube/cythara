package com.github.cythara;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;

import com.shawnlin.numberpicker.NumberPicker;

public class NumberPickerDialog extends DialogFragment {

    private NumberPicker.OnValueChangeListener valueChangeListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final NumberPicker numberPicker = new NumberPicker(getActivity());

        Bundle arguments = getArguments();
        int currentValue = arguments.getInt("current_value", 440);

        numberPicker.setMinValue(300);
        numberPicker.setMaxValue(500);
        numberPicker.setValue(currentValue);

        if (MainActivity.isDarkModeEnabled()) {
            int color = getResources().getColor(R.color.colorTextDark);
            numberPicker.setTextColor(color);
            numberPicker.setDividerColor(color);
            numberPicker.setSelectedTextColor(color);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(),
                R.style.AppTheme));
        builder.setTitle("Set reference pitch");
        builder.setMessage("Choose a frequency:");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                valueChangeListener.onValueChange(numberPicker,
                        numberPicker.getValue(), numberPicker.getValue());
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { }
        });

        builder.setView(numberPicker);
        return builder.create();
    }

    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }
}
