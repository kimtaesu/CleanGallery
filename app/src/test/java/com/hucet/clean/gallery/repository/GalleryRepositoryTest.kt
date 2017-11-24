package com.hucet.clean.gallery.repository

import com.hucet.clean.gallery.datasource.local.LocalDataSource
import com.hucet.clean.gallery.fixture.MediumFixture
import com.hucet.clean.gallery.fixture.ReadOnlyConfigsFixture
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.category.MediumTransformer
import com.hucet.clean.gallery.gallery.sort.SortOptions
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
    val tranformer by memoized { mock<MediumTransformer>() }

    val readOnlyConfig = ReadOnlyConfigsFixture.readOnlyConfigs(
            categoryMode = CategoryMode.DATE,
            sortOptionType = SortOptions(SortOptions.SORT_TYPE.DAILY)
    )

    given("a galleryRepository")
    {
        subject {
            GalleryRepository(localDataSource, tranformer)
        }
        on("a getGalleries subscriber")
        {
            whenever(localDataSource.getGalleries(any(), any())).thenReturn(Flowable.just(test))
            whenever(tranformer.transform(any(), any(), any())).thenReturn(test)
            val testSubscriber = subject.getGalleries(readOnlyConfig, "", true, true).test()
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