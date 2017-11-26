package com.hucet.clean.gallery.gallery.category

import com.hucet.clean.gallery.convertFileSeperator2Linux
import com.hucet.clean.gallery.fixture.MediumFixture
import com.hucet.clean.gallery.gallery.sort.SortOptions
import org.amshove.kluent.`should equal`
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.api.dsl.xgiven
import org.jetbrains.spek.subject.SubjectSpek

/**
 * Created by taesu on 2017-11-10.
 */
class DirTransformerTest : SubjectSpek<DirTransformer>({
    xgiven("a dirClassifier")
    {
        subject {
            DirTransformer()
        }
        on("dir transform")
        {
            val (test, correct) = MediumFixture.TEST_CATEGORY_DIR
            var result = subject.transform(SortOptions(SortOptions.SORT_TYPE.NAME, SortOptions.ORDER_BY.DESC), test)

            result = result.map {
                it.copy(path = it.path.convertFileSeperator2Linux())
            }

            it("result == correct")
            {
                result `should equal` correct
            }
        }

    }
})
