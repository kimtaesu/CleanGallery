package com.hucet.clean.gallery.extension

import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.config.ReadOnlyConfigs
import com.hucet.clean.gallery.gallery.mapper.DialogRadioItemMapper
import com.hucet.clean.gallery.gallery.sort.SortOptions
import com.hucet.clean.gallery.model.DialogRadioItem


/**
 * Created by taesu on 2017-11-16.
 */
fun AlertDialog.Builder.createSortDialog(readOnlyConfigs: ReadOnlyConfigs, isRoot: Boolean, callback: (SortOptions) -> Unit): MaterialDialog {
    val mapper = DialogRadioItemMapper.SortMapper()
    val v = LayoutInflater.from(context).inflate(R.layout.dialog_sorting, null)
    val items = mapper.map(context, readOnlyConfigs, isRoot)
    val sortItems = items[SortOptions.SORT_TYPE.KEY]!!
    val orderItems = items[SortOptions.ORDER_BY.KEY]!!

    val sortGroup = v.findViewById<RadioGroup>(R.id.group_sort)
    addRadioChilden(sortGroup, sortItems)

    val orderGroup = v.findViewById<RadioGroup>(R.id.group_order)
    addRadioChilden(orderGroup, orderItems)

    return MaterialDialog.Builder(this.context)
            .title(R.string.dialog_sort_title)
            .customView(v, true)
            .positiveText(android.R.string.ok)
            .onPositive { dialog, which ->
                val sortType = SortOptions.getFromSortBit(sortGroup.getCheckedItemIndex(sortItems) or orderGroup.getCheckedItemIndex(orderItems))
                callback.invoke(sortType)
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


fun AlertDialog.Builder.createFilterDialog(readOnlyConfigs: ReadOnlyConfigs, fp: (Long) -> Unit): MaterialDialog {
    val mapper = DialogRadioItemMapper.FilterMapper()
    val resultMap = mapper.map(context, readOnlyConfigs, false)
    val listItem = resultMap.values.flatMap { it }
    val callback = MaterialDialog.ListCallbackMultiChoice { _, which, _ ->
        val filterType = which.sumBy { which ->
            listItem
                    .first { it.index == which }
                    .bitAtt.toInt()
        }
        fp.invoke(filterType.toLong())
        true
    }

    val checkedIndexs = listItem.filter { it.isCheck }.map { it.index }.toTypedArray()
    return MaterialDialog.Builder(this.context)
            .title(R.string.dialog_filter_title)
            .items(listItem.map { it.title })
            .itemsCallbackMultiChoice(checkedIndexs, callback)
            .positiveText(android.R.string.ok)
            .build()
}

