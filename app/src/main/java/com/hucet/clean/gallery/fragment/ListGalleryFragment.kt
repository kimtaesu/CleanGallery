package com.hucet.clean.gallery.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.gallery.adapter.MediumDiffCallback
import com.hucet.clean.gallery.model.Medium
import kotlinx.android.synthetic.main.fragment_gallery.*


/**
 * Created by taesu on 2017-10-30.
 */
class ListGalleryFragment : Fragment() {

    private var adapter = GalleryAdapter()

    companion object {
        fun newInstance() = ListGalleryFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_gallery, null)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        val dummy = arrayListOf(Medium("tyler", "/tyler", false, System.currentTimeMillis(), System.currentTimeMillis(), 10))
        updateData(dummy)
    }

    private fun initRecyclerView() {
        gallery_list.apply {
            adapter = this@ListGalleryFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun updateData(newItems: List<Medium>) {
        val diffCallback = MediumDiffCallback(this.adapter.mediums, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.adapter.mediums.clear()
        this.adapter.mediums.addAll(newItems)
        diffResult.dispatchUpdatesTo(this.adapter)
    }
}