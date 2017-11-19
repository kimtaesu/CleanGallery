package com.hucet.clean.gallery.gallery.fragment

import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.activity.MainActivity
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.config.ReadOnlyConfigs
import com.hucet.clean.gallery.extension.isExternalStorageDir
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.gallery.adapter.GalleryType
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.inject.Injectable
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Directory
import com.hucet.clean.gallery.model.Medium
import com.hucet.clean.gallery.presenter.Gallery
import kotlinx.android.synthetic.main.fragment_gallery.*
import javax.inject.Inject


/**
 * Created by taesu on 2017-10-30.
 */
class ListGalleryFragment : Fragment(), Gallery.View, Injectable {
    @Inject lateinit var adapter: GalleryAdapter
    @Inject lateinit var presenter: Gallery.Presenter
    @Inject lateinit var config: ApplicationConfig
    var curPath = Environment.getExternalStorageDirectory().absolutePath

    companion object {
        fun newInstance() = ListGalleryFragment()
    }

    private val readOnlyFunction: () -> ReadOnlyConfigs = {
        (activity as MainActivity).readOnlyConfigs
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_gallery, null)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        requestFetch(readOnlyFunction.invoke())
    }


    private fun requestFetch(readOnlyConfigs: ReadOnlyConfigs) {
        println("!!!!!!!!!!!! requestFetch ${readOnlyConfigs.getCategoryMode()}")
        presenter.fetchItems(curPath, readOnlyConfigs)
    }

    private val onGalleryClicked: (Basic) -> Unit = {
        when (it) {
            is Medium -> {
                (activity as MainActivity)?.onGalleryClicked.invoke(it)
            }
            is Directory -> {
                curPath = it.path
                adapter.clearItems()
                requestFetch(readOnlyFunction.invoke())
            }
        }
    }

    fun onBackPressed(): Boolean {
        if (!curPath.isExternalStorageDir()) {
            curPath = Environment.getExternalStorageDirectory().absolutePath
            requestFetch(readOnlyFunction.invoke())
            return false
        }
        return true
    }

    private fun setUpLayoutManager(type: ViewModeType) {
        when (type) {
            ViewModeType.GRID -> setUpGrid()
            ViewModeType.LINEAR -> setUpLinear()
        }
    }

    private fun setUpGrid() {
        gallery_list.apply {
            layoutManager = null
            val gridLayoutManager = GridLayoutManager(context, 2)
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (adapter.getItemViewType(position) == GalleryType.DATE.value)
                        return gridLayoutManager.spanCount
                    else
                        return 1
                }
            }
            layoutManager = gridLayoutManager
        }
    }

    private fun setUpLinear() {
        gallery_list.apply {
            layoutManager = null
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initRecyclerView() {
        gallery_list.apply {
            this@ListGalleryFragment.adapter.setOnClickListener(this, onGalleryClicked)
            adapter = this@ListGalleryFragment.adapter
        }

        setUpLayoutManager((activity as MainActivity).readOnlyConfigs.getViewModeType())
    }

    fun onViewModeChanged(viewModeType: ViewModeType) {
        requestFetch(readOnlyFunction.invoke())
        setUpLayoutManager(viewModeType)
    }

    fun onCategoryModeChanged(categoryMode: CategoryMode) {
        requestFetch(readOnlyFunction.invoke())
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