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
                                    path = "/storage/emulated/0/.SPenSDK30/asd.jpg"
                            ),
                            medium(
                                    path = "/storage/emulated/0/KakaoNavi/aaa.mp4"
                            ),
                            medium(
                                    path = "/storage/emulated/0/KakaoNaviSDK/symbol/asdas.gif"
                            ),
                            medium(
                                    path = "/storage/emulated/0/WhoWho/asdas.aaa"
                            ),
                            medium(
                                    path = "/storage/emulated/0/asdas.aaa"
                            ),
                            medium(
                                    path = "/storage/emulated/0/Camera/asdas.jpg"
                            )),
                    listOf(
                            medium(
                                    path = "/storage/emulated/0/asdas.aaa"
                            ),
                            medium(
                                    path = "/storage/emulated/0/Camera/asdas.jpg"
                            ))
            )
        }

    private val EXTRA_TEST_DATE = listOf(
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
                    1487854345,
                    1487854345000,
                    2729456
            ),
            medium(5,
                    "5_Forest.jpg",
                    "/storage/emulated/0/Samsung/Image/5_Forest.jpg",
                    1509947280,
                    1509947280000,
                    4112102
            ),
            medium(6,
                    "6_Forest.jpg",
                    "/storage/emulated/0/Samsung/Image/6_Forest.jpg",
                    1509947358,
                    1509947358000,
                    4112102
            ),
            medium(7,
                    "7_Forest.jpg",
                    "/storage/emulated/0/Samsung/Image/7_Forest.jpg",
                    1509947358,
                    1509947358000,
                    4112102
            ))
    val TEST_CATEGORY_DAILY: TestData
        get() {
            return TestData(
                    EXTRA_TEST_DATE,
                    listOf(
                            date(0,
                                    "2016-01-01",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ),
                            date(0,
                                    "2017-02-23",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ),
                            date(0,
                                    "2017-11-06",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ))
            )
        }
    val TEST_CATEGORY_MONTHLY: TestData
        get() {
            return TestData(
                    EXTRA_TEST_DATE,
                    listOf(
                            date(0,
                                    "2016-01",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ),
                            date(0,
                                    "2017-02",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ),
                            date(0,
                                    "2017-11",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ))
            )
        }
    val TEST_CATEGORY_YEARLY: TestData
        get() {
            return TestData(
                    EXTRA_TEST_DATE,
                    listOf(
                            date(0,
                                    "2016",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ),
                            date(0,
                                    "2017",
                                    "",
                                    "",
                                    GalleryType.DATE
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
                                    "/storage/emulated/0/Samsung/Image/3_Forest.jpg",
                                    1,
                                    "Image",
                                    "/storage/emulated/0/Samsung/Image"
                            ),
                            directory(
                                    1,
                                    "/storage/emulated/0/Pictures/Screenshots/Screenshot_2017-08-02-09-35-33.png",
                                    441,
                                    "Screenshots",
                                    "/storage/emulated/0/Pictures/Screenshots"
                            ),
                            directory(
                                    1,
                                    "/storage/emulated/0/Pictures/KakaoTalk/14845794522132.png",
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
               viewType: GalleryType = GalleryType.MEDIUM


    ): Medium {
        return Medium(id, name, path, modified, taken, size)
    }

    fun directory(
            count: Int = 0,
            thumbnail: String = "",
            id: Long = 0,
            name: String = "",
            path: String = ""
    ): Directory {
        return Directory(count, thumbnail, id, name, path)
    }

}

data class TestData(
        val test: List<Medium>,
        val correct: List<Basic>
)