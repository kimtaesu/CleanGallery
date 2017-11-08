package com.hucet.clean.gallery.gallery.detail

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.model.Medium
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_gallery_detail.*

/**
 * Created by taesu on 2017-11-03.
 */
class GalleryDetailFragment : Fragment() {
    val medium by lazy {
        arguments.getSerializable(BUNDLE_KEY_MEDIUM) as Medium
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_gallery_detail, null)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateView(medium)
    }


    private fun updateView(medium: Medium) {
        path.text = medium.path
        Glide.with(this).load(medium.path).into(content)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context)
    }

    companion object {
        private val BUNDLE_KEY_MEDIUM = "BUNDLE_KEY_MEDIUM"

        fun newInstance(medium: Medium): GalleryDetailFragment {
            val fragment = GalleryDetailFragment()
            val bundle = Bundle()
            bundle.putSerializable(BUNDLE_KEY_MEDIUM, medium)
            fragment.setArguments(bundle)
            return fragment
        }
    }
}