package com.hucet.clean.gallery.datasource.local

import android.content.Context
import com.hucet.clean.gallery.fixture.CursorFixture
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter
import com.hucet.clean.gallery.resource
import com.nhaarman.mockito_kotlin.mock
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek

/**
 * Created by taesu on 2017-10-31.
 */

class MediaFetcherTest21 : SubjectSpek<MediaFetcher>({
    given("a mediaFetcher") {
        subject {
            val context = mock<Context>()
            val filters = emptySet<MediaTypeFilter>()
            MediaFetcher(context, filters)
        }
        on("test_default.json 검증")
        {
            resource("")
            val cursor = CursorFixture.getCursor("test_default.json", "media/test")
            val items = subject.parseCursor(cursor, emptySet())

            it("items size == 2")
            {
                //                2 `should be` 2

            }
//            it("[3_Forest.jpg,4_Structure.jpg] 포함")
//            {
//                "3_Forest.jpg" `should be in` items
//                "4_Structure.jpg" `should be in` items
//            }
        }
    }
})