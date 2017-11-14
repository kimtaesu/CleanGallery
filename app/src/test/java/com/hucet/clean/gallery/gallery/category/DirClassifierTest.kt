package com.hucet.clean.gallery.gallery.category

import com.google.gson.Gson
import com.hucet.clean.gallery.fixture.DeserializerFixture
import com.hucet.clean.gallery.fixture.MediumFixture
import com.hucet.clean.gallery.model.Directory
import org.amshove.kluent.`should equal`
import org.hamcrest.core.Is.*
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

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
            var result = subject.classify(test)
            result = convertFileSeperator2Linux(result)

            it("result == correct")
            {
                result `should equal` correct
            }
        }

    }
})

fun convertFileSeperator2Linux(items: List<Directory>): List<Directory> {
    return items.map {
        it.copy(path = it.path.replace("\\", "/"))
    }
}