package com.hucet.clean.gallery.gallery.category

/**
 * Created by taesu on 2017-11-15.
 */
enum class CategoryMode {
    DIRECTORY, DATE;

    fun toggle(): CategoryMode {
        return when (this) {
            DIRECTORY -> DATE
            DATE -> DIRECTORY
        }
    }

}