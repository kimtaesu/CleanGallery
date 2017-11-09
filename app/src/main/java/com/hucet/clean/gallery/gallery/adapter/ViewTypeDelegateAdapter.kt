package com.hucet.clean.gallery.gallery.adapter

import com.hucet.clean.gallery.model.Basic
import javax.inject.Inject

/**
 * Created by taesu on 2017-11-09.
 */
class ViewTypeDelegateAdapter @Inject constructor() {
    //     TODO Dependencies Injection
//    @Inject lateinit var viewHolderDelegater:
//            Map<GalleryAdapter.GalleryType, AbstractDelegateAdapter>
    val viewHolderDelegater = mapOf(
            GalleryAdapter.GalleryType.DIRECTORY to DirectoryDelegateAdapter(),
            GalleryAdapter.GalleryType.MEDIUM to MediumDelegateAdapter()
    )

    operator fun get(itemType: Int): AbstractDelegateAdapter? {
        return when (itemType) {
            GalleryAdapter.GalleryType.DIRECTORY.value -> {
                viewHolderDelegater[GalleryAdapter.GalleryType.DIRECTORY]
            }
            GalleryAdapter.GalleryType.MEDIUM.value -> {
                viewHolderDelegater[GalleryAdapter.GalleryType.MEDIUM]
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }
}