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
    enum class GalleryType(val value: Int) {
        DIRECTORY(0), MEDIUM(1);
    }

    @Inject lateinit var viewTypeDelegateAdapter: ViewTypeDelegateAdapter
    //    @Inject lateinit var glideRequests: GlideRequests
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
        val viewHoler = viewTypeDelegateAdapter[viewType]?.onCreateViewHolder(parent, viewType)
        viewHoler?.itemView?.setOnClickListener({
            val position = recyclerView?.getChildAdapterPosition(it)
            onClick?.invoke(Items.get(position!!))
        })
        return viewHoler
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val item = Items[position]
        viewTypeDelegateAdapter[item.viewType.value]?.onBindViewHolder(holder, position, item)
    }

    override fun getItemCount() = Items.size

    fun updateData(newItems: List<Basic>) {
        updateByDiff(newItems)
    }

    fun clearItems() {
        updateByDiff(emptyList())
    }

    private fun <T : Basic> updateByDiff(newItems: List<T>) {
        val diffCallback = MediumDiffCallback(this.Items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.Items.clear()
        this.Items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}