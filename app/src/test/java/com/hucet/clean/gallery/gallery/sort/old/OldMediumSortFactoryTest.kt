package com.hucet.clean.gallery.gallery.sort.old

import com.google.gson.Gson
import com.hucet.clean.gallery.fixture.DeserializerFixture
import com.hucet.clean.gallery.model.Medium
import org.hamcrest.core.Is.*
import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Test
import java.util.*

/**
 * Created by taesu on 2017-11-12.
 */
class OldMediumSortFactoryTest {
    val testData = DeserializerFixture.deserializeMedium("test_sort.json", "media/test")

    @Ignore
    @Test
    fun `Strategy NAME ASC 검증 `() {
        val option = OldMediaSortOptions(OldMediaSortOptions.STRATEGY.NAME, OldMediaSortOptions.ORDER.ASC)
        sortTest(option, "test_sort_name_asc.json", "media/correct")
    }
    @Ignore
    @Test
    fun `Strategy NAME DESC 검증 `() {
        val option = OldMediaSortOptions(OldMediaSortOptions.STRATEGY.NAME, OldMediaSortOptions.ORDER.DESC)
        sortTest(option, "test_sort_name_desc.json", "media/correct")
    }
    @Ignore
    @Test
    fun `Strategy MODIFIED ASC 검증`() {
        val option = OldMediaSortOptions(OldMediaSortOptions.STRATEGY.MODIFIED, OldMediaSortOptions.ORDER.ASC)
        sortTest(option, "test_sort_modified_asc.json", "media/correct")
    }
    @Ignore
    @Test
    fun `Strategy MODIFIED DESC 검증`() {
        val option = OldMediaSortOptions(OldMediaSortOptions.STRATEGY.MODIFIED, OldMediaSortOptions.ORDER.DESC)
        sortTest(option, "test_sort_modified_desc.json", "media/correct")
    }
    @Ignore
    @Test
    fun `Strategy PATH ASC 검증`() {
        val option = OldMediaSortOptions(OldMediaSortOptions.STRATEGY.PATH, OldMediaSortOptions.ORDER.ASC)
        sortTest(option, "test_sort_path_asc.json", "media/correct")
    }
    @Ignore
    @Test
    fun `Strategy PATH DESC 검증`() {
        val option = OldMediaSortOptions(OldMediaSortOptions.STRATEGY.PATH, OldMediaSortOptions.ORDER.DESC)
        sortTest(option, "test_sort_path_desc.json", "media/correct")
    }
    @Ignore
    @Test
    fun `Strategy SIZE ASC 검증`() {
        val option = OldMediaSortOptions(OldMediaSortOptions.STRATEGY.SIZE, OldMediaSortOptions.ORDER.ASC)
        sortTest(option, "test_sort_size_asc.json", "media/correct")
    }
    @Ignore
    @Test
    fun `Strategy SIZE DESC 검증`() {
        val option = OldMediaSortOptions(OldMediaSortOptions.STRATEGY.SIZE, OldMediaSortOptions.ORDER.DESC)
        sortTest(option, "test_sort_size_desc.json", "media/correct")
    }
    @Ignore
    @Test
    fun `Strategy TAKEN ASC 검증`() {
        val option = OldMediaSortOptions(OldMediaSortOptions.STRATEGY.TAKEN, OldMediaSortOptions.ORDER.ASC)
        sortTest(option, "test_sort_taken_asc.json", "media/correct")
    }
    @Ignore
    @Test
    fun `Strategy TAKEN DESC 검증`() {
        val option = OldMediaSortOptions(OldMediaSortOptions.STRATEGY.TAKEN, OldMediaSortOptions.ORDER.DESC)
        sortTest(option, "test_sort_taken_desc.json", "media/correct")
    }

    private fun sortTest(option: OldMediaSortOptions, path: String, parent: String) {
        var correctData: List<Medium> = DeserializerFixture.deserializeMedium(path, parent)
        val comparator = OldMediumSortFactory.createComparator(option, option.order)
        Collections.sort(testData, comparator)
        when (option.strategy) {
            OldMediaSortOptions.STRATEGY.NAME -> {
                testData.forEachIndexed { index, medium ->
                    assertThat(medium.name, `is`(correctData[index].name))
                }
            }
            OldMediaSortOptions.STRATEGY.MODIFIED -> {
                testData.forEachIndexed { index, medium ->
                    assertThat(medium.modified, `is`(correctData[index].modified))
                }
            }
            OldMediaSortOptions.STRATEGY.TAKEN -> {
                testData.forEachIndexed { index, medium ->
                    assertThat(medium.taken, `is`(correctData[index].taken))
                }
            }

            OldMediaSortOptions.STRATEGY.SIZE -> {
                testData.forEachIndexed { index, medium ->
                    assertThat(medium.size, `is`(correctData[index].size))
                }
            }
            OldMediaSortOptions.STRATEGY.PATH -> {
                testData.forEachIndexed { index, medium ->
                    assertThat(medium.path, `is`(correctData[index].path))
                }
            }
        }
    }
}