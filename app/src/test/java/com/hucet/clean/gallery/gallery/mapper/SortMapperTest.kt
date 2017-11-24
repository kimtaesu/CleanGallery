package com.hucet.clean.gallery.gallery.mapper

import android.content.Context
import com.hucet.clean.gallery.fixture.DialogRadioItemFixture
import com.hucet.clean.gallery.fixture.ReadOnlyConfigsFixture
import com.hucet.clean.gallery.fixture.TestDialogItem
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.sort.SortOptions
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.`should equal`
import org.amshove.kluent.mock
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek

/**
 * Created by taesu on 2017-11-17.
 */
class SortMapperTest : SubjectSpek<DialogRadioItemMapper>({
    given("a sortMapper")
    {
        subject {
            DialogRadioItemMapper.SortMapper()
        }
        on("directory check item")
        {
            val sortOptionType = SortOptions(SortOptions.SORT_TYPE.PATH, SortOptions.ORDER_BY.DESC)
            val (sort, order) = DialogRadioItemFixture.getCorrectFromCheckItems(sortOptionType)
            val (checkSort, checkOrder) = getTestDialogItem(subject, sortOptionType)

            it("sortOption must equal sort")
            {
                checkSort `should equal` sort
            }
            it("desc sortOption must equal orderBY")
            {
                checkOrder `should equal` order
            }
        }
        on("directory check item")
        {
            val sortOptionType = SortOptions(SortOptions.SORT_TYPE.PATH, SortOptions.ORDER_BY.ASC)
            val (sort, order) = DialogRadioItemFixture.getCorrectFromCheckItems(sortOptionType)
            val (checkSort, checkOrder) = getTestDialogItem(subject, sortOptionType)

            it("sortOption must equal sort")
            {
                checkSort `should equal` sort
            }
            it("asc sortOption must equal orderBY")
            {
                checkOrder `should equal` order
            }
        }

        on("date check item")
        {
            val sortOptionType = SortOptions(SortOptions.SORT_TYPE.DAILY, SortOptions.ORDER_BY.DESC)
            val (sort, order) = DialogRadioItemFixture.getCorrectFromCheckItems(sortOptionType)
            val (checkSort, checkOrder) = getTestDialogItem(subject, sortOptionType)

            it("sortOption must equal sort")
            {
                checkSort `should equal` sort
            }
            it("desc sortOption must equal orderBY")
            {
                checkOrder `should equal` order
            }
        }
        on("date check item")
        {
            val sortOptionType = SortOptions(SortOptions.SORT_TYPE.MONTHLY, SortOptions.ORDER_BY.ASC)
            val (sort, order) = DialogRadioItemFixture.getCorrectFromCheckItems(sortOptionType)
            val (checkSort, checkOrder) = getTestDialogItem(subject, sortOptionType)

            it("sortOption must equal sort")
            {
                checkSort `should equal` sort
            }
            it("asc sortOption must equal orderBY")
            {
                checkOrder `should equal` order
            }
        }

    }
})

private fun getTestDialogItem(subject: DialogRadioItemMapper, sortOptionType: SortOptions): TestDialogItem {

    val dialogItems = if (sortOptionType.sort.isDateType()) {
        subject.map(mockContext(), ReadOnlyConfigsFixture.readOnlyConfigs(CategoryMode.DATE,
                sortOptionType = sortOptionType))

    } else {
        subject.map(mockContext(), ReadOnlyConfigsFixture.readOnlyConfigs(CategoryMode.DIRECTORY,
                sortOptionType = sortOptionType))

    }
    val checkedSortOption = dialogItems[SortOptions.SORT_TYPE.KEY]?.find { it.isCheck }
    val checkedOrder = dialogItems[SortOptions.ORDER_BY.KEY]?.find { it.isCheck }
    return TestDialogItem(checkedSortOption!!, checkedOrder!!)
}

fun mockContext(): Context {
    val mock = mock<Context>()
    whenever(mock.getString(any())).thenReturn("")
    return mock
}
