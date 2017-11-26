package com.hucet.clean.gallery.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.extension.toastTodo
import com.hucet.clean.gallery.model.Medium
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.layout_toolbar.*

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

//    val transitionName by lazy {
//        intent.getStringExtra(EXTRA_TRANSITION_NAME) as String
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initToolbar()
        //Postpone the enter transition until image is loaded in this activity
//        supportPostponeEnterTransition()

//        imageview_content.transitionName = transitionName
        updateView()
    }


    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setBackgroundDrawable(getDrawable(R.drawable.shape_linear_gradient))
            title = medium.name
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.action_info -> {
                toastTodo(this)
            }
            R.id.action_share -> {
                toastTodo(this)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateView() {
        Glide.with(this)
                .load(medium.path)
//                .listener(object : RequestListener<Drawable> {
//                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
//                        supportStartPostponedEnterTransition()
//                        return true
//                    }
//
//                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//                        supportStartPostponedEnterTransition()
//                        return true
//                    }
//
//                })
                .into(imageview_content)
    }
}