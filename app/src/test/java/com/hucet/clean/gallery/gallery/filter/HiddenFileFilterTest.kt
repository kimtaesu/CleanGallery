package com.hucet.clean.gallery.gallery.filter

import com.google.gson.Gson
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.fixture.DeserializerFixture
import com.hucet.clean.gallery.model.Medium
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.core.Is
import org.junit.Assert.*
import org.junit.Test

/**
 * Created by taesu on 2017-11-13.
 */
class HiddenFileFilterTest {
    var hiddenFilter = HiddenFileFilter(mockConfig(false))
    val testData = DeserializerFixture.deserializeMedium("test_hidden_filter.json", "media/test")
    val correctData = DeserializerFixture.deserializeMedium("test_hidden_filter.json", "media/correct")
    val noMediaTestData = DeserializerFixture.deserializeMedium("test_hidden_filter_no_media.json", "media/test")
            .map {
                it.path
            }.toSet()

    private fun getTestMedium(path: String): Medium {
        return Medium(1, "", path, 1, 1, 1)
    }

    @Test
    fun `Show Hidden false Filter 검증`() {

        hiddenFilter = HiddenFileFilter(mockConfig(false))
        val result = testData.filter {
            hiddenFilter.filterd(getTestMedium(it.path), noMediaTestData)
        }
        assertThat(result, Is.`is`(correctData))
    }

    @Test
    fun `Show Hidden true Filter 검증`() {
        hiddenFilter = HiddenFileFilter(mockConfig(true))
        val result = testData.filter {
            hiddenFilter.filterd(getTestMedium(it.path), noMediaTestData)
        }
        assertThat(result, Is.`is`(testData))
    }

    private fun mockConfig(showHidden: Boolean): ApplicationConfig {
        val config = mock<ApplicationConfig>()
        whenever(config.showHidden).thenReturn(showHidden)
        return config
    }
}