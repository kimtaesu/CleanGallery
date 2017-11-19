package com.hucet.clean.gallery.gallery.mapper

import android.content.Context
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.config.SORT_BY_DATE_MODIFIED
import com.hucet.clean.gallery.config.SORT_DESCENDING
import com.hucet.clean.gallery.fixture.DialogRadioItemFixture
import com.hucet.clean.gallery.fixture.ReadOnlyConfigsFixture
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.sort.ByOrder
import com.hucet.clean.gallery.gallery.sort.ByOrder.*
import com.hucet.clean.gallery.gallery.sort.SortOptionType
import com.hucet.clean.gallery.gallery.sort.SortOptionType.BY_MODIFIED
import com.hucet.clean.gallery.gallery.sort.SortOptionType.BY_PATH
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
            val (sort, order) = DialogRadioItemFixture.getCorrectFromCheckItems(BY_PATH, BY_DESC)
            val dialogItems = subject.map(mockContext(), ReadOnlyConfigsFixture.readOnlyConfigs(CategoryMode.DIRECTORY, sortOptionType = BY_PATH))

            val checkedSortOption = dialogItems[SortOptionType.KEY]?.find { it.isCheck }
            val checkedOrder = dialogItems[ByOrder.KEY]?.find { it.isCheck }
            it("isCheck item must equal test_checked_item")
            {

                checkedSortOption `should equal` sort
                checkedOrder `should equal` order
            }
        }
    }
})

fun mockContext(): Context {
    val mock = mock<Context>()
    whenever(mock.getString(any())).thenReturn("")
    return mock
}