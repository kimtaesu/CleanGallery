package com.hucet.clean.gallery.gallery.list

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Directory
import com.hucet.clean.gallery.model.Medium
import javax.inject.Inject

/**
 * Created by taesu on 2017-10-31.
 */
class GalleryAdapter @Inject constructor() : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    enum class GalleryType {
        DIRECTORY, MEDIUM
    }

//    @Inject
//    lateinit var a: MediumViewHolderCreator
//        @Inject lateinit var viewHolderCreator:
//            Map<String, AbstractGalleryCreator>
    @Inject lateinit var glideRequests: GlideRequests
    private var mediums: ArrayList<Basic> = arrayListOf()
    private var onClick: ((Basic) -> Unit)? = null
    private var recyclerView: RecyclerView? = null

    fun setOnClickListener(recyclerView: RecyclerView, onGalleryClicked: (Basic) -> Unit) {
        this.recyclerView = recyclerView
        this.onClick = onGalleryClicked
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val medium = mediums[position]
        when (medium) {
            is Medium -> {
                holder.fileName.text = medium.path
//                glideRequests
//                        .asDrawable()
//                        .centerCrop()
//                        .load(medium.path)
//                        .into(holder.thumbnail)
            }
            is Directory -> {

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item_gallery, parent, false)

        itemView.setOnClickListener({
            val position = recyclerView?.getChildAdapterPosition(it)
            onClick?.invoke(mediums.get(position!!))
        })
        return ViewHolder(itemView)
    }

    override fun getItemCount() = mediums.size

    fun <T : Basic> updateData(newItems: Map<String, List<T>>) {
        val allItems = newItems.flatMap {
            it.value
        }
        val diffCallback = MediumDiffCallback(this.mediums, allItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.mediums.clear()
        this.mediums.addAll(allItems)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fileName: TextView = view.findViewById(R.id.filename)
        val thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    }


}