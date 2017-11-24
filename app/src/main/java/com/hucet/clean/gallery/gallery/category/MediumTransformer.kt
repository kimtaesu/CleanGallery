package com.hucet.clean.gallery.gallery.category

import android.support.annotation.VisibleForTesting
import com.hucet.clean.gallery.config.ReadOnlyConfigs
import com.hucet.clean.gallery.extension.isExternalStorageDir
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Medium

/**
 * Created by taesu on 2017-11-12.
 */

class MediumTransformer(private val dateClassifier: DateClassifier,
                        private val dirClassifier: DirClassifier) {
    fun transform(items: List<Medium>, isRoot: Boolean, readOnlyConfigs: ReadOnlyConfigs): List<Basic> {
        return when (readOnlyConfigs.getCategoryMode()) {
            CategoryMode.DATE -> {
                return dateClassifier.classify(readOnlyConfigs.getSortOptionType(), items)
            }
            CategoryMode.DIRECTORY -> {
                if (isRoot) {
                    return dirClassifier.classify(readOnlyConfigs.getSortOptionType(), items)
                }
                return items
            }
        }
    }

}