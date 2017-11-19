package com.hucet.clean.gallery.gallery.mapper

import android.content.Context
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.config.SORT_BY_DATE_MODIFIED
import com.hucet.clean.gallery.config.SORT_DESCENDING
import com.hucet.clean.gallery.fixture.DialogRadioItemFixture
import com.hucet.clean.gallery.fixture.ReadOnlyConfigsFixture
import com.hucet.clean.gallery.fixture.TestDialogItem
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.sort.ByOrder
import com.hucet.clean.gallery.gallery.sort.ByOrder.*
import com.hucet.clean.gallery.gallery.sort.SortOptionType
import com.hucet.clean.gallery.gallery.sort.SortOptionType.*
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
class DialogListItemMapperTest : SubjectSpek<DialogRadioItemMapper>({
    given("a dialogListItemMapper")
    {
        subject {
            DialogRadioItemMapper()
        }
        on("directory check item")
        {
            val sortOptionType = BY_PATH order BY_DESC
            val (sort, order) = DialogRadioItemFixture.getCorrectFromCheckItems(sortOptionType)
            val (checkSort, checkOrder) = getTestDialogItem(subject, sortOptionType)

            it("sortOption must equal sort")
            {
                checkSort `should equal` sort
            }
            it("desc sortOption must equal order")
            {
                checkOrder `should equal` order
            }
        }
        on("directory check item")
        {
            val sortOptionType = BY_PATH order BY_ASC
            val (sort, order) = DialogRadioItemFixture.getCorrectFromCheckItems(sortOptionType)
            val (checkSort, checkOrder) = getTestDialogItem(subject, sortOptionType)

            it("sortOption must equal sort")
            {
                checkSort `should equal` sort
            }
            it("asc sortOption must equal order")
            {
                checkOrder `should equal` order
            }
        }

        on("date check item")
        {
            val sortOptionType = BY_DAILY order BY_DESC
            val (sort, order) = DialogRadioItemFixture.getCorrectFromCheckItems(sortOptionType)
            val (checkSort, checkOrder) = getTestDialogItem(subject, sortOptionType)

            it("sortOption must equal sort")
            {
                checkSort `should equal` sort
            }
            it("desc sortOption must equal order")
            {
                checkOrder `should equal` order
            }
        }
        on("date check item")
        {
            val sortOptionType = BY_MONTHLY order BY_ASC
            val (sort, order) = DialogRadioItemFixture.getCorrectFromCheckItems(sortOptionType)
            val (checkSort, checkOrder) = getTestDialogItem(subject, sortOptionType)

            it("sortOption must equal sort")
            {
                checkSort `should equal` sort
            }
            it("asc sortOption must equal order")
            {
                checkOrder `should equal` order
            }
        }
    }
})

private fun getTestDialogItem(subject: DialogRadioItemMapper, sortOptionType: SortOptionType): TestDialogItem {

    val dialogItems = if (SortOptionType.isDateType(sortOptionType)) {
        subject.map(mockContext(), ReadOnlyConfigsFixture.readOnlyConfigs(CategoryMode.DATE,
                sortOptionType = sortOptionType))

    } else {
        subject.map(mockContext(), ReadOnlyConfigsFixture.readOnlyConfigs(CategoryMode.DIRECTORY,
                sortOptionType = sortOptionType))

    }
    val checkedSortOption = dialogItems[SortOptionType.KEY]?.find { it.isCheck }
    val checkedOrder = dialogItems[ByOrder.KEY]?.find { it.isCheck }
    return TestDialogItem(checkedSortOption!!, checkedOrder!!)
}

fun mockContext(): Context {
    val mock = mock<Context>()
    whenever(mock.getString(any())).thenReturn("")
    return mock
}
