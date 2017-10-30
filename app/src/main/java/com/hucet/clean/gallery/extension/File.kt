package com.hucet.clean.gallery.extension

import com.hucet.clean.gallery.config.NOMEDIA
import java.io.File

/**
 * Created by taesu on 2017-10-30.
 */
fun File.containsNoMedia() = isDirectory && File(this, NOMEDIA).exists()