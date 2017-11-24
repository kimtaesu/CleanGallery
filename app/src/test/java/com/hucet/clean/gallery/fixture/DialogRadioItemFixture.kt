package com.hucet.clean.gallery.fixture

import com.hucet.clean.gallery.gallery.sort.SortOptions
import com.hucet.clean.gallery.model.DialogRadioItem

/**
 * Created by taesu on 2017-11-14.
 */
object DialogRadioItemFixture {
    private val TEST_DIRECTORY: Map<String, List<DialogRadioItem>>
        get() {
            return mapOf(
                    SortOptions.SORT_TYPE.KEY to
                            SortOptions.SORT_TYPE.MEDIUM_TYPES.mapIndexed { index, sort ->
                                dialog(
                                        index = index,
                                        title = sort.name,
                                        isCheck = false,
                                        bitAttr = sort.bit
                                )
                            },
                    TEST_ORDER
            )
        }


    private val TEST_DATE: Map<String, List<DialogRadioItem>>
        get() {
            return mapOf(
                    SortOptions.SORT_TYPE.KEY to
                            SortOptions.SORT_TYPE.DATE_TYPES
                                    .mapIndexed { index, sort ->
                                        dialog(
                                                index = index,
                                                title = sort.name,
                                                isCheck = false,
                                                bitAttr = sort.bit
                                        )
                                    },
                    TEST_ORDER)
        }

    private val TEST_ORDER = SortOptions.ORDER_BY.KEY to
            SortOptions.ORDER_BY.values().mapIndexed { index, order ->
                dialog(
                        index = index,
                        title = order.name,
                        isCheck = false,
                        bitAttr = order.bit
                )
            }

    fun getCorrectFromCheckItems(checkedSortItem: SortOptions): TestDialogItem {
        val items = if (checkedSortItem.sort.isDateType())
            TEST_DATE
        else
            TEST_DIRECTORY

        return TestDialogItem(
                items[SortOptions.SORT_TYPE.KEY]?.first {
                    it.bitAtt and checkedSortItem.sort.bit > 0
                }?.copy(isCheck = true, title = "")!!,

                items[SortOptions.ORDER_BY.KEY]?.first {
                    it.bitAtt and checkedSortItem.orderBy.bit > 0
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
           bitAttr: Long = 0
): DialogRadioItem {
    return DialogRadioItem(index, title, isCheck, bitAttr)
}