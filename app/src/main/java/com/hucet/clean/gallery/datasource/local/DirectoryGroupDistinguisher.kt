package com.hucet.clean.gallery.datasource.local

import android.support.annotation.VisibleForTesting
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.extension.isExternalStorageDir
import com.hucet.clean.gallery.gallery.category.CategoryType
import com.hucet.clean.gallery.model.Medium
import java.io.File
import javax.inject.Inject

/**
 * Created by taesu on 2017-11-15.
 */
open class DirectoryGroupDistinguisher @Inject constructor(private val config: ApplicationConfig) {
    private val parentPathMap = HashMap<String, String>()

    open fun isDirectoryRoot(curPath: String): Boolean {
        return curPath.isExternalStorageDir() && config.categoryType == CategoryType.DIRECTORY
    }

    open fun getParentPath(path: String): String {
        return File(path).parent
    }

    @VisibleForTesting
    fun getMappedParentPaths(): Set<String> {
        return parentPathMap.keys
    }

    operator fun plusAssign(medium: Medium) {
        val parentPath = getParentPath(medium.path)
        if (!parentPathMap.containsKey(parentPath)) {
            parentPathMap.put(parentPath, parentPath)
        }
    }

    fun clear() {
        parentPathMap.clear()
    }
}