package com.hucet.clean.gallery.datasource.local

import android.content.Context
import com.hucet.clean.gallery.gallery.filter.OrchestraFilter

/**
 * Created by taesu on 2017-11-26.
 */
class TestMediaFetcher(context: Context, orchestraFilter: OrchestraFilter) : MediaFetcher(context, orchestraFilter) {
    override fun getMimetypeFromExtension(fileName: String): String {
        return fileName
    }
}