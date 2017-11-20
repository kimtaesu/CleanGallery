package com.hucet.clean.gallery.gallery.adapter.linear

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.gallery.adapter.AbstractDelegateAdapter
import com.hucet.clean.gallery.gallery.fragment.GlideRequests
import com.hucet.clean.gallery.inject.scopes.PerFragment
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Medium
import javax.inject.Inject

/**
 * Created by taesu on 2017-11-09.
 */
@PerFragment
class MediumLinearDelegateAdapter @Inject constructor(private val glideRequests: GlideRequests) : AbstractDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater
                .from(parent?.context)
                .inflate(R.layout.medium_list_item_gallery, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int, item: Basic) {
        holder as ViewHolder
        item as Medium
        holder.name.text = item.name
        if (item.isVideo) {
            holder.indicator.visibility = View.VISIBLE
        } else {
            holder.indicator.visibility = View.GONE
        }
        glideRequests
                .asDrawable()
                .centerCrop()
                .load(item.path)
                .into(holder.thumbnail)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val thumbnail: ImageView = view.findViewById(R.id.thumbnail)
        val indicator: ImageView = view.findViewById(R.id.video)
    }
}


