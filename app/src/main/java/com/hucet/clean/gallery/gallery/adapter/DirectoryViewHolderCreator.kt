package com.hucet.clean.gallery.gallery.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.model.Basic
import javax.inject.Inject

/**
 * Created by taesu on 2017-11-09.
 */
class DirectoryViewHolderCreator @Inject constructor() : AbstractGalleryCreator {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int, item: Basic?) {
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater
                .from(parent?.context)
                .inflate(R.layout.list_item_gallery, parent, false)

        return ViewHolder(v)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fileName: TextView = view.findViewById(R.id.filename)
        val thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    }

}