package com.hucet.clean.gallery.config

import android.app.Application
import com.hucet.clean.gallery.gallery.category.DateClassifier
import javax.inject.Inject

/**
 * Created by taesu on 2017-10-30.
 */
class ApplicationConfig @Inject constructor(
        val application: Application
) {
    val filterdType: Int = PreferenceHelper.defaultPrefs(application)[key_filter_media, VIDEOS or IMAGES or GIFS]

    fun getDirSortType(path: String): Int {
        return PreferenceHelper.defaultPrefs(application)[key_file_sorting + path.toLowerCase(), SORT_BY_DATE_MODIFIED or SORT_DESCENDING]
    }

    fun getDateSortType(path: String): Int {
        return PreferenceHelper.defaultPrefs(application)[key_file_sorting + path.toLowerCase(), SORT_BY_DAILY or SORT_DESCENDING]
    }
}
