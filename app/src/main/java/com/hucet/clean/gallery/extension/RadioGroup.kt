package com.hucet.clean.gallery.extension

import android.widget.RadioGroup
import com.hucet.clean.gallery.model.DialogRadioItem

/**
 * Created by taesu on 2017-11-19.
 */

fun RadioGroup.getCheckedItemIndex(radioItems: List<DialogRadioItem>): Int {
    val checkedSortItem = radioItems.first {
        it.index == this.checkedRadioButtonId
    }

    return checkedSortItem.bitAtt
}