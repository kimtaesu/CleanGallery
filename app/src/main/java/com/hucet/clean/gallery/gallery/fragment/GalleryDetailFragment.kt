package com.hucet.clean.gallery.gallery.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.inject.Injectable
import com.hucet.clean.gallery.model.Medium
import kotlinx.android.synthetic.main.fragment_gallery_detail.*

/**
 * Created by taesu on 2017-11-03.
 */
class GalleryDetailFragment : Fragment(), Injectable {
    val medium by lazy {
        arguments?.getSerializable(BUNDLE_KEY_MEDIUM) as Medium
    }

    val transitionName by lazy {
        arguments?.getSerializable(EXTRA_TRANSITION_NAME) as String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_gallery_detail, null)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateView(medium)
    }


    private fun updateView(medium: Medium) {
        content.transitionName = transitionName
        Glide.with(this)
                .load(medium.path)
                .into(content)
        path.text = medium.path
    }

    companion object {
        val TAG = GalleryDetailFragment.javaClass.name
        private val BUNDLE_KEY_MEDIUM = "BUNDLE_KEY_MEDIUM"
        private val EXTRA_TRANSITION_NAME = "EXTRA_TRANSITION_NAME"

        fun newInstance(medium: Medium, transitionName: String): GalleryDetailFragment {
            val fragment = GalleryDetailFragment()
            val bundle = Bundle()
            bundle.putSerializable(BUNDLE_KEY_MEDIUM, medium)
            bundle.putString(EXTRA_TRANSITION_NAME, transitionName)
            fragment.setArguments(bundle)
            return fragment
        }

        fun isVisible(supportFragmentManager: FragmentManager) = supportFragmentManager.findFragmentByTag(TAG)?.isVisible == true
    }
}