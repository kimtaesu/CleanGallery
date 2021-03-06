package com.hucet.clean.gallery.gallery.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.hucet.clean.gallery.gallery.glide.GlideRequests
import com.hucet.clean.gallery.model.Basic

/**
 * Created by taesu on 2017-11-09.
 */
interface AbstractDelegateAdapter {

    fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int, item: Basic, glideRequests: GlideRequests?)

    fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder
}
