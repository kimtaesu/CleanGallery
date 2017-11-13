package com.hucet.clean.gallery.gallery.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.hucet.clean.gallery.inject.scopes.PerFragment
import com.hucet.clean.gallery.model.Basic
import javax.inject.Inject

/**
 * Created by taesu on 2017-10-31.
 */
@PerFragment
class GalleryAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @Inject lateinit var delegateMap: Map<GalleryType, @JvmSuppressWildcards AbstractDelegateAdapter>

    private var Items: ArrayList<Basic> = arrayListOf()
    private var onClick: ((Basic) -> Unit)? = null
    private var recyclerView: RecyclerView? = null

    fun setOnClickListener(recyclerView: RecyclerView, onGalleryClicked: (Basic) -> Unit) {
        this.recyclerView = recyclerView
        this.onClick = onGalleryClicked
    }

    override fun getItemViewType(position: Int): Int {
        return this.Items[position].viewType.value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        val viewHoler = delegateMap[GalleryType.galleryType(viewType)]?.onCreateViewHolder(parent, viewType)
        viewHoler?.itemView?.setOnClickListener({
            val position = recyclerView?.getChildAdapterPosition(it)
            onClick?.invoke(Items.get(position!!))
        })
        return viewHoler
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val item = Items[position]
        delegateMap[item.viewType]?.onBindViewHolder(holder, position, item)
    }

    override fun getItemCount() = Items.size

    fun updateData(newItems: List<Basic>) {
        updateByDiff(newItems)
    }

    fun clearItems() {
        updateByDiff(emptyList())
    }

    private fun updateByDiff(newItems: List<Basic>) {
        val diffCallback = MediumDiffCallback(this.Items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.Items.clear()
        this.Items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}