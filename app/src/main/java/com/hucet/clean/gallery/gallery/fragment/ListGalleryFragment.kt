package com.hucet.clean.gallery.gallery.fragment

import android.content.Intent
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
import com.hucet.clean.gallery.extension.isExternalStorageDir
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.gallery.adapter.GalleryType
import com.hucet.clean.gallery.gallery.category.CategoryType
import com.hucet.clean.gallery.inject.Injectable
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Directory
import com.hucet.clean.gallery.model.Medium
import com.hucet.clean.gallery.preference.SettingActivity
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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_gallery, null)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        presenter.fetchItems(curPath)

//        TODO Remove TEST CODE
        btn.setOnClickListener {
            config.layoutType = LayoutType.toggle(config.layoutType)
            setUpLayoutManager(config.layoutType)
        }
        category.text = config.categoryType.name
        category.setOnClickListener {
            config.categoryType = CategoryType.toggle(config.categoryType)
            category.text = config.categoryType.name
            presenter.fetchItems(curPath)
        }

        setting.setOnClickListener {
            startSettingActivity()
        }

    }

    private fun startSettingActivity() {
        startActivity(Intent(context, SettingActivity::class.java))
    }

    private val onGalleryClicked: (Basic) -> Unit = {
        when (it) {
            is Medium -> {
                (activity as MainActivity)?.onGalleryClicked.invoke(it)
            }
            is Directory -> {
                curPath = it.path
                adapter.clearItems()
                presenter.fetchItems(curPath)
            }
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

    private fun setUpLayoutManager(type: LayoutType) {
        when (type) {
            LayoutType.GRID -> setUpGrid()
            LayoutType.LINEAR -> setUpLinear()
        }
    }

    private fun setUpGrid() {
        btn.setText("grid")
        gallery_list.apply {
            layoutManager = null
            val gridLayoutManager = GridLayoutManager(context, 3)
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
        btn.setText("linear")
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
        setUpLayoutManager(config.layoutType)
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