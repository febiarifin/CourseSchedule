package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode
import java.util.Locale

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        val themePreference = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        themePreference?.setOnPreferenceChangeListener { _, newValue ->
            val mode = NightMode.valueOf(newValue.toString().toUpperCase(Locale.US))
            updateTheme(mode.value)
            true
        }
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        val notificationPreference = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
//        val dailyReminder = DailyReminder()
//        notificationPreference?.setOnPreferenceChangeListener {_, newValue ->
//            val value = newValue as Boolean
//            if (value) {
//                dailyReminder.setDailyReminder(requireContext())
//            } else {
//                dailyReminder.cancelAlarm(requireContext())
//            }
//            true
//        }
        notificationPreference?.setOnPreferenceChangeListener { _, newValue ->
            newValue?.let {
                val value = it as Boolean
                val dailyReminder = DailyReminder()

                when (value) {
                    true -> {
                        context?.let { c -> dailyReminder.setDailyReminder(c) }
                    }
                    false -> {
                        context?.let { c -> dailyReminder.cancelAlarm(c) }
                    }
                }
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}