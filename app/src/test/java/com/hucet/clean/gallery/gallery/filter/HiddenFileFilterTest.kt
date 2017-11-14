package com.hucet.clean.gallery.gallery.filter

import com.google.gson.Gson
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.fixture.MediumFixture
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should equal`
import org.hamcrest.core.Is
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.subject.SubjectSpek
import org.junit.Assert.assertThat

/**
 * Created by taesu on 2017-11-13.
 */
class HiddenFileFilterTest : SubjectSpek<HiddenFileFilter>({
    val (test, correct) = MediumFixture.TEST_HIDDEN_FILTER
    val noMediaData = MediumFixture.EXTRA_NO_MEDIA

    describe("a hiddenFileFilter1")
    {
        subject {
            HiddenFileFilter(mockConfig(true))
        }
        context("config showHidden [true] ")
        {
            val result = test.filter {
                !subject.filterd(it, noMediaData)
            }
            it("test == result")
            {
                test  `should equal` result
            }
        }

    }
    describe("a hiddenFileFilter2")
    {
        subject {
            HiddenFileFilter(mockConfig(false))
        }
        context("config showHidden [false] ") {
            val result = test.filter {
                !subject.filterd(it, noMediaData)
            }
            it("correct == result")
            {
                correct `should equal` result
            }
        }

    }

})

fun mockConfig(showHidden: Boolean): ApplicationConfig {
    val config = mock<ApplicationConfig>()
    whenever(config.showHidden).thenReturn(showHidden)
    return config
}