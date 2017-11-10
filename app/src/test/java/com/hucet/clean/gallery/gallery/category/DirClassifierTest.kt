package com.hucet.clean.gallery.gallery.category

import com.google.gson.Gson
import com.hucet.clean.gallery.fixture.DeserializerFixture
import com.hucet.clean.gallery.model.Directory
import org.hamcrest.core.Is
import org.hamcrest.core.Is.*
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Created by taesu on 2017-11-10.
 */
class DirClassifierTest {
    val testData = DeserializerFixture.deserializeMedium("test_category_dir.json", "media/test")
    val correctData = DeserializerFixture.deserializeDirectory("correct_category_dir.json", "media/correct")
    var dirClassifier = DirClassifier()

    @Before
    fun setUp() {
        dirClassifier = DirClassifier()
    }

    @Test
    fun `Dir Category 검증`() {
        var result = dirClassifier.category(testData)
        result = convertFileSeperator2Linux(result)
        assertThat(result == correctData, `is`(true))
    }

    private fun convertFileSeperator2Linux(items: List<Directory>): List<Directory> {
        return items.map {
            it.copy(path = it.path.replace("\\", "/"))
        }
    }
}