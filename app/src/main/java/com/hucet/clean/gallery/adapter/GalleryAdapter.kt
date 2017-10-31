package com.hucet.clean.gallery.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.model.Medium

/**
 * Created by taesu on 2017-10-31.
 */
class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    var mediums: List<Medium> = arrayListOf()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val medium = mediums[position]
        holder.fileName.text = medium.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item_gallery, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = mediums.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var fileName: TextView = view.findViewById(R.id.filename)

    }

}