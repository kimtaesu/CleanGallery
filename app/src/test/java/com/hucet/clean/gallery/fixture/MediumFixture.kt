package com.hucet.clean.gallery.fixture

import com.hucet.clean.gallery.gallery.adapter.GalleryType
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Date
import com.hucet.clean.gallery.model.Directory
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
    val EXTRA_NO_MEDIA: Set<String>
        get() {
            return setOf(
                    "/storage/emulated/0/.SPenSDK30/",
                    "/storage/emulated/0/KakaoNavi/",
                    "/storage/emulated/0/KakaoNaviSDK/symbol/",
                    "/storage/emulated/0/WhoWho/"
            )
        }

    val TEST_NO_MEDIA: TestData
        get() {
            val noMediaMedium = EXTRA_NO_MEDIA.map {
                medium(path = it)
            }
            return TestData(noMediaMedium, noMediaMedium)
        }

    val TEST_HIDDEN_FILTER: TestData
        get() {
            return TestData(
                    listOf(
                            medium(
                                    path = "/storage/emulated/0/.SPenSDK30/asd.jpg",
                                    name = "asd.jpg"
                            ),
                            medium(
                                    path = "/storage/emulated/0/KakaoNavi/aaa.mp4",
                                    name = "aaa.mp4"
                            ),
                            medium(
                                    path = "/storage/emulated/0/KakaoNaviSDK/symbol/asdas.gif",
                                    name = "asdas.gif"
                            ),
                            medium(
                                    path = "/storage/emulated/0/WhoWho/asdas.aaa",
                                    name = "asdas.aaa"
                            ),
                            medium(
                                    path = "/storage/emulated/0/asdas.aaa",
                                    name = "asdas.aaa"
                            ),
                            medium(
                                    path = "/storage/emulated/0/Camera/asdas.jpg",
                                    name = "asdas.jpg"
                            ),
                            medium(
                                    path = "/storage/emulated/0/Camera/.asdas.jpg",
                                    name = ".asdas.jpg"

                            ),
                            medium(
                                    path = "/storage/emulated/0/.Camera/hiddenFolder.jpg",
                                    name = "hiddenFolder.jpg"

                            )
                    ),
                    listOf(
                            medium(
                                    path = "/storage/emulated/0/asdas.aaa",
                                    name = "asdas.aaa"
                            ),
                            medium(
                                    path = "/storage/emulated/0/Camera/asdas.jpg",
                                    name = "asdas.jpg"
                            ))
            )
        }
    val TEST_CATEGORY_DIR: TestData
        get() {
            return TestData(
                    listOf(
                            medium(
                                    1,
                                    "3_Forest.jpg",
                                    "/storage/emulated/0/Samsung/Image/3_Forest.jpg",
                                    1451574083,
                                    1406902694000,
                                    4112102
                            ),
                            medium(
                                    32,
                                    "41_Forest.jpg",
                                    "/storage/emulated/0/Samsung/Image/41_Forest.jpg",
                                    1451574083,
                                    1406902694000,
                                    4112102
                            ),
                            medium(
                                    441,
                                    "Screenshot_2017-08-02-09-35-33.jpg",
                                    "/storage/emulated/0/Pictures/Screenshots/Screenshot_2017-08-02-09-35-33.png",
                                    1451574083,
                                    1406902694000,
                                    2729456
                            ),
                            medium(
                                    1124,
                                    "14845794522132.png",
                                    "/storage/emulated/0/Samsung/Image/4_Structure.jpg",
                                    1451574083,
                                    1406902694000,
                                    2729456
                            ),
                            medium(
                                    32,
                                    "4_Structure.png",
                                    "/storage/emulated/0/Pictures/KakaoTalk/14845794522132.png",
                                    1451574083,
                                    1406902694000,
                                    2729456
                            )
                    ),
                    listOf(
                            directory(
                                    3,
                                    medium(path = "/storage/emulated/0/Samsung/Image/3_Forest.jpg"),
                                    1,
                                    "Image",
                                    "/storage/emulated/0/Samsung/Image"
                            ),
                            directory(
                                    1,
                                    medium(path = "/storage/emulated/0/Pictures/Screenshots/Screenshot_2017-08-02-09-35-33.png"),
                                    441,
                                    "Screenshots",
                                    "/storage/emulated/0/Pictures/Screenshots"
                            ),
                            directory(
                                    1,
                                    medium(path = "/storage/emulated/0/Pictures/KakaoTalk/14845794522132.png"),
                                    32,
                                    "KakaoTalk",
                                    "/storage/emulated/0/Pictures/KakaoTalk"
                            ))
            )
        }

    val TEST_DIRECTORY_GROUP: TestData
        get() {
            return TestData(
                    listOf(
                            medium(
                                    1,
                                    "3_Forest.jpg",
                                    "/storage/emulated/0/Samsung/Image/3_Forest.jpg",
                                    1451574083,
                                    1406902694000,
                                    4112102
                            ),
                            medium(
                                    32,
                                    "41_Forest.jpg",
                                    "/storage/emulated/0/Samsung/Image/41_Forest.jpg",
                                    1451574083,
                                    1406902694000,
                                    4112102
                            ),
                            medium(
                                    441,
                                    "Screenshot_2017-08-02-09-35-33.jpg",
                                    "/storage/emulated/0/Pictures/Screenshots/Screenshot_2017-08-02-09-35-33.png",
                                    1451574083,
                                    1406902694000,
                                    2729456
                            ),
                            medium(
                                    1124,
                                    "14845794522132.png",
                                    "/storage/emulated/0/Samsung/Image/4_Structure.jpg",
                                    1451574083,
                                    1406902694000,
                                    2729456
                            ),
                            medium(
                                    32,
                                    "4_Structure.png",
                                    "/storage/emulated/0/Pictures/KakaoTalk/14845794522132.png",
                                    1451574083,
                                    1406902694000,
                                    2729456
                            )
                    ),
                    listOf(
                            directory(
                                    3,
                                    medium(path = "/storage/emulated/0/Samsung/Image/3_Forest.jpg"),
                                    1,
                                    "Image",
                                    "/storage/emulated/0/Samsung/Image"
                            ),
                            directory(
                                    1,
                                    medium(path = "/storage/emulated/0/Pictures/Screenshots/Screenshot_2017-08-02-09-35-33.png"),
                                    441,
                                    "Screenshots",
                                    "/storage/emulated/0/Pictures/Screenshots"
                            ),
                            directory(
                                    1,
                                    medium(path = "/storage/emulated/0/Pictures/KakaoTalk/14845794522132.png"),
                                    32,
                                    "KakaoTalk",
                                    "/storage/emulated/0/Pictures/KakaoTalk"
                            ))
            )
        }

    fun date(
            id: Long = 0,
            date: String = "",
            path: String = "",
            name: String = "",
            viewType: GalleryType = GalleryType.DATE


    ): Date {
        return Date(date, viewType, id, name, path)
    }

    fun medium(id: Long = 0,
               name: String = "",
               path: String = "",
               modified: Long = 0,
               taken: Long = 0,
               size: Long = 0,
               orientation: Int = 0,
               mimeType: String = "",
               viewType: GalleryType = GalleryType.MEDIUM
    ): Medium {
        return Medium(id, name, path, modified, taken, size, orientation, mimeType)
    }

    fun directory(
            count: Int = 0,
            medium: Medium = MediumFixture.medium(),
            id: Long = 0,
            name: String = "",
            path: String = ""
    ): Directory {
        return Directory(count, medium, id, name, path)
    }

}

data class TestData(
        val test: List<Medium>,
        val correct: List<Basic>
)