package com.hucet.clean.gallery.view

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet


/**
 * Created by taesu on 2017-11-16.
 */
class SquareImageView : AppCompatImageView {
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}
