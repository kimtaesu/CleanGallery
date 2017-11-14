package com.hucet.clean.gallery.fixture

import com.hucet.clean.gallery.gallery.adapter.GalleryType
import com.hucet.clean.gallery.model.Medium

/**
 * Created by taesu on 2017-11-14.
 */
object MediumFixture {
    val DEFAULT: List<Medium>
        get() {
            return listOf(
                    medium(1,
                            "3_Forest.jpg",
                            "/storage/emulated/0/Samsung/Image/3_Forest.jpg",
                            1451574083,
                            1406902694000,
                            4112102
                    ),
                    medium(2,
                            "4_Structure.jpg",
                            "/storage/emulated/0/Samsung/Image/4_Structure.jpg",
                            1451574083,
                            1406902694000,
                            2729456
                    )
            )
        }

    private fun medium(id: Long = 0,
                       name: String = "",
                       path: String = "",
                       modified: Long = 0,
                       taken: Long = 0,
                       size: Long = 0,
                       viewType: GalleryType = GalleryType.MEDIUM


    ): Medium {
        return Medium(id, name, path, modified, taken, size, viewType)
    }
}