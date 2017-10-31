package com.hucet.clean.gallery.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hucet.clean.gallery.R

/**
 * Created by taesu on 2017-10-30.
 */
class ListGalleryFragment : Fragment() {
    companion object {
        fun newInstance() = ListGalleryFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_gallery, null)
}