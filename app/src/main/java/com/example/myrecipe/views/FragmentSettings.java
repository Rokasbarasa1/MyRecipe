package com.example.myrecipe.views;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.myrecipe.ActivityMain;
import com.example.myrecipe.R;

public class FragmentSettings extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey);

        Preference calendarFunctionality = findPreference("google calendar");
        calendarFunctionality.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean value = (Boolean) newValue;
                ((ActivityMain) getActivity()).changeCalendarFunctionality(newValue);
                return true;
            }
        });

        Preference signOut = findPreference("signOut");
        signOut.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ((ActivityMain) getActivity()).signOut();
                return true;
            }
        });
    }
}