package com.hucet.clean.gallery.gallery.mapper

import android.content.Context
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.fixture.DialogItemFixture
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.sort.ByOrder
import com.hucet.clean.gallery.gallery.sort.ByOrder.*
import com.hucet.clean.gallery.gallery.sort.SortOptionType
import com.hucet.clean.gallery.gallery.sort.SortOptionType.*
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
class DialogListItemMapperTest : SubjectSpek<DialogListItemMapper>({
    given("a dialogListItemMapper")
    {

        subject {
            DialogListItemMapper()
        }
        on("directory check item")
        {
            val test_checked_item = DialogItemFixture.TEST_CHECK_ITEM(BY_MODIFIED)
            val dialogItems = subject.sortItemsMap(mockContext(),
                    mockConfig(SortOptions(BY_MODIFIED, BY_DESC)))
            val result = dialogItems.find {
                it.isCheck
            }
            it("isCheck item must equal test_checked_item")
            {
                result `should equal` test_checked_item
            }
        }
    }
})

fun mockContext(): Context {
    val mock = mock<Context>()
    whenever(mock.getString(any())).thenReturn("")
    return mock
}

fun mockConfig(sortOptions: SortOptions): ApplicationConfig {
    val config = com.nhaarman.mockito_kotlin.mock<ApplicationConfig>()

    if (SortOptionType.isDirecotryType(sortOptions.sortOptionType)) {
        whenever(config.getDirSortType()).thenReturn(sortOptions)
        whenever(config.categoryMode).thenReturn(CategoryMode.DIRECTORY)
    } else {
        whenever(config.getDateSortType()).thenReturn(sortOptions)
        whenever(config.categoryMode).thenReturn(CategoryMode.DATE)
    }

    return config
}