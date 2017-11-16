package com.hucet.clean.gallery.gallery.category

/**
 * Created by taesu on 2017-11-15.
 */
enum class CategoryType {
    DIRECTORY, DATE;

    fun toggle(): CategoryType {
        return when (this) {
            DIRECTORY -> DATE
            DATE -> DIRECTORY
        }
    }

}