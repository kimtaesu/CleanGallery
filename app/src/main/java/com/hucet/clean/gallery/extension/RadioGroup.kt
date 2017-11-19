package com.hucet.clean.gallery.extension

import android.widget.RadioGroup
import com.hucet.clean.gallery.gallery.sort.SortOptionType
import com.hucet.clean.gallery.model.DialogRadioItem

/**
 * Created by taesu on 2017-11-19.
 */
fun RadioGroup.createSortOptionType(sortRadioItems: List<DialogRadioItem>?, orderRadioItems: List<DialogRadioItem>?): SortOptionType {
    val checkedSortItem = sortRadioItems?.first {
        it.index == this.checkedRadioButtonId
    }

    val checkedOrderItem = orderRadioItems?.first {
        it.index == this.checkedRadioButtonId
    }
    val sortType = SortOptionType.get(checkedSortItem?.bitAtt!!)
    return sortType order checkedOrderItem?.bitAtt!!
}