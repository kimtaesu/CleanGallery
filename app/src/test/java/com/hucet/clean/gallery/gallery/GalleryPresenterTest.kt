package com.hucet.clean.gallery.gallery

import android.support.v7.util.DiffUtil
import com.hucet.clean.gallery.fixture.MediumFixture
import com.hucet.clean.gallery.fixture.ReadOnlyConfigsFixture
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.gallery.fragment.ListGalleryFragment
import com.hucet.clean.gallery.presenter.Gallery
import com.hucet.clean.gallery.presenter.GalleryPresenter
import com.hucet.clean.gallery.repository.GalleryRepository
import com.hucet.clean.gallery.scheduler.TestSchedulerProvider
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Flowable
import io.reactivex.schedulers.TestScheduler
import org.jetbrains.spek.api.dsl.*
import org.jetbrains.spek.subject.SubjectSpek
import org.mockito.exceptions.base.MockitoException
import java.util.concurrent.TimeUnit

/**
 * Created by taesu on 2017-11-01.
 */
class GalleryPresenterTest : SubjectSpek<GalleryPresenter>({
    val test = MediumFixture.DEFAULT
    val view by memoized { mock<Gallery.View>() }
    val adapter by memoized { mock<GalleryAdapter>() }
    val repository by memoized { mock<GalleryRepository>() }
    val testScheduler by memoized { TestScheduler() }
    val fragment by memoized { mock<ListGalleryFragment>() }
    val diffResult by memoized { mock<DiffUtil.DiffResult>() }

    given("a galleryPresenter") {
        subject {
            GalleryPresenter(view, fragment, repository, TestSchedulerProvider(testScheduler))
        }
        on("presenter next - complete 검증")
        {
            whenever(fragment.getCurrentAdapter()).thenReturn(adapter)
            whenever(adapter.calculateDiff(any())).thenReturn(diffResult)
            whenever(repository.getGalleries(any(), any(), any(), any())).thenReturn(Flowable.just(test))
            subject.fetchItems("", false, ReadOnlyConfigsFixture.readOnlyConfigs(), false)
            testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

            it("call one [repository.getGalleries, adapter.updateByDiff, view.showProgress, view.hideProgress]")
            {
                verify(repository, times(1)).getGalleries(any(), any(), any(), any())
                verify(adapter, times(1)).calculateDiff(any())
                verify(adapter, times(1)).updateByDiff(any())
                verify(view, times(1)).showProgress()
                verify(view, times(1)).hideProgress()
            }
        }
        on("presenter error 검증")
        {
            whenever(fragment.getCurrentAdapter()).thenReturn(adapter)
            whenever(repository.getGalleries(any(), any(), any(), any())).thenReturn(Flowable.just(test)
                    .map {
                        throw MockitoException("")
                    })

            subject.fetchItems("", false, ReadOnlyConfigsFixture.readOnlyConfigs(), false)
            testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

            it("call never [adapter.syncUpdateData]")
            {
                verify(repository, times(1)).getGalleries(any(), any(), any(), any())
                verify(adapter, never()).calculateDiff(any())
                verify(adapter, never()).updateByDiff(any())
                verify(view, times(1)).showProgress()
                verify(view, times(1)).hideProgress()
                verify(view, times(1)).showError()
            }
        }
    }
})
