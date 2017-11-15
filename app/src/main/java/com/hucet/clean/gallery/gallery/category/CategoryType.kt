package com.hucet.clean.gallery.gallery.category

/**
 * Created by taesu on 2017-11-15.
 */
enum class CategoryType {
    DIRECTORY, DATE;

    companion object {
        fun toggle(type: CategoryType): CategoryType {
            return when (type) {
                DIRECTORY -> DATE
                DATE -> DIRECTORY
            }
        }
    }

}