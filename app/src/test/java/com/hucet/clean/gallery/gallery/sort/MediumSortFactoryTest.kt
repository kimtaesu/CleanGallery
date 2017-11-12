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
    fun `Strategy NAME ASC 검증 `() {
        val option = MediaSortOptions(MediaSortOptions.STRATEGY.NAME, MediaSortOptions.ORDER.ASC)
        sortTest(option, "test_sort_name_asc.json", "media/correct")
    }

    @Test
    fun `Strategy NAME DESC 검증 `() {
        val option = MediaSortOptions(MediaSortOptions.STRATEGY.NAME, MediaSortOptions.ORDER.DESC)
        sortTest(option, "test_sort_name_desc.json", "media/correct")
    }

    @Test
    fun `Strategy MODIFIED ASC 검증`() {
        val option = MediaSortOptions(MediaSortOptions.STRATEGY.MODIFIED, MediaSortOptions.ORDER.ASC)
        sortTest(option, "test_sort_modified_asc.json", "media/correct")
    }

    @Test
    fun `Strategy MODIFIED DESC 검증`() {
        val option = MediaSortOptions(MediaSortOptions.STRATEGY.MODIFIED, MediaSortOptions.ORDER.DESC)
        sortTest(option, "test_sort_modified_desc.json", "media/correct")
    }

    @Test
    fun `Strategy PATH ASC 검증`() {
        val option = MediaSortOptions(MediaSortOptions.STRATEGY.PATH, MediaSortOptions.ORDER.ASC)
        sortTest(option, "test_sort_path_asc.json", "media/correct")
    }

    @Test
    fun `Strategy PATH DESC 검증`() {
        val option = MediaSortOptions(MediaSortOptions.STRATEGY.PATH, MediaSortOptions.ORDER.DESC)
        sortTest(option, "test_sort_path_desc.json", "media/correct")
    }

    @Test
    fun `Strategy SIZE ASC 검증`() {
        val option = MediaSortOptions(MediaSortOptions.STRATEGY.SIZE, MediaSortOptions.ORDER.ASC)
        sortTest(option, "test_sort_size_asc.json", "media/correct")
    }

    @Test
    fun `Strategy SIZE DESC 검증`() {
        val option = MediaSortOptions(MediaSortOptions.STRATEGY.SIZE, MediaSortOptions.ORDER.DESC)
        sortTest(option, "test_sort_size_desc.json", "media/correct")
    }

    @Test
    fun `Strategy TAKEN ASC 검증`() {
        val option = MediaSortOptions(MediaSortOptions.STRATEGY.TAKEN, MediaSortOptions.ORDER.ASC)
        sortTest(option, "test_sort_taken_asc.json", "media/correct")
    }

    @Test
    fun `Strategy TAKEN DESC 검증`() {
        val option = MediaSortOptions(MediaSortOptions.STRATEGY.TAKEN, MediaSortOptions.ORDER.DESC)
        sortTest(option, "test_sort_taken_desc.json", "media/correct")
    }

    private fun sortTest(option: MediaSortOptions, path: String, parent: String) {
        var correctData: List<Medium> = DeserializerFixture.deserializeMedium(path, parent)
        val comparator = MediumSortFactory.createComparator(option, option.order)
        Collections.sort(testData, comparator)
        println(Gson().toJson(testData))
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