package com.hucet.clean.gallery.activity.cache

import android.content.Context
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat

/**
 * Created by taesu on 2017-11-16.
 */
class MemoryCacheDrawable {
    companion object {
        val drawableMap = HashMap<Int, Drawable>()

        fun getDrawable(drawableId: Int, context: Context): Drawable {
            var drawable = drawableMap[drawableId]
            synchronized(MemoryCacheDrawable.javaClass)
            {
                if (drawable == null) {
                    drawable = ContextCompat.getDrawable(context.applicationContext, drawableId)
                    drawableMap.put(drawableId, drawable!!);
                }
            }
            return drawable!!
        }

        fun allStopAnimations() {
            drawableMap.values.forEach {
                if (it is Animatable)
                    it.stop()
            }
        }
    }
}