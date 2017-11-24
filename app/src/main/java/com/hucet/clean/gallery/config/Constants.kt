package com.hucet.clean.gallery.config

/**
 * Created by taesu on 2017-10-30.
 */

val GLIDE_OVERRIVE_SIZE = 255
val GLIDE_SIZE_MULTIPLIER = 0.25f

const val DAGGER_NAMED_GRID = "grid"
const val DAGGER_NAMED_LINEAR = "linear"
// preference keys
val key_exclude_folders = "key_exclude_folders"
val key_include_folders = "key_include_folders"
val key_category_type = "key_category_type"
val key_layout_type = "key_layout_type"
val key_show_hidden = "key_show_hidden"
val key_filter_media = "key_filter_media"
val key_dir_sorting = "key_dir_sorting"
val key_dir_root_sorting = "key_dir_root_sorting"
val key_date_sorting = "key_date_sorting"

// filterd media
val NONE = 0
val IMAGES: Long = 1
val VIDEOS: Long = 2
val GIFS: Long = 4


// base sort types
val SORT_ASCENDING: Long = 0x0100000000000
val SORT_DESCENDING: Long = 0x1000000000000


// medium sort types
val SORT_BY_NAME: Long = 0x0000000000001
val SORT_BY_DATE_MODIFIED: Long = 0x0000000000010
val SORT_BY_SIZE: Long = 0x0000000000100
val SORT_BY_DATE_TAKEN: Long = 0x0000000001000
val SORT_BY_PATH: Long = 0x0000000010000
// date sort types
val SORT_BY_DAILY: Long = 0x0000000100000
val SORT_BY_MONTHLY: Long = 0x0000001000000
val SORT_BY_YEARLY: Long = 0x0000010000000

// medium dir types
val SORT_BY_COUNT: Long = 0x0000100000000
val SORT_BY_DIR_NAME: Long = 0x0001000000000
val SORT_BY_DIR_PATH: Long = 0x0010000000000
