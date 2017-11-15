package com.hucet.clean.gallery.preference

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.PreferenceScreen
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.gallery.fragment.GalleryDetailFragment
import net.xpece.android.support.preference.PreferenceScreenNavigationStrategy

/**
 * Created by taesu on 2017-11-16.
 */
class SettingActivity : AppCompatActivity(), PreferenceFragmentCompat.OnPreferenceStartScreenCallback,
        PreferenceScreenNavigationStrategy.ReplaceFragment.Callbacks {

    val mReplaceFragmentStrategy = PreferenceScreenNavigationStrategy.ReplaceFragment(this, R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out);

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_setting)
        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.content, SettingFragment.newInstance(null), "Settings").commit()
    }

    override fun onPreferenceStartScreen(fragment: PreferenceFragmentCompat, screen: PreferenceScreen): Boolean {
        mReplaceFragmentStrategy.onPreferenceStartScreen(supportFragmentManager, fragment, screen)
        return true
    }

    override fun onBuildPreferenceFragment(rootKey: String): PreferenceFragmentCompat {
        return SettingFragment.newInstance(rootKey)
    }
}