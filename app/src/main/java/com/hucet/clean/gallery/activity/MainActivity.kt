package com.hucet.clean.gallery.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import com.hucet.clean.gallery.OnGalleryClickedListener
import com.hucet.clean.gallery.OnViewModechangedListener
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.activity.cache.MemoryCacheDrawable
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.extension.startAsAnimation
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.gallery.adapter.GalleryType
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.fragment.GalleryDetailFragment
import com.hucet.clean.gallery.gallery.fragment.ViewModeType
import com.hucet.clean.gallery.gallery.glide.GlideApp
import com.hucet.clean.gallery.gallery.fragment.switchable.ViewModeSwichable
import com.hucet.clean.gallery.gallery.directory.PathLocationContext
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Directory
import com.hucet.clean.gallery.model.MediaType
import com.hucet.clean.gallery.model.Medium
import com.hucet.clean.gallery.preference.SettingActivity
import com.hucet.clean.gallery.presenter.Gallery
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import permissions.dispatcher.*
import timber.log.Timber
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector, Gallery.View {
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    @Inject lateinit var pathLocationContext: PathLocationContext
    @Inject lateinit var presenter: Gallery.Presenter
    @Inject lateinit var mapViewModeSetUp: Map<ViewModeType, @JvmSuppressWildcards ViewModeSwichable>
    @Inject lateinit var config: ApplicationConfig


    private var onViewModechangedListener: OnViewModechangedListener? = null

    private var viewModeItem: MenuItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // TODO Revert it
//        showGallaeryWithPermissionCheck()
        initToolbar()
        showGallaery()
    }

    // TODO Revert it
