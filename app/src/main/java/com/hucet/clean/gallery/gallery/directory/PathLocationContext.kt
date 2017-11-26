package com.hucet.clean.gallery.gallery.directory

import android.os.Environment
import com.hucet.clean.gallery.config.*
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.filter.ImageVideoGifFilter
import com.hucet.clean.gallery.gallery.view_mode.ViewModeType
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
        private val mappers: Map<MapperType, SubjectMapper<Medium, out Basic>>
) : DirectoryRootChecker, OnConfigObserver {

    init {
        config.setDirectoryRootChecker(this)
    }

    enum class MapperType {
        ROOT, MEDIUM, DATE
    }

    private var curPath = AtomicReference<String>(getRootPath())

    init {
        curPath = AtomicReference<String>(getRootPath())
        onCategoryChanged(config.categoryMode)
    }

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

    fun switchMap(items: List<Medium>): List<Basic> = when (config.categoryMode) {
        CategoryMode.DATE -> {
            allInOne(MapperType.DATE, items)
        }
        CategoryMode.DIRECTORY -> {
            if (isRoot())
                allInOne(MapperType.ROOT, items)
            else
                allInOne(MapperType.MEDIUM, items)
        }
    }

    private fun allInOne(type: MapperType, items: List<Medium>): List<Basic> {
        return mappers.getValue(type).allInOne(
                getCurrentPath(),
                imageVideoGifFilter,
                config.filterdType,
                config.sortOptionType,
                items
        )
    }

    override fun onCategoryChanged(categoryMode: CategoryMode) {
        when (categoryMode) {
            CategoryMode.DATE -> {
                moveRoot()
            }
        }
    }

    override fun onFilterChanged(filterBit: Long) {
    }

    override fun onViewModeChanged(viewModeType: ViewModeType) {
    }
}