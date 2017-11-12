package com.hucet.clean.gallery.gallery.sort

import com.google.gson.Gson
import com.hucet.clean.gallery.fixture.DeserializerFixture
import com.hucet.clean.gallery.model.Medium
import org.hamcrest.core.Is.*
import org.junit.Assert.*
import org.junit.Test
import java.util.*

/**
 * Created by taesu on 2017-11-12.
 */
class MediumSortFactoryTest {
    val testData = DeserializerFixture.deserializeMedium("test_sort.json", "media/test")

    @Test
    fun `Strategy NAME 검증`() {
        val option = MediaSortOptions(MediaSortOptions.STRATEGY.NAME, MediaSortOptions.ORDER.ASC)
        sortTest(option, "test_sort_name.json", "media/correct")
    }

    @Test
    fun `Strategy MODIFIED 검증`() {
        val option = MediaSortOptions(MediaSortOptions.STRATEGY.MODIFIED, MediaSortOptions.ORDER.ASC)
        sortTest(option, "test_sort_modified.json", "media/correct")
    }

    @Test
    fun `Strategy PATH 검증`() {
        val option = MediaSortOptions(MediaSortOptions.STRATEGY.PATH, MediaSortOptions.ORDER.ASC)
        sortTest(option, "test_sort_path.json", "media/correct")
    }

    @Test
    fun `Strategy SIZE 검증`() {
        val option = MediaSortOptions(MediaSortOptions.STRATEGY.SIZE, MediaSortOptions.ORDER.ASC)
        sortTest(option, "test_sort_size.json", "media/correct")
    }

    @Test
    fun `Strategy TAKEN 검증`() {
        val option = MediaSortOptions(MediaSortOptions.STRATEGY.TAKEN, MediaSortOptions.ORDER.ASC)
        sortTest(option, "test_sort_taken.json", "media/correct")
    }

    private fun sortTest(option: MediaSortOptions, path: String, parent: String) {
        var correctData: List<Medium> = DeserializerFixture.deserializeMedium(path, parent)
        val comparator = MediumSortFactory.createComparator(option)
        Collections.sort(testData, comparator)
        when (option.strategy) {
            MediaSortOptions.STRATEGY.NAME -> {
                testData.forEachIndexed { index, medium ->
                    assertThat(medium.name, `is`(correctData[index].name))
                }
            }
            MediaSortOptions.STRATEGY.MODIFIED -> {
                testData.forEachIndexed { index, medium ->
                    assertThat(medium.modified, `is`(correctData[index].modified))
                }
            }
            MediaSortOptions.STRATEGY.TAKEN -> {
                testData.forEachIndexed { index, medium ->
                    assertThat(medium.taken, `is`(correctData[index].taken))
                }
            }

            MediaSortOptions.STRATEGY.SIZE -> {
                testData.forEachIndexed { index, medium ->
                    assertThat(medium.size, `is`(correctData[index].size))
                }
            }
            MediaSortOptions.STRATEGY.PATH -> {
                testData.forEachIndexed { index, medium ->
                    assertThat(medium.path, `is`(correctData[index].path))
                }
            }
        }
    }
}