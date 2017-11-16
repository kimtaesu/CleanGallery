package com.hucet.clean.gallery.extension

import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable

/**
 * Created by taesu on 2017-11-17.
 */
fun Drawable.startAsAnimatable() {
    if (this is Animatable)
        this.start()
}