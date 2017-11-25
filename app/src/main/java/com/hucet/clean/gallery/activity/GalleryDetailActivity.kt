package com.hucet.clean.gallery.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.gallery.fragment.GalleryDetailFragment
import com.hucet.clean.gallery.gallery.fragment.glide.GlideApp
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        //Postpone the enter transition until image is loaded in this activity
        supportPostponeEnterTransition()
        updateview()
    }

    private fun updateview() {
        println("!!!!!!!!!!! ${medium.path}")
        Glide.with(this)
                .load(medium.path)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        println("!!!!!!! onLoadFailed ${e?.printStackTrace()}")
                        startPostponedEnterTransition();
                        return true
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        startPostponedEnterTransition();
                        println("!!!!!!! onResourceReady")
                        return true
                    }

                })
                .into(imageview_content)
    }
}