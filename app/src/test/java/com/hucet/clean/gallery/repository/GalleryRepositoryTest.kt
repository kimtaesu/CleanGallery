package com.hucet.clean.gallery.repository

import com.hucet.clean.gallery.datasource.local.LocalDataSource
import com.hucet.clean.gallery.fixture.FakeMedium
import com.hucet.clean.gallery.model.Medium
import com.nhaarman.mockito_kotlin.any
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
    val testData = FakeMedium.deserializeResource("default_medium.json")
    @Before
    fun setUp() {
        mockLocalDataSource = mock<LocalDataSource>()
        repository = GalleryRepository(mockLocalDataSource)
    }

    @Test
    fun `subscriber State Completed 확인`() {
        whenever(mockLocalDataSource.getGalleries(any())).thenReturn(Flowable.just(testData))

        val testSubscriber = repository.getGalleries("").test()
        testSubscriber.assertNoErrors()
        testSubscriber.assertComplete()
    }

    @Test
    fun `subscriber assert value`() {
        whenever(mockLocalDataSource.getGalleries(any())).thenReturn(Flowable.just(testData))
        val testSubscriber = repository.getGalleries("").test()
        testSubscriber.assertComplete()
        testSubscriber.assertValue { data ->
            data.first().id == testData.first().id
        }
    }
}