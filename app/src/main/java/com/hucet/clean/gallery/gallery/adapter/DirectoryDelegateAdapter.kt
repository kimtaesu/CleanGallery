package com.hucet.clean.gallery.gallery.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.gallery.list.GlideRequests
import com.hucet.clean.gallery.inject.scopes.PerFragment
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Directory
import javax.inject.Inject

/**
 * Created by taesu on 2017-11-09.
 */
class DirectoryDelegateAdapter(val glideRequests: GlideRequests) : AbstractDelegateAdapter {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int, item: Basic?) {
        holder as ViewHolder
        item as Directory
        holder.name.text = item.dirName
        holder.path.text = item.absolutePath
        holder.count.text = item.count.toString()
        glideRequests
                .asDrawable()
                .centerCrop()
                .load(item.firstMedium.path)
                .into(holder.thumbnail)
//        TODO thumbnail
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater
                .from(parent?.context)
                .inflate(R.layout.directory_item_gallery, parent, false)

        return ViewHolder(v)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.dir_name)
        val path: TextView = view.findViewById(R.id.dir_path)
        val count: TextView = view.findViewById(R.id.dir_count)
        val thumbnail: ImageView = view.findViewById(R.id.dir_thumbnail)
    }

}