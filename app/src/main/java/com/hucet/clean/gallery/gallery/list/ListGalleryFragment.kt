package com.hucet.clean.gallery.gallery.list

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.gallery.list.presenter.Gallery
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_gallery.*
import javax.inject.Inject


/**
 * Created by taesu on 2017-10-30.
 */


class ListGalleryFragment : Fragment(), Gallery.View {


    @Inject lateinit var adapter: GalleryAdapter
    @Inject lateinit var presenter: Gallery.Presenter
    val onClick by lazy {
        arguments.getSerializable(BUNDLE_KEY_CLICK_LISTENER) as GalleryListener
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_gallery, null)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        presenter.fetchItems()
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context)
    }

    private fun initRecyclerView() {
        gallery_list.apply {
            this@ListGalleryFragment.adapter.setOnClickListener(this, onClick)
            adapter = this@ListGalleryFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun showProgress() {
        Toast.makeText(context, "showProgress", Toast.LENGTH_SHORT).show()
    }

    override fun hideProgress() {
        Toast.makeText(context, "hideProgress", Toast.LENGTH_SHORT).show()
    }

    override fun showError() {
        Toast.makeText(context, "showError", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private val BUNDLE_KEY_CLICK_LISTENER = "BUNDLE_KEY_CLICK_LISTENER"

        class Builder {
            private var onClick: GalleryListener? = null

            fun setOnClickListener(onClick: GalleryListener): Builder {
                this.onClick = onClick
                return this
            }

            fun build(): ListGalleryFragment {
                val fragment = ListGalleryFragment()
                fragment.setArguments(createArgs())
                return fragment
            }

            private fun createArgs(): Bundle {
                val bundle = Bundle()
                if (onClick != null) {
                    //store the listener in the arguments bundle
                    //it is a state less lambda, guaranteed to be serializable
                    bundle.putSerializable(BUNDLE_KEY_CLICK_LISTENER, onClick)
                }
                return bundle
            }

        }
    }
}