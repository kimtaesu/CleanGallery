package com.hucet.clean.gallery.gallery.adapter.grid

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.gallery.adapter.AbstractDelegateAdapter
import com.hucet.clean.gallery.gallery.fragment.glide.GlideRequests
import com.hucet.clean.gallery.inject.scopes.PerActivity
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.MediaType
import com.hucet.clean.gallery.model.Medium
import javax.inject.Inject

/**
 * Created by taesu on 2017-11-09.
 */
@PerActivity
class MediumGridDelegateAdapter @Inject constructor() : AbstractDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater
                .from(parent?.context)
                .inflate(R.layout.medium_grid_item_gallery, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int, item: Basic, glideRequests: GlideRequests?) {
        holder as ViewHolder
        item as Medium
        holder.name.text = item.name
        ViewCompat.setTransitionName(holder.thumbnail, item.path)
        when (item.mediaType) {
            MediaType.VIDEO -> {
                holder.indicator.visibility = View.VISIBLE
                holder.indicator.setImageResource(R.drawable.ic_play_circle_outline_black_24px)
            }
            MediaType.GIF -> {
                holder.indicator.visibility = View.VISIBLE
                holder.indicator.setImageResource(R.drawable.ic_gif_black_24dp)
            }
            MediaType.IMAGE -> {
                holder.indicator.visibility = View.GONE
            }
        }
        if (glideRequests == null) return
        glideRequests
                .asThumbnail()
                .load(item.path)
                .into(holder.thumbnail)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val thumbnail: ImageView = view.findViewById(R.id.thumbnail)
        val indicator: ImageView = view.findViewById(R.id.indicator)
    }
}


