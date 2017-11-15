package com.hucet.clean.gallery.preference

import android.os.Bundle
import android.support.v7.preference.XpPreferenceFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hucet.clean.gallery.R

/**
 * Created by taesu on 2017-11-16.
 */
class SettingFragment : XpPreferenceFragment() {
    companion object {
        fun newInstance(rootKey: String?): SettingFragment {
            val args = Bundle()
            args.putString(ARG_PREFERENCE_ROOT, rootKey)
            val fragment = SettingFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onCreatePreferences2(savedInstanceState: Bundle?, rootKey: String?) {
        // Set an empty screen so getPreferenceScreen doesn't return null -
        // so we can create fake headers from the get-go.
        preferenceScreen = preferenceManager.createPreferenceScreen(preferenceManager.context)

        // Add 'general' preferences.
        addPreferencesFromResource(R.xml.pref_setting)
    }
}