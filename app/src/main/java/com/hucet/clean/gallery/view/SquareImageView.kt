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
class SquareImageView : ImageView {
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}
