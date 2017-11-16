package com.hucet.clean.gallery.extension

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.gallery.category.CategoryMode
import android.widget.ArrayAdapter
import android.widget.ListView
import com.afollestad.materialdialogs.MaterialDialog
import com.hucet.clean.gallery.config.GIFS
import com.hucet.clean.gallery.config.IMAGES
import com.hucet.clean.gallery.config.VIDEOS


/**
 * Created by taesu on 2017-11-16.
 */
fun AlertDialog.Builder.createSortDialog(config: ApplicationConfig): AlertDialog {
    val v = LayoutInflater.from(context).inflate(R.layout.dialog_sorting, null)
    val items = when (config.categoryMode) {
        CategoryMode.DATE -> {
            this.context.resources.getStringArray(R.array.array_date_sort_option)
        }
        CategoryMode.DIRECTORY -> {
            this.context.resources.getStringArray(R.array.array_file_sort_option)
        }
    }
    val adapter = ArrayAdapter<String>(
            this.context,
            android.R.layout.select_dialog_singlechoice)
    adapter.addAll(items.toList())
    val listView = v.findViewById<ListView>(R.id.list)
    listView.adapter = adapter
    return this.setTitle(R.string.dialog_sort_title)
            .setView(v)
            .create()
}

fun AlertDialog.Builder.createFilterDialog(configFilter: Int, fp: (Int) -> Unit): MaterialDialog {
    val filterConfigMap = getFilterMap(context, configFilter)
    val callback = MaterialDialog.ListCallbackMultiChoice { dialog, which, text ->
        val filterType = which?.sumBy {
            filterConfigMap[it]?.bitAtt!!
        }
        fp.invoke(filterType!!)
        true
    }
    return MaterialDialog.Builder(this.context)
            .title(R.string.dialog_filter_title)
            .items(filterConfigMap.keys)
            .itemsCallbackMultiChoice(getFilterCheckArray(filterConfigMap), callback)
            .positiveText(android.R.string.ok)
            .show()
}

private fun getFilterMap(context: Context, configFilter: Int): Map<Int, FilterContext> {
    return FilterType.values().mapIndexed { index, filterType ->
        index to FilterContext(index, filterType.getString(context), filterType.isCheck(configFilter), filterType.bitAtt)
    }.toMap()
}

private fun getFilterCheckArray(filterConfigMap: Map<Int, FilterContext>): Array<Int> {
    return filterConfigMap.values.filter {
        it.isCheck
    }.map {
        it.index
    }.toTypedArray()
}


private enum class FilterType(private val stringId: Int, val bitAtt: Int) {
    IMAGE(R.string.filter_images, IMAGES), VIDEO(R.string.filter_videos, VIDEOS), GIF(R.string.filter_gifs, GIFS);

    fun getString(context: Context): String {
        return context.getString(stringId)
    }

    fun isCheck(configFilter: Int) = configFilter and bitAtt > 0
}

private data class FilterContext(val index: Int, val title: String, val isCheck: Boolean, val bitAtt: Int)

