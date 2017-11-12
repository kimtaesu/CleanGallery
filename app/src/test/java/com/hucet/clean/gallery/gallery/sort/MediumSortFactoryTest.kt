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
        sortTest(MediaSortOptions.STRATEGY.NAME, MediaSortOptions.ORDER.ASC)
    }

    @Test
    fun `Strategy MODIFIED 검증`() {
        sortTest(MediaSortOptions.STRATEGY.MODIFIED, MediaSortOptions.ORDER.ASC)
    }

    @Test
    fun `Strategy PATH 검증`() {
        sortTest(MediaSortOptions.STRATEGY.PATH, MediaSortOptions.ORDER.ASC)
    }

    @Test
    fun `Strategy SIZE 검증`() {
        sortTest(MediaSortOptions.STRATEGY.SIZE, MediaSortOptions.ORDER.ASC)
    }

    @Test
    fun `Strategy TAKEN 검증`() {
        sortTest(MediaSortOptions.STRATEGY.TAKEN, MediaSortOptions.ORDER.ASC)
    }

    private fun sortTest(strategy: MediaSortOptions.STRATEGY, order: MediaSortOptions.ORDER) {
        var correctData: List<Medium>
        var option: MediaSortOptions
        when (strategy) {
            MediaSortOptions.STRATEGY.NAME -> {
                option = MediaSortOptions(MediaSortOptions.STRATEGY.NAME, order)
                correctData = DeserializerFixture.deserializeMedium("test_sort_name.json", "media/correct")
            }
            MediaSortOptions.STRATEGY.MODIFIED -> {
                option = MediaSortOptions(MediaSortOptions.STRATEGY.MODIFIED, order)
                correctData = DeserializerFixture.deserializeMedium("test_sort_modified.json", "media/correct")
            }
            MediaSortOptions.STRATEGY.TAKEN -> {
                option = MediaSortOptions(MediaSortOptions.STRATEGY.TAKEN, order)
                correctData = DeserializerFixture.deserializeMedium("test_sort_taken.json", "media/correct")
            }

            MediaSortOptions.STRATEGY.SIZE -> {
                option = MediaSortOptions(MediaSortOptions.STRATEGY.SIZE, order)
                correctData = DeserializerFixture.deserializeMedium("test_sort_size.json", "media/correct")
            }
            MediaSortOptions.STRATEGY.PATH -> {
                option = MediaSortOptions(MediaSortOptions.STRATEGY.PATH, order)
                correctData = DeserializerFixture.deserializeMedium("test_sort_path.json", "media/correct")
            }
        }
        val comparator = MediumSortFactory.createComparator(option)
        Collections.sort(testData, comparator)
        println(Gson().toJson(testData))
        when (strategy) {
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