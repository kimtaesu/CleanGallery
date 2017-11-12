package com.hucet.clean.gallery.gallery.sort

import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Medium

/**
 * Created by taesu on 2017-11-12.
 */
class MediaSortOptions(val strategy: STRATEGY, var order: ORDER = ORDER.DESC) {

    enum class ORDER {
        DESC, ASC
    }

    enum class STRATEGY(val fieldName: String) {
        NAME(Basic::name.name),
        SIZE(Medium::size.name),
        TAKEN(Medium::taken.name),
        PATH(Basic::path.name),
        MODIFIED(Medium::modified.name)
    }

    infix fun with(order: ORDER): MediaSortOptions {
        this.order = order
        return this
    }


}