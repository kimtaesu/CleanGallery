package com.hucet.clean.gallery.gallery

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.hucet.clean.gallery.model.Basic

/**
 * Created by taesu on 2017-11-09.
 */
abstract class AbstractGalleryCreator {
    abstract fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int, item: Basic?)

    abstract fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder
}


