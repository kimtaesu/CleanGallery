package com.hucet.clean.gallery.gallery.category

import android.support.annotation.VisibleForTesting
import com.hucet.clean.gallery.extension.isExternalStorageDir
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Directory
import com.hucet.clean.gallery.model.Medium

/**
 * Created by taesu on 2017-11-12.
 */

open class MediumTransformer(private val dateClassifier: DateClassifier,
                        private val dirClassifier: DirClassifier) {
    fun transform(items: List<Medium>, curPath: String, isDirType: Boolean): List<Basic> {
        if (!isDirType)
            return dateClassifier.classify(items, curPath)
        else if (isExternalStorage(curPath))
            return dirClassifier.classify(items, curPath)
        else
            return items
    }

    @VisibleForTesting
    open fun isExternalStorage(curPath: String): Boolean {
        return curPath.isExternalStorageDir()
    }
}