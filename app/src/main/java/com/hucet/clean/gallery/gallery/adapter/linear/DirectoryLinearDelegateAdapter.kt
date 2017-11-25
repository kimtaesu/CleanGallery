package com.hucet.clean.gallery.gallery.adapter.linear

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.signature.MediaStoreSignature
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.gallery.adapter.AbstractDelegateAdapter
import com.hucet.clean.gallery.gallery.glide.GlideRequests
import com.hucet.clean.gallery.inject.scopes.PerActivity
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Directory
import javax.inject.Inject

/**
 * Created by taesu on 2017-11-09.
 */
@PerActivity
class DirectoryLinearDelegateAdapter @Inject constructor() : AbstractDelegateAdapter {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int, item: Basic, glideRequests: GlideRequests?) {
        holder as ViewHolder
        item as Directory
        holder.name.text = item.name
        holder.path.text = item.path
        holder.count.text = item.count.toString()
        if (glideRequests == null) return
        glideRequests
                .asThumbnail()
                .signature(MediaStoreSignature(item.medium.mimeType, item.medium.modified, item.medium.orientation))
                .load(item.medium.path)
                .into(holder.thumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater
                .from(parent?.context)
                .inflate(R.layout.directory_list_item_gallery, parent, false)
        return ViewHolder(v)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.dir_name)
        val path: TextView = view.findViewById(R.id.dir_path)
        val count: TextView = view.findViewById(R.id.dir_count)
        val thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    }

}