package com.hucet.clean.gallery.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.model.Medium
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * Created by taesu on 2017-11-25.
 */
class GalleryDetailActivity : AppCompatActivity() {
    companion object {
        val BUNDLE_KEY_MEDIUM = "BUNDLE_KEY_MEDIUM"
        val EXTRA_TRANSITION_NAME = "EXTRA_TRANSITION_NAME"
    }

    val medium by lazy {
        intent.getSerializableExtra(BUNDLE_KEY_MEDIUM) as Medium
    }

    val transitionName by lazy {
        intent.getStringExtra(EXTRA_TRANSITION_NAME) as String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        //Postpone the enter transition until image is loaded in this activity
        supportPostponeEnterTransition()

        imageview_content.transitionName = transitionName
        updateView()
    }

    private fun updateView() {
        println("!!!!!!!!!!! ${medium.path}")
        Glide.with(this)
                .load(medium.path)
                .into(imageview_content)
    }
}