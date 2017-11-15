package com.hucet.clean.gallery

import com.hucet.clean.gallery.model.Directory

/**
 * Created by taesu on 2017-11-15.
 */

fun String.convertFileSeperator2Linux(): String {
    return this.replace("\\", "/")
}

