package com.hucet.clean.gallery.gallery.filter

/**
 * Created by taesu on 2017-11-13.
 */
object MediaTypeHelper {
    private val photoExtensions: Array<String> = arrayOf(".jpg", ".png", ".jpeg", ".bmp", ".webp")
    private val videoExtensions: Array<String> = arrayOf(".mp4", ".mkv", ".webm", ".avi", ".3gp", ".mov", ".m4v", ".3gpp")
    // fast extension checks, not guaranteed to be accurate

    fun isVideo(fileName: String) = videoExtensions.any { fileName.endsWith(it, true) }
    fun isImage(fileName: String) = photoExtensions.any { fileName.endsWith(it, true) }
    fun isGif(fileName: String) = fileName.endsWith(".gif", true)
}