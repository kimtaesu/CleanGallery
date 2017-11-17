package com.hucet.clean.gallery.extension

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.gallery.category.CategoryMode
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.RadioGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.hucet.clean.gallery.config.GIFS
import com.hucet.clean.gallery.config.IMAGES
import com.hucet.clean.gallery.config.VIDEOS


/**
 * Created by taesu on 2017-11-16.
 */
fun AlertDialog.Builder.createSortDialog(config: ApplicationConfig): MaterialDialog {
    val v = LayoutInflater.from(context).inflate(R.layout.dialog_sorting, null)
    val sortGroup = v.findViewById<RadioGroup>(R.id.sorting_dialog_sort_group)

    val items = getSortItems(context, config.categoryMode)

    val callback = MaterialDialog.ListCallbackSingleChoice { dialog, itemView, which, text ->
        true
    }

    return MaterialDialog.Builder(this.context)
            .title(R.string.dialog_sort_title)
            .items(R.array.array_date_sort_option)
            .build()
}

private fun getSortItems(context: Context, categoryMode: CategoryMode) {
    val items = when (categoryMode) {
        CategoryMode.DATE -> {
            context.resources.getStringArray(R.array.array_date_sort_option)
        }
        CategoryMode.DIRECTORY -> {
            context.resources.getStringArray(R.array.array_file_sort_option)
        }
    }
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
            .build()
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

