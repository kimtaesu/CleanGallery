package com.hucet.clean.gallery.gallery.directory

import com.hucet.clean.gallery.model.Basic

/**
 * Created by taesu on 2017-11-26.
 */

sealed class DirectoryState {
    abstract fun map(): List<Basic>
    abstract fun filter(): List<Basic>
    abstract fun sort(): List<Basic>
    abstract fun aggregate(): List<Basic>
    fun allInOne(items: List<Basic>): List<Basic> {

    }

    class DirectoryMedium : DirectoryState() {
        override fun map(): List<Basic> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun filter(): List<Basic> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun sort(): List<Basic> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun aggregate(): List<Basic> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    class DirecotryRoot : DirectoryState() {
        override fun map(): List<Basic> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun filter(): List<Basic> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun sort(): List<Basic> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun aggregate(): List<Basic> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}

