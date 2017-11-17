package com.hucet.clean.gallery.extension

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.config.GIFS
import com.hucet.clean.gallery.config.IMAGES
import com.hucet.clean.gallery.config.VIDEOS
import com.hucet.clean.gallery.gallery.category.CategoryMode


/**
 * Created by taesu on 2017-11-16.
 */
fun AlertDialog.Builder.createSortDialog(config: ApplicationConfig): MaterialDialog {
    val v = LayoutInflater.from(context).inflate(R.layout.dialog_sorting, null)
    val sortGroup = v.findViewById<RadioGroup>(R.id.sorting_dialog_sort_group)
    val items = getSortItems(context, config)
    addRadioChilden(sortGroup, items)

    return MaterialDialog.Builder(this.context)
            .title(R.string.dialog_sort_title)
            .customView(v, true)
            .positiveText(android.R.string.ok)
            .build()
}

private fun addRadioChilden(radioGroup: RadioGroup, items: List<DialogItem>) {
    items.forEach {
        val radioButton = RadioButton(radioGroup.context)
        radioButton.setText(it.title)
        radioButton.isChecked = it.isCheck
        radioGroup.addView(radioButton)
    }
}

private fun getSortItems(context: Context, config: ApplicationConfig): List<DialogItem> {
    val items = when (config.categoryMode) {
        CategoryMode.DATE -> {
            context.resources.getStringArray(R.array.array_date_sort_option)
        }
        CategoryMode.DIRECTORY -> {
            context.resources.getStringArray(R.array.array_file_sort_option)
        }
    }
    return emptyList()
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
            .items(filterConfigMap.values.map { it.title })
            .itemsCallbackMultiChoice(getFilterCheckArray(filterConfigMap), callback)
            .positiveText(android.R.string.ok)
            .build()
}

private fun getFilterMap(context: Context, configFilter: Int): Map<Int, DialogItem> {
    return FilterType.values().mapIndexed { index, filterType ->
        index to DialogItem(index, filterType.getString(context), filterType.isCheck(configFilter), filterType.bitAtt)
    }.toMap()
}

private fun getFilterCheckArray(filterConfigMap: Map<Int, DialogItem>): Array<Int> {
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

private data class DialogItem(val index: Int, val title: String, val isCheck: Boolean, val bitAtt: Int)

