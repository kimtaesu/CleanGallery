package com.hucet.clean.gallery.gallery

import com.hucet.clean.gallery.fixture.FakeMedium
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.presenter.Gallery
import com.hucet.clean.gallery.presenter.GalleryPresenter
import com.hucet.clean.gallery.repository.GalleryRepository
import com.hucet.clean.gallery.scheduler.TestSchedulerProvider
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Flowable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.exceptions.base.MockitoException
import java.util.concurrent.TimeUnit

/**
 * Created by taesu on 2017-11-01.
 */
class GalleryPresenterTest {

    @Mock lateinit var view: Gallery.View
    @Mock lateinit var adapter: GalleryAdapter
    @Mock lateinit var repository: GalleryRepository
    @Mock lateinit var presenter: GalleryPresenter
    var testScheduler = TestScheduler()
    val testData = FakeMedium.deserializeResource("test_default.json", "media/test")
    @Before
    fun setUp() {
        view = mock()
        adapter = mock()
        repository = mock()
        testScheduler = TestScheduler()
        presenter = GalleryPresenter(view, adapter, repository, TestSchedulerProvider(testScheduler))
    }

    @Test
    fun `presenter Next Complete 검증`() {
        whenever(repository.getGalleries(any())).thenReturn(Flowable.just(testData))

        presenter.fetchItems("")

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        verify(repository, times(1)).getGalleries(any())

        verify(adapter, times(1)).updateData(any())

        verify(view, times(1)).showProgress()
        verify(view, times(1)).hideProgress()
    }

    @Test
    fun `presenter Error 검증`() {
        whenever(repository.getGalleries(any())).thenReturn(Flowable.just(testData)
                .map {
                    throw MockitoException("")
                })

        presenter.fetchItems("")

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        verify(repository, times(1)).getGalleries(any())

        verify(adapter, never()).updateData(any())

        verify(view, times(1)).showProgress()
        verify(view, times(1)).hideProgress()
        verify(view, times(1)).showError()
    }

}