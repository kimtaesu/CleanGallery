package com.hucet.clean.gallery.gallery.directory

import android.os.Environment
import com.hucet.clean.gallery.config.*
import com.hucet.clean.gallery.gallery.filter.ImageVideoGifFilter
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Medium
import java.util.concurrent.atomic.AtomicReference

/**
 * Created by taesu on 2017-11-26.
 */

interface DirectoryRootChecker {
    fun isRoot(): Boolean
}


open class PathLocationContext(
        private val imageVideoGifFilter: ImageVideoGifFilter,
        private val config: ApplicationConfig,
        private val mappers: Map<DirectoryType, SubjectMapper<Medium, out Basic>>
) : DirectoryRootChecker {

    init {
        config.setDirectoryRootChecker(this)
    }

    enum class DirectoryType {
        ROOT, MEDIUM, DATE
    }

    private var curPath = AtomicReference<String>(getRootPath())

    fun movePath(path: String) {
        updatePath(path)
    }

    fun moveRoot() {
        curPath.getAndSet(getRootPath())
    }

    private fun updatePath(path: String) {
        curPath.getAndSet(path)
    }

    override fun isRoot() = curPath.get() == getRootPath()

    open fun getRootPath() = Environment.getExternalStorageDirectory().absolutePath

    fun getCurrentPath() = curPath.get()

    fun map(items: List<Medium>): List<Basic> {
        return if (isRoot()) allInOne(DirectoryType.ROOT, items)
        else allInOne(DirectoryType.MEDIUM, items)
    }

    private fun allInOne(type: DirectoryType, items: List<Medium>): List<Basic> {
        return mappers.getValue(type).allInOne(
                getCurrentPath(),
                imageVideoGifFilter,
                config.filterdType,
                config.sortOptionType,
                items
        )
    }
}