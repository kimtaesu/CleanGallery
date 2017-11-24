package com.hucet.clean.gallery.gallery

import com.hucet.clean.gallery.fixture.MediumFixture
import com.hucet.clean.gallery.fixture.ReadOnlyConfigsFixture
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.gallery.category.MediumTransformer
import com.hucet.clean.gallery.gallery.fragment.ListGalleryFragment
import com.hucet.clean.gallery.presenter.Gallery
import com.hucet.clean.gallery.presenter.GalleryPresenter
import com.hucet.clean.gallery.repository.GalleryRepository
import com.hucet.clean.gallery.scheduler.DefaultSchedulerProvider
import com.hucet.clean.gallery.scheduler.SchedulerProvider
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
    given("a galleryPresenter") {
        subject {
            GalleryPresenter(view, fragment, repository, TestSchedulerProvider(testScheduler))
        }
        on("presenter next - complete 검증")
        {
            whenever(fragment.getCurrentAdapter()).thenReturn(adapter)
            whenever(repository.getGalleries(any(), any(), any())).thenReturn(Flowable.just(test))
            subject.fetchItems("", false, ReadOnlyConfigsFixture.readOnlyConfigs(), false)
            testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

            it("call one [repository.getGalleries, adapter.updateData, view, view.showProgress, view.hideProgress]")
            {
                verify(repository, times(1)).getGalleries(any(), any(), any())
                verify(adapter, times(1)).updateData(any())
                verify(view, times(1)).showProgress()
                verify(view, times(1)).hideProgress()
            }
        }
        on("presenter error 검증")
        {
            whenever(fragment.getCurrentAdapter()).thenReturn(adapter)
            whenever(repository.getGalleries(any(), any(), any())).thenReturn(Flowable.just(test)
                    .map {
                        throw MockitoException("")
                    })

            subject.fetchItems("", false, ReadOnlyConfigsFixture.readOnlyConfigs(), false)
            testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

            it("call never [adapter.updateData]")
            {
                verify(repository, times(1)).getGalleries(any(), any(), any())
                verify(adapter, never()).updateData(any())
                verify(view, times(1)).showProgress()
                verify(view, times(1)).hideProgress()
                verify(view, times(1)).showError()
            }
        }
    }
})
