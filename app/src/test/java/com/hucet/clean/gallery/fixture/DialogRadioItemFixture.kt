package com.hucet.clean.gallery.fixture

import com.hucet.clean.gallery.config.*
import com.hucet.clean.gallery.gallery.sort.ByOrder
import com.hucet.clean.gallery.gallery.sort.SortOptionType
import com.hucet.clean.gallery.model.DialogRadioItem

/**
 * Created by taesu on 2017-11-14.
 */
object DialogRadioItemFixture {
    private val TEST_DIRECTORY: Map<String, List<DialogRadioItem>>
        get() {
            return mapOf(
                    SortOptionType.KEY to
                            listOf(
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
                            ),
                    TEST_ORDER
            )
        }


    private val TEST_DATE: Map<String, List<DialogRadioItem>>
        get() {
            return mapOf(
                    SortOptionType.KEY to
                            listOf(
                                    dialog(
                                            index = 0,
                                            title = "Daily",
                                            isCheck = false,
                                            bitAttr = SORT_BY_DAILY
                                    ),
                                    dialog(
                                            index = 1,
                                            title = "Monthly",
                                            isCheck = false,
                                            bitAttr = SORT_BY_MONTHLY
                                    ),
                                    dialog(
                                            index = 2,
                                            title = "Yearly",
                                            isCheck = false,
                                            bitAttr = SORT_BY_YEARLY
                                    )
                            ),

                    TEST_ORDER


            )
        }

    private val TEST_ORDER = ByOrder.KEY to
            listOf(
                    dialog(
                            index = 0,
                            title = "ASC",
                            isCheck = false,
                            bitAttr = SORT_ASCENDING
                    ),
                    dialog(
                            index = 1,
                            title = "DESC",
                            isCheck = false,
                            bitAttr = SORT_DESCENDING
                    ))


    fun getCorrectFromCheckItems(checkedSortItem: SortOptionType): TestDialogItem {
        val items = if (SortOptionType.isDateType(checkedSortItem))
            TEST_DATE
        else
            TEST_DIRECTORY

        return TestDialogItem(
                items[SortOptionType.KEY]?.first {
                    it.bitAtt and checkedSortItem.bitAttr > 0
                }?.copy(isCheck = true, title = "")!!,

                items[ByOrder.KEY]?.first {
                    it.bitAtt and checkedSortItem.getOrder().bitAttr > 0
                }?.copy(isCheck = true, title = "")!!
        )
    }

}

data class TestDialogItem(
        val checkedSortRadioItem: DialogRadioItem,
        val checkedOrderRadioItem: DialogRadioItem
)


fun dialog(index: Int = 0,
           title: String = "",
           isCheck: Boolean = false,
           bitAttr: Int = 0
): DialogRadioItem {
    return DialogRadioItem(index, title, isCheck, bitAttr)
}