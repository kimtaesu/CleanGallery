package com.hucet.clean.gallery.gallery.sort.old

/**
 * Created by taesu on 2017-11-12.
 */
class OldMediaSortOptions(val strategy: STRATEGY, var order: ORDER = ORDER.DESC) {

    enum class ORDER {
        DESC, ASC
    }

    enum class STRATEGY {
        NAME,
        SIZE,
        TAKEN,
        PATH,
        MODIFIED
    }

    infix fun with(order: ORDER): OldMediaSortOptions {
        this.order = order
        return this
    }


}