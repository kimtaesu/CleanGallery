package com.hucet.clean.gallery.gallery.view_mode

import android.app.Activity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.hucet.clean.gallery.OnGalleryClickedListener
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.gallery.adapter.GalleryType
import com.hucet.clean.gallery.gallery.glide.GlideApp
import com.hucet.clean.gallery.model.Basic

/**
 * Created by taesu on 2017-11-27.
 */
class ViewModeNavigator(val mapViewModeSetUp: Map<ViewModeType, @JvmSuppressWildcards ViewModeSwichable>) {
    fun setUpLayoutManager(activity: Activity, type: ViewModeType, recyclerView: RecyclerView, adapterFP: () -> GalleryAdapter?, onGalleryClicked: OnGalleryClickedListener) {
        val adapter = adapterFP()
        when (type) {
            ViewModeType.GRID -> {
                val gridLayoutManager = GridLayoutManager(activity, 2)
                gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (adapter?.getItemViewType(position) == GalleryType.DATE.value)
                            gridLayoutManager.spanCount
                        else
                            1
                    }
                }

                mapViewModeSetUp[type]?.switchViewMode(recyclerView,
                        gridLayoutManager,
                        GlideApp.with(activity),
                        onGalleryClicked
                )
            }
            ViewModeType.LINEAR -> {
                mapViewModeSetUp[type]?.switchViewMode(recyclerView,
                        LinearLayoutManager(activity),
                        GlideApp.with(activity),
                        onGalleryClicked)
            }
        }
    }
}