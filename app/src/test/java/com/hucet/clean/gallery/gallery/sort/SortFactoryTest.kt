package com.hucet.clean.gallery.gallery.sort

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
class SortFactoryTest {
    val testData = DeserializerFixture.deserializeMedium("test_sort.json", "media/test")

    @Ignore
    @Test
    fun `Strategy NAME ASC 검증 `() {
        val option = SortOptions(SortOptions.SORT_TYPE.NAME, SortOptions.ORDER_BY.ASC)
        sortTest(option, "test_sort_name_asc.json", "media/correct")
    }

    @Ignore
    @Test
    fun `Strategy NAME DESC 검증 `() {
        val option = SortOptions(SortOptions.SORT_TYPE.NAME, SortOptions.ORDER_BY.DESC)
        sortTest(option, "test_sort_name_desc.json", "media/correct")
    }

    @Ignore
    @Test
    fun `Strategy MODIFIED ASC 검증`() {
        val option = SortOptions(SortOptions.SORT_TYPE.MODIFIED, SortOptions.ORDER_BY.ASC)
        sortTest(option, "test_sort_modified_asc.json", "media/correct")
    }

    @Ignore
    @Test
    fun `Strategy MODIFIED DESC 검증`() {
        val option = SortOptions(SortOptions.SORT_TYPE.MODIFIED, SortOptions.ORDER_BY.DESC)
        sortTest(option, "test_sort_modified_desc.json", "media/correct")
    }

    @Ignore
    @Test
    fun `Strategy PATH ASC 검증`() {
        val option = SortOptions(SortOptions.SORT_TYPE.PATH, SortOptions.ORDER_BY.ASC)
        sortTest(option, "test_sort_path_asc.json", "media/correct")
    }

    @Ignore
    @Test
    fun `Strategy PATH DESC 검증`() {
        val option = SortOptions(SortOptions.SORT_TYPE.PATH, SortOptions.ORDER_BY.DESC)
        sortTest(option, "test_sort_path_desc.json", "media/correct")
    }

    @Ignore
    @Test
    fun `Strategy SIZE ASC 검증`() {
        val option = SortOptions(SortOptions.SORT_TYPE.SIZE, SortOptions.ORDER_BY.ASC)
        sortTest(option, "test_sort_size_asc.json", "media/correct")
    }

    @Ignore
    @Test
    fun `Strategy SIZE DESC 검증`() {
        val option = SortOptions(SortOptions.SORT_TYPE.SIZE, SortOptions.ORDER_BY.DESC)
        sortTest(option, "test_sort_size_desc.json", "media/correct")
    }

    @Ignore
    @Test
    fun `Strategy TAKEN ASC 검증`() {
        val option = SortOptions(SortOptions.SORT_TYPE.TAKEN, SortOptions.ORDER_BY.ASC)
        sortTest(option, "test_sort_taken_asc.json", "media/correct")
    }

    @Ignore
    @Test
    fun `Strategy TAKEN DESC 검증`() {
        val option = SortOptions(SortOptions.SORT_TYPE.TAKEN, SortOptions.ORDER_BY.DESC)
        sortTest(option, "test_sort_taken_desc.json", "media/correct")
    }

    private fun sortTest(option: SortOptions, path: String, parent: String) {
        var correctData: List<Medium> = DeserializerFixture.deserializeMedium(path, parent)
        val comparator = SortComparatorFactory.createComparator(option)
        Collections.sort(testData, comparator)
        when (option.sort) {
            SortOptions.SORT_TYPE.NAME -> {
                testData.forEachIndexed { index, medium ->
                    assertThat(medium.name, `is`(correctData[index].name))
                }
            }
            SortOptions.SORT_TYPE.MODIFIED -> {
                testData.forEachIndexed { index, medium ->
                    assertThat(medium.modified, `is`(correctData[index].modified))
                }
            }
            SortOptions.SORT_TYPE.TAKEN -> {
                testData.forEachIndexed { index, medium ->
                    assertThat(medium.taken, `is`(correctData[index].taken))
                }
            }

            SortOptions.SORT_TYPE.SIZE -> {
                testData.forEachIndexed { index, medium ->
                    assertThat(medium.size, `is`(correctData[index].size))
                }
            }
            SortOptions.SORT_TYPE.PATH -> {
                testData.forEachIndexed { index, medium ->
                    assertThat(medium.path, `is`(correctData[index].path))
                }
            }
        }
    }
}