package com.hucet.clean.gallery.gallery.adapter

import com.hucet.clean.gallery.inject.scopes.PerFragment
import javax.inject.Inject

/**
 * Created by taesu on 2017-11-09.
 */
@PerFragment
class ViewTypeDelegateAdapter @Inject constructor(val directoryAdapter: DirectoryDelegateAdapter, val mediumAdapter: MediumDelegateAdapter) {
//    @Inject lateinit var directoryAdapter: DirectoryDelegateAdapter
//    @Inject lateinit var mediumAdapter: MediumDelegateAdapter
    //     TODO Dependencies Injection
//    @Inject lateinit var viewHolderDelegater:

    val viewHolderDelegater = mapOf(
            GalleryAdapter.GalleryType.DIRECTORY to directoryAdapter,
            GalleryAdapter.GalleryType.MEDIUM to mediumAdapter
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