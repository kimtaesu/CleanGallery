package com.hucet.clean.gallery.gallery.directory

import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.fixture.ConfigFixture
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.filter.ImageVideoGifFilter
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Medium
import org.amshove.kluent.`should be`
import org.amshove.kluent.mock
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek

/**
 * Created by taesu on 2017-11-26.
 */
class PathLocatorContextTest : SubjectSpek<TestPathLocationContext>({

    val config = ConfigFixture.mockConfig(categoryMode = CategoryMode.DIRECTORY)
    given("PathLocationContext")
    {
        subject {
            TestPathLocationContext(mock(), config, mock())
        }
        on("move root")
        {
            subject.moveRoot()
            it("isRoot true")
            {
                subject.isRoot() `should be` true
            }
        }
        on("move path")
        {
            val testPath = "ABC"
            subject.movePath(testPath)
            it("isRoot true")
            {
                subject.getCurrentPath() `should be` testPath
            }
        }
    }
})