package com.hucet.clean.gallery.gallery.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.gallery.Gallery
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_gallery.*
import javax.inject.Inject


/**
 * Created by taesu on 2017-10-30.
 */
class ListGalleryFragment : Fragment(), Gallery.View {


    @Inject lateinit var adapter: GalleryAdapter
    @Inject lateinit var presenter: Gallery.Presenter

    companion object {
        fun newInstance() = ListGalleryFragment()
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

}