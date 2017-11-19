package com.hucet.clean.gallery.gallery.filter

import org.amshove.kluent.`should be in`
import org.amshove.kluent.`should be`
import org.amshove.kluent.mock
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.subject.SubjectSpek
import org.junit.Assert.*

/**
 * Created by taesu on 2017-11-15.
 */
class OrderedFilterContextTest : SubjectSpek<OrderedFilterContext>({
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
        OrderedFilterContext(filters)
    }

    describe("")
    {
        it("")
        {
            subject.iterator().first()::class `should be in` correctFilterNames
        }
    }
})
