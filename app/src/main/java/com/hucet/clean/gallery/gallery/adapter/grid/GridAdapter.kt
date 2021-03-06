package com.hucet.clean.gallery.gallery.adapter.grid

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.hucet.clean.gallery.config.DAGGER_NAMED_GRID
import com.hucet.clean.gallery.gallery.adapter.AbstractDelegateAdapter
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.gallery.adapter.GalleryType
import com.hucet.clean.gallery.gallery.glide.GlideRequests
import com.hucet.clean.gallery.inject.scopes.PerActivity
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by taesu on 2017-10-31.
 */


@PerActivity
class GridAdapter @Inject constructor() : GalleryAdapter() {

    @Inject
    @field:[Named(DAGGER_NAMED_GRID)]
    lateinit var delegateGridMap: Map<GalleryType, @JvmSuppressWildcards AbstractDelegateAdapter>

    override fun createDelegateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return delegateGridMap[GalleryType.galleryType(viewType)]?.onCreateViewHolder(parent, viewType)
    }


    override fun onBindDelegateViewHolder(holder: RecyclerView.ViewHolder?, position: Int, glideRequests: GlideRequests?) {
        val item = items[position]
        delegateGridMap[item.viewType]?.onBindViewHolder(holder, position, item, glideRequests)
    }

}