package com.hucet.clean.gallery.gallery.directory

import org.amshove.kluent.`should be`
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek

/**
 * Created by taesu on 2017-11-26.
 */
class DirecotryContextTest : SubjectSpek<TestDirectoryContext>({
    given("DirectoryContext")
    {
        subject {
            TestDirectoryContext()
        }
        on("move root")
        {
            subject.moveRoot()
            it("isRoot true")
            {
                subject.isRoot() `should be` true
            }
        }
        on("move path")
        {
            val testPath = "ABC"
            subject.movePath(testPath)
            it("isRoot true")
            {
                subject.getCurrentPath() `should be` testPath
            }
        }
    }
})