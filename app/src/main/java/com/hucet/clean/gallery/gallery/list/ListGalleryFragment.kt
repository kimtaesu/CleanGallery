package com.hucet.clean.gallery.gallery.list

import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.activity.MainActivity
import com.hucet.clean.gallery.extension.isExternalStorageDir
import com.hucet.clean.gallery.extension.parentDirPath
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.presenter.Gallery
import com.hucet.clean.gallery.inject.Injectable
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Directory
import com.hucet.clean.gallery.model.Medium
import kotlinx.android.synthetic.main.fragment_gallery.*
import javax.inject.Inject


/**
 * Created by taesu on 2017-10-30.
 */
class ListGalleryFragment : Fragment(), Gallery.View, Injectable {
    @Inject lateinit var adapter: GalleryAdapter
    @Inject lateinit var presenter: Gallery.Presenter
    var curPath = Environment.getExternalStorageDirectory().absolutePath

    companion object {
        fun newInstance() = ListGalleryFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_gallery, null)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        presenter.fetchItems(curPath)
    }


    private val onGalleryClicked: (Medium) -> Unit = {
        when (it) {
            is Medium -> {
                (activity as MainActivity)?.onGalleryClicked.invoke(it)
            }
//            TODO
//            is Directory -> {
//                curPath = it.absolutePath
//                adapter.clearItems()
//                presenter.fetchItems(curPath)
//            }
        }
    }

    fun onBackPressed(): Boolean {
        if (!curPath.isExternalStorageDir()) {
            curPath = Environment.getExternalStorageDirectory().absolutePath
            presenter.fetchItems(curPath)
            return false
        }
        return true
    }

    private fun initRecyclerView() {
        gallery_list.apply {
            this@ListGalleryFragment.adapter.setOnClickListener(this, onGalleryClicked)
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