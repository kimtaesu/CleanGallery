package com.hucet.clean.gallery.gallery.view_mode

import android.support.v7.widget.RecyclerView
import com.hucet.clean.gallery.OnGalleryClickedListener
import com.hucet.clean.gallery.gallery.adapter.grid.GridAdapter
import com.hucet.clean.gallery.gallery.adapter.linear.LinearAdapter
import com.hucet.clean.gallery.gallery.glide.GlideRequests
import com.hucet.clean.gallery.inject.scopes.PerActivity
import com.hucet.clean.gallery.model.Basic
import javax.inject.Inject

/**
 * Created by taesu on 2017-11-20.
 */
interface ViewModeSwichable {
    fun switchViewMode(recy: RecyclerView, manager: RecyclerView.LayoutManager, glideRequests: GlideRequests, clickedListener: OnGalleryClickedListener)
}

@PerActivity
class GridViewModeSetUp @Inject constructor() : ViewModeSwichable {
    @Inject lateinit var gridAdapter: GridAdapter
    override fun switchViewMode(recy: RecyclerView, manager: RecyclerView.LayoutManager, glideRequests: GlideRequests, clickedListener: OnGalleryClickedListener) {
        recy.apply {
            layoutManager = null
            adapter = null
            layoutManager = manager
            swapAdapter(gridAdapter, false)
        }
        gridAdapter.apply {
            setGlideRequest(glideRequests)
            setOnClickListener(recy, clickedListener)
        }
    }
}

@PerActivity
class LinearViewModeSetUp @Inject constructor() : ViewModeSwichable {
    @Inject lateinit var linearAdapter: LinearAdapter
    override fun switchViewMode(recy: RecyclerView, manager: RecyclerView.LayoutManager, glideRequests: GlideRequests, clickedListener: OnGalleryClickedListener) {
        recy.apply {
            layoutManager = null
            adapter = null
            layoutManager = manager
            swapAdapter(linearAdapter, false)

        }
        linearAdapter.apply {
            setGlideRequest(glideRequests)
            setOnClickListener(recy, clickedListener)
        }
    }

}