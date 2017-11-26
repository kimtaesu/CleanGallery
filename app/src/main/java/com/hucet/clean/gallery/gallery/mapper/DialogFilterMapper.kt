package com.hucet.clean.gallery.gallery.mapper

import android.content.Context
import com.hucet.clean.gallery.gallery.filter.FilterType
import com.hucet.clean.gallery.model.DialogRadioItem

/**
 * Created by taesu on 2017-11-27.
 */
class DialogFilterMapper {
    fun map(context: Context, filterBit: Long): Map<String, List<DialogRadioItem>> {
        return mapOf(
                FilterType.KEY to
                        FilterType.values().mapIndexed { index, filterType ->
                            DialogRadioItem(index, context.getString(filterType.stringId),
                                    filterType.isChecked(filterBit), filterType.bitAtt)
                        }
        )
    }
}
