package com.hucet.clean.gallery.gallery.list

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.inject.scopes.PerFragment
import com.hucet.clean.gallery.model.Medium
import javax.inject.Inject

/**
 * Created by taesu on 2017-10-31.
 */
@PerFragment
class GalleryAdapter @Inject constructor() : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    @Inject lateinit var glideRequests: GlideRequests
    private var mediums: ArrayList<Medium> = arrayListOf()
    private var onClick: GalleryListener? = null
    private var recyclerView: RecyclerView? = null

    fun setOnClickListener(recyclerView: RecyclerView, onClick: GalleryListener) {
        this.recyclerView = recyclerView
        this.onClick = onClick
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val medium = mediums[position]
        holder.fileName.text = medium.name
        glideRequests
                .asDrawable()
                .centerCrop()
                .load(medium.path)
                .into(holder.thumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item_gallery, parent, false)


        itemView.setOnClickListener({
            val position = recyclerView?.getChildAdapterPosition(it)
            onClick?.onGalleryClicked(mediums.get(position!!))
        })
        return ViewHolder(itemView)
    }

    override fun getItemCount() = mediums.size

    fun updateData(newItems: List<Medium>) {
        val diffCallback = MediumDiffCallback(this.mediums, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.mediums.clear()
        this.mediums.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fileName: TextView = view.findViewById(R.id.filename)
        val thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    }


}