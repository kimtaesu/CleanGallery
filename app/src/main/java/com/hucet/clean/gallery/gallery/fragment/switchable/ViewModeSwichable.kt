package com.hucet.clean.gallery.gallery.fragment.switchable

import android.support.v7.widget.RecyclerView
import com.hucet.clean.gallery.gallery.adapter.grid.GridAdapter
import com.hucet.clean.gallery.gallery.adapter.linear.LinearAdapter
import com.hucet.clean.gallery.inject.scopes.PerFragment
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.onGalleryClickedListener
import javax.inject.Inject

/**
 * Created by taesu on 2017-11-20.
 */
interface ViewModeSwichable {
    fun switchViewMode(recy: RecyclerView, manager: RecyclerView.LayoutManager, items: List<Basic>, clickedListener: onGalleryClickedListener)
}

@PerFragment
class GridViewModeSetUp @Inject constructor() : ViewModeSwichable {
    @Inject lateinit var gridAdapter: GridAdapter
    override fun switchViewMode(recy: RecyclerView, manager: RecyclerView.LayoutManager, items: List<Basic>, clickedListener: onGalleryClickedListener) {
        recy.apply {
            layoutManager = null
            adapter = null
            layoutManager = manager
            gridAdapter.setOnClickListener(this, clickedListener)
            swapAdapter(gridAdapter, false)
            gridAdapter.updateData(items)
        }
    }
}

@PerFragment
class LinearViewModeSetUp @Inject constructor() : ViewModeSwichable {
    @Inject lateinit var linearAdapter: LinearAdapter
    override fun switchViewMode(recy: RecyclerView, manager: RecyclerView.LayoutManager, items: List<Basic>, clickedListener: onGalleryClickedListener) {
        recy.apply {
            layoutManager = null
            adapter = null
            layoutManager = manager
            linearAdapter.setOnClickListener(this, clickedListener)
            swapAdapter(linearAdapter, false)
            linearAdapter.updateData(items)
        }
    }

}