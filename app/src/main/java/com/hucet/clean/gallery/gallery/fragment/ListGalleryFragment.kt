package com.hucet.clean.gallery.gallery.fragment

import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.activity.MainActivity
import com.hucet.clean.gallery.config.ReadOnlyConfigs
import com.hucet.clean.gallery.extension.isExternalStorageDir
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.gallery.adapter.GalleryType
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.fragment.switchable.ViewModeSwichable
import com.hucet.clean.gallery.inject.Injectable
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Directory
import com.hucet.clean.gallery.model.Medium
import com.hucet.clean.gallery.onGalleryClickedListener
import com.hucet.clean.gallery.presenter.Gallery
import kotlinx.android.synthetic.main.fragment_gallery.*
import javax.inject.Inject


/**
 * Created by taesu on 2017-10-30.
 */
class ListGalleryFragment : Fragment(), Gallery.View, Injectable {
    @Inject lateinit var presenter: Gallery.Presenter
    @Inject lateinit var mapViewModeSetUp: Map<ViewModeType, @JvmSuppressWildcards ViewModeSwichable>

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
        presenter.fetchItems(curPath, readOnlyConfigs)
    }

    private val onGalleryClicked: onGalleryClickedListener = { basic: Basic, imageView: ImageView? ->
        when (basic) {
            is Medium -> {
                ViewCompat.setTransitionName(imageView, basic.name)
                (activity as MainActivity).onGalleryClicked.invoke(basic, imageView)
            }
            is Directory -> {
                curPath = basic.path
                getCurrentAdapter()?.clearItems()
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
        val items = ArrayList<Basic>()
        items.addAll(getItems())
        when (type) {
            ViewModeType.GRID -> {
                val gridLayoutManager = GridLayoutManager(context, 2)
                gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (getCurrentAdapter()?.getItemViewType(position) == GalleryType.DATE.value)
                            gridLayoutManager.spanCount
                        else
                            1
                    }
                }

                mapViewModeSetUp[type]?.switchViewMode(gallery_list,
                        gridLayoutManager,
                        items,
                        onGalleryClicked)
            }
            ViewModeType.LINEAR -> {
                mapViewModeSetUp[type]?.switchViewMode(gallery_list,
                        LinearLayoutManager(context),
                        items,
                        onGalleryClicked)
            }
        }
    }


    private fun initRecyclerView() {
        setUpLayoutManager((activity as MainActivity).readOnlyConfigs.getViewModeType())
    }

    fun onViewModeChanged(viewModeType: ViewModeType) {
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

    fun getCurrentAdapter(): GalleryAdapter? = gallery_list.adapter as? GalleryAdapter

    private fun getItems(): List<Basic> {
        val adapter = getCurrentAdapter()
        adapter ?: return emptyList()
        return adapter.Items
    }
}