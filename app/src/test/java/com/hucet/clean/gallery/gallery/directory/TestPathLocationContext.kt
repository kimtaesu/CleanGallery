package com.hucet.clean.gallery.gallery.directory

import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.gallery.filter.ImageVideoGifFilter
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Medium

/**
 * Created by taesu on 2017-11-26.
 */
class TestPathLocationContext(
        private val imageVideoGifFilter: ImageVideoGifFilter,
        private val config: ApplicationConfig,
        private val mappers: Map<DirectoryType, SubjectMapper<Medium, out Basic>>
) : PathLocationContext(imageVideoGifFilter, config, mappers) {
    override fun getRootPath(): String {
        return "Root"
    }
}