package com.hucet.clean.gallery.repository

import com.hucet.clean.gallery.datasource.local.LocalDataSource
import com.hucet.clean.gallery.model.Medium
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

/**
 * Created by taesu on 2017-11-01.
 */
class GalleryRepositoryTest {
    var mockLocalDataSource = mock<LocalDataSource>()
    var repository = GalleryRepository(mockLocalDataSource)

    @Before
    fun setUp() {
        mockLocalDataSource = mock<LocalDataSource>()
        repository = GalleryRepository(mockLocalDataSource)
    }

    @Test
    fun `subscriber State Completed 확인`() {
        whenever(mockLocalDataSource.getGalleries()).thenReturn(Flowable.just(testMediumData()))

        val testSubscriber = repository.getGalleries().test()
        testSubscriber.assertNoErrors()
        testSubscriber.assertComplete()
    }

    @Test
    fun `subscriber assert value`() {
        whenever(mockLocalDataSource.getGalleries()).thenReturn(Flowable.just(testMediumData()))

        val testSubscriber = repository.getGalleries().test()
        testSubscriber.assertComplete()
        testSubscriber.assertValue { data ->
            data.get(0).path == testMediumData().get(0).path
        }
    }
}

fun testMediumData(): List<Medium> {
    return listOf(Medium("tyler", "/tyler", false, System.currentTimeMillis(), System.currentTimeMillis(), 10))
}