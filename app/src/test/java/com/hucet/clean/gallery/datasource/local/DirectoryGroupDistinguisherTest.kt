package com.hucet.clean.gallery.datasource.local

import com.google.gson.Gson
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.convertFileSeperator2Linux
import com.hucet.clean.gallery.fixture.MediumFixture
import com.hucet.clean.gallery.gallery.category.CategoryType
import com.hucet.clean.gallery.model.Medium
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.*
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek
import org.junit.Assert.*
import java.io.File

/**
 * Created by taesu on 2017-11-15.
 */
class DirectoryGroupDistinguisherTest : SubjectSpek<TestDirectoryGroupDistinguisher>({
    given("a testDirectoryGroupDistinguisher")
    {
        subject {
            TestDirectoryGroupDistinguisher(mock())
        }
        on("medium plusAssign at TestDirectoryGroupDistinguisher")
        {
            subject.isDirectoryRoot = true
            val (test, correct) = MediumFixture.TEST_DIRECTORY_GROUP
            test.forEach {
                subject += it
            }

            it("correct should be in subject.getMappedParentPaths()")
            {
                correct.forEach {
                    it.path `should be in` subject.getMappedParentPaths().map {
                        it.convertFileSeperator2Linux()
                    }
                }
            }
        }
    }
})

class TestDirectoryGroupDistinguisher(config: ApplicationConfig) : DirectoryGroupDistinguisher(config) {
    var isDirectoryRoot: Boolean = false
    override fun isDirectoryRoot(curPath: String): Boolean {
        return isDirectoryRoot
    }
}