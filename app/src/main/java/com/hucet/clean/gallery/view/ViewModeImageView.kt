package com.hucet.clean.gallery.view

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.content.Context
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import com.hucet.clean.gallery.config.ApplicationConfig
import android.arch.lifecycle.OnLifecycleEvent


/**
 * Created by taesu on 2017-11-16.
 */
class ViewModeImageView : ImageView, LifecycleObserver {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0) {

    }

    fun startAsAnimatable(drawable: Drawable) {
        this.setImageDrawable(drawable)
        if (drawable is Animatable) {
            drawable.start()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun OnPause() {
        this.clearAnimation()
    }

}