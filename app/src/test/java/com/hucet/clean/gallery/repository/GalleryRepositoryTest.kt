package com.hucet.clean.gallery.repository

import com.hucet.clean.gallery.datasource.local.LocalDataSource
import com.hucet.clean.gallery.fixture.MediumFixture
import com.hucet.clean.gallery.gallery.directory.PathLocationContext
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek

/**
 * Created by taesu on 2017-11-01.
 */
class GalleryRepositoryTest : SubjectSpek<GalleryRepository>({
    val test = MediumFixture.DEFAULT
    val localDataSource = mock<LocalDataSource>()
    val pathLocator by memoized { mock<PathLocationContext>() }
    given("a galleryRepository")
    {
        subject {
            GalleryRepository(localDataSource)
        }
        on("a getGalleries subscriber")
        {
            whenever(localDataSource.getGalleries(any())).thenReturn(Flowable.just(test))
            whenever(pathLocator.map(any())).thenReturn(test)
            val testSubscriber = subject.getGalleries(pathLocator, true).test()
            it("testSubscriber status [no errors, complete]")
            {
                testSubscriber.assertNoErrors()
                testSubscriber.assertComplete()
            }
            it("first id equlas")
            {

                testSubscriber.assertValue { data ->
                    data.first().id == test.first().id
                }
            }
        }
    }
})