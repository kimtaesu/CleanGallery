package com.hucet.clean.gallery.model

import com.hucet.clean.gallery.config.SORT_BY_DATE_MODIFIED
import com.hucet.clean.gallery.config.SORT_BY_NAME
import com.hucet.clean.gallery.config.SORT_BY_SIZE
import com.hucet.clean.gallery.config.SORT_DESCENDING
import com.hucet.clean.gallery.extension.*
import java.io.Serializable

/**
 * Created by taesu on 2017-10-30.
 */

data class Medium(var name: String, var path: String, val video: Boolean, val modified: Long, val taken: Long, val size: Long) : Serializable, Comparable<Medium> {
    companion object {
        private val serialVersionUID = -6553149366975455L
        var sorting: Int = 0
    }

    fun isPng() = path.isPng()

    fun isGif() = path.isGif()

    fun isJpg() = path.endsWith(".jpg", true) || path.endsWith(".jpeg", true)

    fun isImage() = !isGif() && !video

    fun getMimeType() = path.getMimeTypeFromPath()

    override fun compareTo(other: Medium): Int {
        var result: Int
        when {
            sorting and SORT_BY_NAME != 0 -> result = AlphanumericComparator().compare(name.toLowerCase(), other.name.toLowerCase())
            sorting and SORT_BY_SIZE != 0 -> result = when {
                size == other.size -> 0
                size > other.size -> 1
                else -> -1
            }
            sorting and SORT_BY_DATE_MODIFIED != 0 -> result = when {
                modified == other.modified -> 0
                modified > other.modified -> 1
                else -> -1
            }
            else -> result = when {
                taken == other.taken -> 0
                taken > other.taken -> 1
                else -> -1
            }
        }

        if (sorting and SORT_DESCENDING != 0) {
            result *= -1
        }
        return result
    }
}

class AlphanumericComparator {
    fun compare(string1: String, string2: String): Int {
        var thisMarker = 0
        var thatMarker = 0
        val s1Length = string1.length
        val s2Length = string2.length

        while (thisMarker < s1Length && thatMarker < s2Length) {
            val thisChunk = getChunk(string1, s1Length, thisMarker)
            thisMarker += thisChunk.length

            val thatChunk = getChunk(string2, s2Length, thatMarker)
            thatMarker += thatChunk.length

            // If both chunks contain numeric characters, sort them numerically.
            var result: Int
            if (isDigit(thisChunk[0]) && isDigit(thatChunk[0])) {
                // Simple chunk comparison by length.
                val thisChunkLength = thisChunk.length
                result = thisChunkLength - thatChunk.length
                // If equal, the first different number counts.
                if (result == 0) {
                    for (i in 0..thisChunkLength - 1) {
                        result = thisChunk[i] - thatChunk[i]
                        if (result != 0) {
                            return result
                        }
                    }
                }
            } else {
                result = thisChunk.compareTo(thatChunk)
            }

            if (result != 0) {
                return result
            }
        }

        return s1Length - s2Length
    }

    private fun getChunk(string: String, length: Int, marker: Int): String {
        var current = marker
        val chunk = StringBuilder()
        var c = string[current]
        chunk.append(c)
        current++
        if (isDigit(c)) {
            while (current < length) {
                c = string[current]
                if (!isDigit(c)) {
                    break
                }
                chunk.append(c)
                current++
            }
        } else {
            while (current < length) {
                c = string[current]
                if (isDigit(c)) {
                    break
                }
                chunk.append(c)
                current++
            }
        }
        return chunk.toString()
    }

    private fun isDigit(ch: Char): Boolean {
        return ch in '0'..'9'
    }
}

