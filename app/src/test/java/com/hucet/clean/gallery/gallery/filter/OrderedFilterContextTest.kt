package com.hucet.clean.gallery.gallery.filter

import org.amshove.kluent.`should be in`
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.subject.SubjectSpek

/**
 * Created by taesu on 2017-11-15.
 */
class OrderedFilterContextTest : SubjectSpek<OrchestraFilter>({
    val filters = setOf(
            HiddenFileFilter(),
            HiddenFileFilter(),
            HiddenFileFilter(),
            HiddenFileFilter(),
            HiddenFileFilter(),
            ImageVideoGifFilter(),
            HiddenFileFilter()
    )

    val correctFilterNames = setOf(
            ImageVideoGifFilter::class
    )

    subject {
        OrchestraFilter(filters)
    }

    describe("")
    {
        it("")
        {
            subject.iterator().first()::class `should be in` correctFilterNames
        }
    }
})
