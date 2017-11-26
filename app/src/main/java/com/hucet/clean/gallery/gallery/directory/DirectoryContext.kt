package com.hucet.clean.gallery.gallery.directory

import android.os.Environment
import com.hucet.clean.gallery.gallery.adapter.GalleryType
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Medium
import java.util.concurrent.atomic.AtomicReference

/**
 * Created by taesu on 2017-11-26.
 */

open class DirectoryContext {

    enum class DirectoryType {
        ROOT, MEDIUM
    }

    private val stateMap = mapOf(
            DirectoryType.ROOT to DirectoryState.DirecotryRoot(),
            GalleryType.MEDIUM to DirectoryState.DirectoryMedium()
    )

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

    fun isRoot() = curPath.get() == getRootPath()

    open fun getRootPath() = Environment.getExternalStorageDirectory().absolutePath
    fun getCurrentPath() = curPath.get()

    fun map(flowableItems: List<Medium>): List<Basic> {
        return if (isRoot())
            stateMap.getValue(DirectoryType.ROOT).allInOne(flowableItems)
        else
            stateMap.getValue(DirectoryType.MEDIUM).allInOne(flowableItems)
    }

}