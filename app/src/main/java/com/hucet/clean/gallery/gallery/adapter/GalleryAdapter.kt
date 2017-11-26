package com.hucet.clean.gallery.gallery.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.hucet.clean.gallery.OnGalleryClickedListener
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.gallery.glide.GlideRequests
import com.hucet.clean.gallery.model.Basic

/**
 * Created by taesu on 2017-10-31.
 */


abstract class GalleryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val items: ArrayList<Basic> = arrayListOf()
    private var onClick: OnGalleryClickedListener? = null
    private var recyclerView: RecyclerView? = null
    private var glideRequests: GlideRequests? = null

    fun setGlideRequest(glideRequests: GlideRequests) {
        this.glideRequests = glideRequests
    }

    fun setOnClickListener(recyclerView: RecyclerView, onGalleryClicked: OnGalleryClickedListener) {
        this.recyclerView = recyclerView
        this.onClick = onGalleryClicked
    }

    override fun getItemViewType(position: Int): Int {
        return this.items[position].viewType.value
    }

    abstract fun createDelegateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder?

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        val viewHoler = createDelegateViewHolder(parent, viewType)
        viewHoler?.itemView?.setOnClickListener({
            val position = recyclerView?.getChildAdapterPosition(it)
            if (viewHoler != null) {
                onClick?.invoke(items[position!!], viewHoler.itemView.findViewById(R.id.thumbnail))
            }

        })
        return viewHoler
    }

    abstract fun onBindDelegateViewHolder(holder: RecyclerView.ViewHolder?, position: Int, glideRequests: GlideRequests?)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        onBindDelegateViewHolder(holder, position, this.glideRequests)
    }

    override fun getItemCount() = items.size

    fun syncUpdateData(newItems: List<Basic>) {
        updateByDiff(calculateDiff(newItems))
    }

    fun clearItems() {
        updateByDiff(calculateDiff(emptyList()))
    }

    fun calculateDiff(newItems: List<Basic>): DiffUtil.DiffResult {
        val diffCallback = MediumDiffCallback(this.items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items.clear()
        this.items.addAll(newItems)
        return diffResult
    }

    fun updateByDiff(diffResult: DiffUtil.DiffResult) {
        diffResult.dispatchUpdatesTo(this)
    }
}