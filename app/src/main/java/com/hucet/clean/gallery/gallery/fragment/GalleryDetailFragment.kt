package com.hucet.clean.gallery.gallery.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.inject.Injectable
import com.hucet.clean.gallery.model.Medium
import kotlinx.android.synthetic.main.fragment_gallery_detail.*

/**
 * Created by taesu on 2017-11-03.
 */
class GalleryDetailFragment : Fragment(), Injectable {
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

    companion object {
        val TAG = GalleryDetailFragment.javaClass.name
        private val BUNDLE_KEY_MEDIUM = "BUNDLE_KEY_MEDIUM"

        fun newInstance(medium: Medium): GalleryDetailFragment {
            val fragment = GalleryDetailFragment()
            val bundle = Bundle()
            bundle.putSerializable(BUNDLE_KEY_MEDIUM, medium)
            fragment.setArguments(bundle)
            return fragment
        }

        fun isVisible(supportFragmentManager: FragmentManager) = supportFragmentManager.findFragmentByTag(TAG)?.isVisible == true
    }
}