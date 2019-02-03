package com.github.cythara;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
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

        numberPicker.setMinValue(400);
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
        builder.setMessage(R.string.choose_a_frequency);

        builder.setPositiveButton("OK", (dialog, which) -> valueChangeListener.onValueChange(numberPicker,
                numberPicker.getValue(), numberPicker.getValue()));

        builder.setNegativeButton("CANCEL", (dialog, which) -> {
        });

        builder.setView(numberPicker);
        return builder.create();
    }

    @Override
    public void onPause() {
        super.onPause();

        this.dismiss();
    }

    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }
}
