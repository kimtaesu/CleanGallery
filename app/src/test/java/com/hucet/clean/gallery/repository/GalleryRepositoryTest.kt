package com.hucet.clean.gallery.repository

import com.hucet.clean.gallery.datasource.local.LocalDataSource
import com.hucet.clean.gallery.fixture.MediumFixture
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
    given("a galleryRepository")
    {
        val test = MediumFixture.DEFAULT
        val mockLocalDataSource = mock<LocalDataSource>()
        subject {
            GalleryRepository(mockLocalDataSource)
        }
        on("a getGalleries subscriber")
        {
            whenever(mockLocalDataSource.getGalleries(any())).thenReturn(Flowable.just(test))
            val testSubscriber = subject.getGalleries("").test()
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