//    @SuppressLint("NeedOnRequestPermissionsResult")
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        onRequestPermissionsResult(requestCode, grantResults)
//    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showGallaery() {
        lifecycle.addObserver(presenter)
        initRecyclerView()
        requestFetch()
    }

    private fun setUpLayoutManager(type: ViewModeType) {
        val items = ArrayList<Basic>()
        items.addAll(getItems())
        when (type) {
            ViewModeType.GRID -> {
                val gridLayoutManager = GridLayoutManager(this, 2)
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
                        GlideApp.with(this),
                        items,
                        onGalleryClicked
                )
            }
            ViewModeType.LINEAR -> {
                mapViewModeSetUp[type]?.switchViewMode(gallery_list,
                        LinearLayoutManager(this),
                        GlideApp.with(this),
                        items,
                        onGalleryClicked)
            }
        }
    }

    fun getCurrentAdapter(): GalleryAdapter? = gallery_list.adapter as? GalleryAdapter

    private fun getItems(): List<Basic> {
        val adapter = getCurrentAdapter()
        adapter ?: return emptyList()
        return adapter.Items
    }

    override fun showProgress() {
//        Toast.makeText(context, "showProgress", Toast.LENGTH_SHORT).show()
    }

    override fun hideProgress() {
//        Toast.makeText(context, "hideProgress", Toast.LENGTH_SHORT).show()
    }

    override fun showError() {
//        Toast.makeText(context, "showError", Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerView() {
        setUpLayoutManager(config.viewModeType)
        gallery_list.apply {
            setRecyclerListener({ viewHolder ->
                val thumbnailView = viewHolder.itemView.findViewById<ImageView>(R.id.thumbnail)
                if (thumbnailView != null)
                    GlideApp.with(this).clear(thumbnailView)
            })
        }
    }

    private fun requestFetch() {
        presenter.fetchItems(pathLocationContext, false)
    }

    fun onCategoryModeChanged() {
        getCurrentAdapter()?.syncClearItems()
        setUpLayoutManager(config.viewModeType)
        requestFetch()
    }

    fun onFilterChanged() {
        requestFetch()
    }

    fun onSortChanged() {
        requestFetch()
    }

    override fun onDestroy() {
        MemoryCacheDrawable.allStopAnimations()
        super.onDestroy()
    }

    private fun updateCategory(item: MenuItem?, categoryMode: CategoryMode) {
        when (categoryMode) {
            CategoryMode.DIRECTORY -> {
                item?.startAsAnimation(MemoryCacheDrawable.getDrawable(R.drawable.ic_directory2date_animation, this))
            }
            CategoryMode.DATE -> {
                item?.startAsAnimation(MemoryCacheDrawable.getDrawable(R.drawable.ic_date2directory_animation, this))
            }
        }
    }

    private fun updateViewMode(item: MenuItem?, viewMode: ViewModeType, categoryMode: CategoryMode) {
        val isRestrict = !isGridRestriction(categoryMode)
        item?.isEnabled = isRestrict
        when (viewMode) {
            ViewModeType.GRID -> {
                if (isRestrict)
                    item?.startAsAnimation(MemoryCacheDrawable.getDrawable(R.drawable.ic_grid2list_animation, this))
                else
                    item?.startAsAnimation(MemoryCacheDrawable.getDrawable(R.drawable.ic_view_mode_list_disable, this))
            }
            ViewModeType.LINEAR -> {
                if (isRestrict)
                    item?.startAsAnimation(MemoryCacheDrawable.getDrawable(R.drawable.ic_list2grid_animation, this))
                else
                    item?.startAsAnimation(MemoryCacheDrawable.getDrawable(R.drawable.ic_view_mode_grid_disable, this))
            }
        }
    }


    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.app_name)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        viewModeItem = menu?.findItem(R.id.action_view_mode)
        updateViewMode(viewModeItem, config.viewModeType, config.categoryMode)
        val categoryItem = menu?.findItem(R.id.action_category_mode)
        updateCategory(categoryItem, config.categoryMode)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item?.itemId) {
//        R.id.action_settings -> {
//            startSettingActivity()
//            true
//        }
        R.id.action_category_mode -> {
//            val categoryMode = readOnlyConfigs.getCategoryMode().toggle()
//
//            readOnlyConfigs = config.ReadOnlyConfigBuild {
//                categoryMode(categoryMode)
//                if (isGridRestriction(categoryMode))
//                    viewMode(ViewModeType.GRID)
//            }
//            updateViewMode(viewModeItem, readOnlyConfigs.getViewModeType(), readOnlyConfigs.getCategoryMode())
//
//            if (isGridRestriction(categoryMode)) {
//                pathLocationContext.moveRoot()
//                refreshSortType()
//            }
//            onCategoryModeChanged(readOnlyConfigs)
//            updateCategory(item, categoryMode)
            true
        }
        R.id.action_view_mode -> {
//            val viewMode = readOnlyConfigs.getViewModeType().toggle()
//            readOnlyConfigs = config.ReadOnlyConfigBuild {
//                viewMode(viewMode)
//            }
//            onViewModechangedListener?.invoke(readOnlyConfigs.getViewModeType())
//            updateViewMode(item, viewMode, readOnlyConfigs.getCategoryMode())
            true
        }
        R.id.action_sort -> {
//            AlertDialog.Builder(this).createSortDialog(readOnlyConfigs, config.isRoot(), {
//                readOnlyConfigs = config.ReadOnlyConfigBuild {
//                    sortType(it)
//                }
//                onSortChanged(readOnlyConfigs)
//            }).show()
            true
        }
        R.id.action_filter -> {
//            AlertDialog.Builder(this).createFilterDialog(readOnlyConfigs, {
//                readOnlyConfigs = config.ReadOnlyConfigBuild {
//                    filterType(it)
//                }
//                onFilterChanged(readOnlyConfigs)
//            }).show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun isGridRestriction(categoryMode: CategoryMode): Boolean {
        if (categoryMode == CategoryMode.DATE)
            return true
        return false
    }


    fun setOnViewModeChangedListener(onViewModeChanged: (ViewModeType) -> Unit) {
        onViewModechangedListener = onViewModeChanged
    }

//    @SuppressLint("NeedOnRequestPermissionsResult")
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        onRequestPermissionsResult(requestCode, grantResults)
//    }


    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showRationaleForGallaery(request: PermissionRequest) {
        AlertDialog.Builder(this)
                .setMessage(R.string.permission_access_storage_rationale)
                .setPositiveButton(android.R.string.ok, { dialog, button -> request.proceed() })
                .setNegativeButton(android.R.string.no, { dialog, button -> request.cancel() })
                .show()
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showDeniedForGallaery() {
        Toast.makeText(this, R.string.permission_access_storage_denied, Toast.LENGTH_SHORT).show()
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showNeverAskForGallaery() {
        Toast.makeText(this, R.string.permission_access_storage_never_ask_again, Toast.LENGTH_SHORT).show()
    }

    val onGalleryClicked: OnGalleryClickedListener = { basic: Basic, imageView: ImageView ->
        Timber.d("onGalleryClicked ${basic}")
        when (basic) {
            is Medium -> {
                if (basic.mediaType == MediaType.VIDEO) {
                    startVideoPlayer(basic)
                } else {
                    startDetailActivity(imageView, basic)
                }
                ViewCompat.setTransitionName(imageView, basic.name)
            }
            is Directory -> {
                pathLocationContext.movePath(basic.path)
                requestFetch()
            }
        }
    }


    private fun startDetailActivity(shareElement: ImageView, medium: Medium) {
//        val transitionName = ViewCompat.getTransitionName(shareElement)
        val intent = Intent(this, GalleryDetailActivity::class.java).also {
            it.putExtra(GalleryDetailActivity.BUNDLE_KEY_MEDIUM, medium)
//            it.putExtra(GalleryDetailActivity.EXTRA_TRANSITION_NAME, transitionName)
        }

//        val option = ActivityOptionsCompat.makeSceneTransitionAnimation(this, shareElement, transitionName)
//        startActivity(intent, option.toBundle())
        startActivity(intent)
    }

    private fun startVideoPlayer(medium: Medium) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(medium.path))
        intent.setDataAndType(Uri.parse(medium.path), "video/*")
        startActivity(intent)

    }

    private fun canBack(): Boolean {
        if (!pathLocationContext.isRoot()) {
            pathLocationContext.moveRoot()
            requestFetch()
            return false
        }
        return true
    }

    override fun onBackPressed() {
        if (GalleryDetailFragment.isVisible(supportFragmentManager)) {
            supportFragmentManager.popBackStackImmediate()
            return
        }

        if (!canBack())
            return

        super.onBackPressed()
    }

}
