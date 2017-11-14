package com.hucet.clean.gallery.datasource.local

import android.content.Context
import com.hucet.clean.gallery.fixture.CursorFixture
import com.hucet.clean.gallery.fixture.MediumFixture
import com.hucet.clean.gallery.gallery.filter.ImageVideoGifFilter
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.`should be in`
import org.amshove.kluent.`should be`
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek

/**
 * Created by taesu on 2017-10-31.
 */


class MediaFetcherTest21 : SubjectSpek<MediaFetcher>({
    given("a mediaFetcher") {

        var filters = HashSet<MediaTypeFilter>()
        subject {
            println("subject")
            val context = mock<Context>()
            MediaFetcher(context, filters)
        }

        on("MediumFixture.DEFAULT 검증")
        {
            val cursor = CursorFixture.getCursor(MediumFixture.DEFAULT)
            val items = subject.parseCursor(cursor, emptySet())

            it("items size == 2")
            {
                2 `should be` 2

            }
            it("[3_Forest.jpg,4_Structure.jpg] 포함")
            {
                "3_Forest.jpg" `should be` items[0].name
                "4_Structure.jpg" `should be` items[1].name
            }

            context("ImageVideoGifFilter")
            {
                filters.add(mock<ImageVideoGifFilter>())
            }
        }

        on("MediaFetcher ImageVideoGifFilter 검증")
        {
        }
    }

})