package com.hucet.clean.gallery.fixture

import com.hucet.clean.gallery.config.*
import com.hucet.clean.gallery.gallery.sort.SortOptionType
import com.hucet.clean.gallery.model.DialogItem

/**
 * Created by taesu on 2017-11-14.
 */
object DialogItemFixture {
    private val DEFAULT: List<DialogItem>
        get() {
            return listOf(
                    dialog(
                            index = 0,
                            title = "File name",
                            isCheck = false,
                            bitAttr = SORT_BY_NAME
                    ),
                    dialog(
                            index = 1,
                            title = "Last modified",
                            isCheck = false,
                            bitAttr = SORT_BY_DATE_MODIFIED
                    ),
                    dialog(
                            index = 2,
                            title = "Date taken",
                            isCheck = false,
                            bitAttr = SORT_BY_DATE_TAKEN
                    ),
                    dialog(
                            index = 3,
                            title = "Path",
                            isCheck = false,
                            bitAttr = SORT_BY_PATH
                    ),
                    dialog(
                            index = 4,
                            title = "Size",
                            isCheck = false,
                            bitAttr = SORT_BY_SIZE
                    )
            )
        }

    fun TEST_CHECK_ITEM(checkItem: SortOptionType): DialogItem {
        val items = DEFAULT
        return items.first {
            it.bitAtt and checkItem.bitAttr > 0
        }.copy(isCheck = true, title = "")
    }

}

fun dialog(index: Int = 0,
           title: String = "",
           isCheck: Boolean = false,
           bitAttr: Int = 0
): DialogItem {
    return DialogItem(index, title, isCheck, bitAttr)
}