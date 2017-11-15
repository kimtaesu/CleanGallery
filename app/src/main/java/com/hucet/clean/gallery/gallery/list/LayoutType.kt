package com.hucet.clean.gallery.gallery.list

/**
 * Created by taesu on 2017-11-15.
 */
enum class LayoutType {
    GRID, LINEAR;

    companion object {
        fun toggle(type: LayoutType): LayoutType {
            when (type) {
                GRID -> {
                    return LINEAR
                }
                LINEAR -> {
                    return GRID
                }
            }
        }
    }
}