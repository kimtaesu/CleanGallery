package com.hucet.clean.gallery.gallery.filter

import org.amshove.kluent.`should be in`
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek

/**
 * Created by taesu on 2017-11-26.
 */
class OrchestraFilterOrderTest : SubjectSpek<OrchestraFilter>({
    val filters = setOf(
            HiddenFileFilter(),
            HiddenFileFilter(),
            HiddenFileFilter(),
            HiddenFileFilter(),
            HiddenFileFilter(),
            ImageVideoGifFilter(),
            HiddenFileFilter()
    )

    given("OrchestraFilter")
    {
        subject {
            OrchestraFilter(filters)
        }
        val correctFilterNames = setOf(
                ImageVideoGifFilter::class
        )
        on("ordering filter")
        {
            it("first ImageVideoGifFilter")
            {
                println(correctFilterNames)
                subject.iterator().first()::class `should be in` correctFilterNames
            }
        }
    }
})