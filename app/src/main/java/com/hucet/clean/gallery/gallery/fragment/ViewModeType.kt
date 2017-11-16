package com.hucet.clean.gallery.gallery.fragment

/**
 * Created by taesu on 2017-11-15.
 */
enum class ViewModeType {
    GRID, LINEAR;

    fun toggle(): ViewModeType {
        when (this) {
            GRID -> {
                return LINEAR
            }
            LINEAR -> {
                return GRID
            }
        }
    }
}