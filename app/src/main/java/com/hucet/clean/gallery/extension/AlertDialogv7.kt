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
import com.hucet.clean.gallery.gallery.mapper.DialogRadioItemMapper
import com.hucet.clean.gallery.gallery.sort.ByOrder
import com.hucet.clean.gallery.gallery.sort.SortOptionType
import com.hucet.clean.gallery.model.DialogRadioItem


/**
 * Created by taesu on 2017-11-16.
 */
fun AlertDialog.Builder.createSortDialog(config: ApplicationConfig, callback: (SortOptionType) -> Unit): MaterialDialog {
    val mapper = DialogRadioItemMapper()
    val v = LayoutInflater.from(context).inflate(R.layout.dialog_sorting, null)
    val items = mapper.map(context, config)
    val sortItems = items[SortOptionType.KEY]
    val orderItems = items[ByOrder.KEY]

    val sortGroup = v.findViewById<RadioGroup>(R.id.group_sort)
    addRadioChilden(sortGroup, sortItems)

    val orderGroup = v.findViewById<RadioGroup>(R.id.group_order)
    addRadioChilden(orderGroup, orderItems)

    return MaterialDialog.Builder(this.context)
            .title(R.string.dialog_sort_title)
            .customView(v, true)
            .positiveText(android.R.string.ok)
            .onPositive { dialog, which ->
                callback.invoke(sortGroup.createSortOptionType(sortItems, orderItems))
            }
            .build()
}

private fun addRadioChilden(radioGroup: RadioGroup, radioItems: List<DialogRadioItem>?) {
    radioItems?.forEachIndexed { index, item ->
        val radioButton = RadioButton(radioGroup.context)
        radioButton.text = item.title
        radioButton.id = item.index
        radioGroup.addView(radioButton)
        radioButton.isChecked = item.isCheck
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
            .items(filterConfigMap.values.map { it.title })
            .itemsCallbackMultiChoice(getFilterCheckArray(filterConfigMap), callback)
            .positiveText(android.R.string.ok)
            .build()
}

private fun getFilterMap(context: Context, configFilter: Int): Map<Int, DialogRadioItem> {
    return FilterType.values().mapIndexed { index, filterType ->
        index to DialogRadioItem(index, filterType.getString(context), filterType.isCheck(configFilter), filterType.bitAtt)
    }.toMap()
}

private fun getFilterCheckArray(filterConfigMap: Map<Int, DialogRadioItem>): Array<Int> {
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


