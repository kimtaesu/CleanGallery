package com.hucet.clean.gallery.config

/**
 * Created by taesu on 2017-10-30.
 */

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
val key_date_sorting = "key_date_sorting"

// filterd media
val NONE = 0
val IMAGES = 1
val VIDEOS = 2
val GIFS = 4


// base sort types
val SORT_ASCENDING = 256
val SORT_DESCENDING = 512


// dir sort types
val SORT_BY_NAME = 1
val SORT_BY_DATE_MODIFIED = 2
val SORT_BY_SIZE = 4
val SORT_BY_DATE_TAKEN = 8
val SORT_BY_PATH = 16
// date sort types
val SORT_BY_DAILY = 32
val SORT_BY_MONTHLY = 64
val SORT_BY_YEARLY = 128


