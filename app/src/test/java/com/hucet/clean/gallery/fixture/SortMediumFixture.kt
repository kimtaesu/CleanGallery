package com.hucet.clean.gallery.fixture

import com.hucet.clean.gallery.gallery.adapter.GalleryType

/**
 * Created by taesu on 2017-11-19.
 */

object SortMediumFixture {
    private val EXTRA_TEST_DATE = listOf(
            MediumFixture.medium(1,
                    "3_Forest.jpg",
                    "/storage/emulated/0/Samsung/Image/3_Forest.jpg",
                    1451574083,
                    1406902694000,
                    4112102
            ),

            MediumFixture.medium(2,
                    "4_Structure.jpg",
                    "/storage/emulated/0/Samsung/Image/4_Structure.jpg",
                    1487854345,
                    1487854345000,
                    2729456
            ),
            MediumFixture.medium(5,
                    "5_Forest.jpg",
                    "/storage/emulated/0/Samsung/Image/5_Forest.jpg",
                    1509947280,
                    1509947280000,
                    4112102
            ),
            MediumFixture.medium(6,
                    "6_Forest.jpg",
                    "/storage/emulated/0/Samsung/Image/6_Forest.jpg",
                    1509947358,
                    1509947358000,
                    4112102
            ),
            MediumFixture.medium(7,
                    "7_Forest.jpg",
                    "/storage/emulated/0/Samsung/Image/7_Forest.jpg",
                    1509947358,
                    1509947358000,
                    4112102
            ))
    val TEST_CATEGORY_DAILY_DESC: TestData
        get() {
            return TestData(
                    EXTRA_TEST_DATE,
                    listOf(
                            MediumFixture.date(0,
                                    "2017-11-06",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ),
                            MediumFixture.date(0,
                                    "2017-02-23",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ),
                            MediumFixture.date(0,
                                    "2016-01-01",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ))
            )
        }
    val TEST_CATEGORY_DAILY_ASC: TestData
        get() {
            return TestData(
                    EXTRA_TEST_DATE,
                    listOf(
                            MediumFixture.date(0,
                                    "2016-01-01",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ),
                            MediumFixture.date(0,
                                    "2017-02-23",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ),
                            MediumFixture.date(0,
                                    "2017-11-06",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ))
            )
        }

    val TEST_CATEGORY_MONTHLY_DESC: TestData
        get() {
            return TestData(
                    EXTRA_TEST_DATE,
                    listOf(
                            MediumFixture.date(0,
                                    "2017-11",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ),
                            MediumFixture.date(0,
                                    "2017-02",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ),
                            MediumFixture.date(0,
                                    "2016-01",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ))
            )
        }
    val TEST_CATEGORY_MONTHLY_ASC: TestData
        get() {
            return TestData(
                    EXTRA_TEST_DATE,
                    listOf(
                            MediumFixture.date(0,
                                    "2016-01",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ),
                            MediumFixture.date(0,
                                    "2017-02",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ),
                            MediumFixture.date(0,
                                    "2017-11",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ))
            )
        }
    val TEST_CATEGORY_YEARLY_DESC: TestData
        get() {
            return TestData(
                    EXTRA_TEST_DATE,
                    listOf(
                            MediumFixture.date(0,
                                    "2017",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ),
                            MediumFixture.date(0,
                                    "2016",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ))
            )
        }
    val TEST_CATEGORY_YEARLY_ASC: TestData
        get() {
            return TestData(
                    EXTRA_TEST_DATE,
                    listOf(
                            MediumFixture.date(0,
                                    "2016",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ),
                            MediumFixture.date(0,
                                    "2017",
                                    "",
                                    "",
                                    GalleryType.DATE
                            ))
            )
        }
}
