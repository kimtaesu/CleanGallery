package com.hucet.clean.gallery.datasource.local

import com.hucet.clean.gallery.fixture.CursorFixture
import com.hucet.clean.gallery.fixture.MediumFixture
import com.hucet.clean.gallery.model.Medium
import org.amshove.kluent.mock
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek
import org.junit.Assert.*

/**
 * Created by taesu on 2017-11-24.
 */
class LocalDataSourceTest : SubjectSpek<LocalDataSource>({
    val mediaFetcher by memoized { mock<MediaFetcher>() }
    val noMediaFolderProvider by memoized { mock<NoMediaFolderProvider>() }

    given("LocalDataSource")
    {
        subject {
            LocalDataSource(mediaFetcher, noMediaFolderProvider)
        }
        on("") {
            val mediums = subject.getGalleries(true)
            it("") {

            }
        }
    }
})