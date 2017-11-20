package com.hucet.clean.gallery

import android.widget.ImageView
import com.hucet.clean.gallery.gallery.fragment.ViewModeType
import com.hucet.clean.gallery.model.Basic

/**
 * Created by taesu on 2017-11-20.
 */
typealias OnGalleryClickedListener = (Basic, ImageView?) -> Unit
typealias OnViewModechangedListener = (ViewModeType) -> Unit
