package com.hucet.clean.gallery.gallery.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.inject.scopes.PerFragment
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Medium
import javax.inject.Inject

/**
 * Created by taesu on 2017-11-09.
 */
@PerFragment
class MediumDelegateAdapter @Inject constructor() : AbstractDelegateAdapter {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int, item: Basic?) {
        holder as ViewHolder?
        item as Medium?

        holder?.fileName?.text = item?.path
//        glideRequests
//                .asDrawable()
//                .centerCrop()
//                .load(medium.path)
//                .into(holder.thumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater
                .from(parent?.context)
                .inflate(R.layout.medium_item_gallery, parent, false)

        return ViewHolder(v)
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fileName: TextView = view.findViewById(R.id.filename)
        val thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    }
}


