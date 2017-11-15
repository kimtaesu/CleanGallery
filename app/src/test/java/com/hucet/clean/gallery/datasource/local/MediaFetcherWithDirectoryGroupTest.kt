package com.hucet.clean.gallery.datasource.local

import android.content.Context
import com.hucet.clean.gallery.fixture.CursorFixture
import com.hucet.clean.gallery.fixture.MediumFixture
import com.hucet.clean.gallery.model.Medium
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.`should be`
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek

/**
 * Created by taesu on 2017-10-31.
 */


class MediaFetcherWithDirectoryGroupTest : SubjectSpek<MediaFetcher>({
    val distinguisher by memoized {
        DirectoryGroupDistinguisher(mock())
    }

    given("a mediaFetcher") {
        subject {
            MediaFetcher(mock<Context>(), mock(), mock())
        }
        on("MediumFixture.DEFAULT 검증")
        {

            val cursor = CursorFixture.getCursor(MediumFixture.DEFAULT)
            whenever(distinguisher)
            val items = subject.parseCursor(cursor, emptySet(), "")

            it("items size == 2")
            {
                2 `should be` 2
            }
            it("[3_Forest.jpg,4_Structure.jpg] 포함")
            {
                "3_Forest.jpg" `should be` items[0].name
                "4_Structure.jpg" `should be` items[1].name
            }
        }
    }
})
