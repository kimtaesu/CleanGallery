package com.hucet.clean.gallery.datasource.local

import android.content.Context
import com.hucet.clean.gallery.fixture.CursorFixture
import com.hucet.clean.gallery.fixture.DeserializerFixture
import com.hucet.clean.gallery.fixture.MediumFixture
import com.hucet.clean.gallery.model.Medium
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.`should equal`
import org.amshove.kluent.shouldEqualTo
import org.hamcrest.core.Is
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.File

/**
 * Created by taesu on 2017-11-13.
 */
class NoMediaFolderProviderTest : SubjectSpek<NoMediaFolderProvider>({

    given("a noMediaFolderProvider")
    {
        subject {
            NoMediaFolderProvider(mock<Context>())
        }
        on("noMediaFolderProvider 검증")
        {
            val (test, correct) = MediumFixture.TEST_NO_MEDIA
            val cursor = CursorFixture.getCursor(test)
            subject.parseCursor(cursor, ::testAddingifExsist)
            it("test == correct")
            {
                test `should equal` correct
            }
        }
    }

})

fun testAddingifExsist(noMediaList: HashSet<String>, path: String) {
    noMediaList.add(path)
}