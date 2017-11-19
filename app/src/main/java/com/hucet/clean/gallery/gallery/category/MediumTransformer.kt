package com.hucet.clean.gallery.gallery.category

import android.support.annotation.VisibleForTesting
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.extension.isExternalStorageDir
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Medium

/**
 * Created by taesu on 2017-11-12.
 */

open class MediumTransformer(private val dateClassifier: DateClassifier,
                             private val dirClassifier: DirClassifier,
                             private val config: ApplicationConfig) {
    fun transform(items: List<Medium>, curPath: String): List<Basic> {
        when (config.categoryMode) {
            CategoryMode.DATE -> {
                return dateClassifier.classify(config.sortOptionType, items)
            }
            CategoryMode.DIRECTORY -> {
                if (isExternalStorage(curPath)) {
                    return dirClassifier.classify(config.sortOptionType, items)
                }
            }
        }
        return items
    }

    @VisibleForTesting
    open fun isExternalStorage(curPath: String): Boolean {
        return curPath.isExternalStorageDir()
    }
}