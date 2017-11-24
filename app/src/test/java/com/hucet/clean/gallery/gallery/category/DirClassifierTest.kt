package com.hucet.clean.gallery.gallery.category

import com.hucet.clean.gallery.convertFileSeperator2Linux
import com.hucet.clean.gallery.fixture.MediumFixture
import com.hucet.clean.gallery.gallery.sort.SortOptions
import org.amshove.kluent.`should equal`
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek

/**
 * Created by taesu on 2017-11-10.
 */
class DirClassifierTest : SubjectSpek<DirClassifier>({
    given("a dirClassifier")
    {
        subject {
            DirClassifier()
        }
        on("dir classify")
        {
            val (test, correct) = MediumFixture.TEST_CATEGORY_DIR
            var result = subject.classify(SortOptions(SortOptions.SORT_TYPE.NAME, SortOptions.ORDER_BY.DESC), test)

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
