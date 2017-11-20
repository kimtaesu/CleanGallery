package com.hucet.clean.gallery.gallery.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.hucet.clean.gallery.OnGalleryClickedListener
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.model.Basic

/**
 * Created by taesu on 2017-10-31.
 */


abstract class GalleryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val Items: ArrayList<Basic> = arrayListOf()
    private var onClick: OnGalleryClickedListener? = null
    private var recyclerView: RecyclerView? = null

    fun setOnClickListener(recyclerView: RecyclerView, onGalleryClicked: OnGalleryClickedListener) {
        this.recyclerView = recyclerView
        this.onClick = onGalleryClicked
    }

    override fun getItemViewType(position: Int): Int {
        return this.Items[position].viewType.value
    }

    abstract fun createDelegateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder?

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        val viewHoler = createDelegateViewHolder(parent, viewType)
        viewHoler?.itemView?.setOnClickListener({
            val position = recyclerView?.getChildAdapterPosition(it)
            onClick?.invoke(Items[position!!], viewHoler?.itemView?.findViewById(R.id.thumbnail))
        })
        return viewHoler
    }

    abstract fun onBindDelegateViewHolder(holder: RecyclerView.ViewHolder?, position: Int)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        onBindDelegateViewHolder(holder, position)
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