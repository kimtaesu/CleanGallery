package com.hucet.clean.gallery.gallery.fragment

import com.hucet.clean.gallery.model.Medium
import java.io.Serializable

/**
 * Created by taesu on 2017-11-03.
 */
interface GalleryListener : Serializable {
    fun onGalleryClicked(medium: Medium)
}