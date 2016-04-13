package com.andela.motustracker.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;

import com.andela.motustracker.R;

/**
 * Created by Spykins on 13/04/16.
 */
public class NumberPickerPreference extends DialogPreference implements Preference.OnPreferenceChangeListener,
        NumberPicker.OnValueChangeListener{

    private static final String NUMBER_PICKER_DEFAULT = "5";
    private NumberPicker numberPicker;
    private SharedPreferences sharedPreferences;
    private String savedValue;
    private String sharedPreferenceKey = "minimumTime";

    public NumberPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(R.layout.number_picker);
        setOnPreferenceChangeListener(this);

    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        int newTime = numberPicker.getValue();
        if (positiveResult) {
            String newSelectedTime = String.valueOf(newTime);
            if (callChangeListener(newSelectedTime)) {
                saveString(sharedPreferenceKey, newSelectedTime);
            }
        }
        setSummary(newTime + " Minutes");
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        if (restorePersistedValue) {
            setSummary(loadString() + " Minutes");
        } else {
            setSummary(defaultValue.toString() + " Minutes");
            persistString(defaultValue.toString());
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        setSummary(newValue.toString() + " Minutes");
        saveString(sharedPreferenceKey, newValue.toString());
        return false;
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }

    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    protected View onCreateDialogView() {
        View view = super.onCreateDialogView();
        numberPicker = (NumberPicker) view.findViewById(R.id.number_picker);
        numberPicker.setMaxValue(30);
        numberPicker.setMinValue(1);
        numberPicker.setValue(Integer.parseInt(loadString()));
        return view;
    }

    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
    }

    public void saveString(String key, String value) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String loadString() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        savedValue = sharedPreferences.getString(sharedPreferenceKey, NUMBER_PICKER_DEFAULT);
        return savedValue;
    }

}
