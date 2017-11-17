package com.hucet.clean.gallery.extension

import android.graphics.drawable.Drawable
import android.view.MenuItem

/**
 * Created by taesu on 2017-11-17.
 */
fun MenuItem.startAsAnimation(drawable: Drawable?) {
    this.icon = drawable
    this.icon.startAsAnimatable()
}