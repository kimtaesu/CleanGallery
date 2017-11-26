package com.hucet.clean.gallery.activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import com.hucet.clean.gallery.OnGalleryClickedListener
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.activity.drawable.MemoryCacheDrawable
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.config.ConfigOrderedNotifier
import com.hucet.clean.gallery.config.OnConfigObserver
import com.hucet.clean.gallery.extension.createFilterDialog
import com.hucet.clean.gallery.extension.createSortDialog
import com.hucet.clean.gallery.extension.startAsAnimation
import com.hucet.clean.gallery.extension.toastTodo
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.view_mode.ViewModeType
import com.hucet.clean.gallery.gallery.glide.GlideApp
import com.hucet.clean.gallery.gallery.directory.PathLocationContext
import com.hucet.clean.gallery.gallery.sort.SortOptions
import com.hucet.clean.gallery.gallery.view_mode.ViewModeNavigator
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Directory
import com.hucet.clean.gallery.model.MediaType
import com.hucet.clean.gallery.model.Medium
import com.hucet.clean.gallery.presenter.Gallery
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import permissions.dispatcher.*
import timber.log.Timber
import javax.inject.Inject


class MainActivity() : AppCompatActivity(), HasSupportFragmentInjector, Gallery.View, OnConfigObserver {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    @Inject lateinit var pathLocationContext: PathLocationContext
    @Inject lateinit var presenter: Gallery.Presenter
    @Inject lateinit var config: ApplicationConfig
    @Inject lateinit var viewModeNavigator: ViewModeNavigator
    @Inject lateinit var configOrderedNotifier: ConfigOrderedNotifier

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


    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showRationaleForGallaery(request: PermissionRequest) {
        AlertDialog.Builder(this)
                .setMessage(R.string.permission_access_storage_rationale)
                .setPositiveButton(android.R.string.ok, { _, _ -> request.proceed() })
                .setNegativeButton(android.R.string.no, { _, _ -> request.cancel() })
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

    fun getCurrentAdapter(): GalleryAdapter? = gallery_list.adapter as? GalleryAdapter

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
        viewModeNavigator.setUpLayoutManager(this, config.viewModeType, gallery_list, { getCurrentAdapter() }, onGalleryClicked)
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

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.app_name)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        viewModeItem = menu?.findItem(R.id.action_view_mode)
        updateViewModeItem(viewModeItem, config.viewModeType)
        val categoryItem = menu?.findItem(R.id.action_category_mode)
        updateCategoryItem(categoryItem, config.categoryMode)
        return true
    }


    private fun updateCategoryItem(item: MenuItem?, categoryMode: CategoryMode) {
        when (categoryMode) {
            CategoryMode.DIRECTORY -> {
                item?.startAsAnimation(MemoryCacheDrawable.getDrawable(R.drawable.ic_directory2date_animation, this))
            }
            CategoryMode.DATE -> {
                item?.startAsAnimation(MemoryCacheDrawable.getDrawable(R.drawable.ic_date2directory_animation, this))
            }
        }
    }

    private fun updateViewModeItem(item: MenuItem?, viewMode: ViewModeType) {
        val isDate = !config.isCategoryDate()
        item?.isEnabled = isDate
        when (viewMode) {
            ViewModeType.GRID -> {
                if (isDate)
                    item?.startAsAnimation(MemoryCacheDrawable.getDrawable(R.drawable.ic_grid2list_animation, this))
                else
                    item?.startAsAnimation(MemoryCacheDrawable.getDrawable(R.drawable.ic_view_mode_list_disable, this))
            }
            ViewModeType.LINEAR -> {
                if (isDate)
                    item?.startAsAnimation(MemoryCacheDrawable.getDrawable(R.drawable.ic_list2grid_animation, this))
                else
                    item?.startAsAnimation(MemoryCacheDrawable.getDrawable(R.drawable.ic_view_mode_grid_disable, this))
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                toastTodo(this)
            }
            R.id.action_category_mode -> {
                with(config) {
                    configOrderedNotifier.categoryNotify(categoryMode.toggle())
                    updateCategoryItem(item, categoryMode)
                }
            }
            R.id.action_view_mode -> {
                with(config) {
                    configOrderedNotifier.viewModeNotify(viewModeType.toggle())
                    updateViewModeItem(item, viewModeType)
                }
            }
            R.id.action_sort -> {
                AlertDialog.Builder(this).createSortDialog(config.categoryMode,
                        config.sortOptionType,
                        pathLocationContext.isRoot(),
                        { selectedSort ->
                            configOrderedNotifier.sortOptionNotify(selectedSort)
                        })
                        .show()
            }
            R.id.action_filter -> {
                AlertDialog.Builder(this).createFilterDialog(config.filterdType, { seletecFilter ->
                    configOrderedNotifier.filterNotify(seletecFilter)
                }).show()
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
        return true
    }

    override fun onSortChanged(sortOptions: SortOptions) {
        getCurrentAdapter()?.clearItems()
        setUpLayout()
        requestFetch()
    }

    override fun onCategoryChanged(categoryMode: CategoryMode) {
        getCurrentAdapter()?.clearItems()
        updateViewModeItem(viewModeItem, config.viewModeType)
        setUpLayout()
        requestFetch()
    }

    override fun onFilterChanged(filterBit: Long) {
        getCurrentAdapter()?.clearItems()
        setUpLayout()
        requestFetch()
    }

    override fun onViewModeChanged(viewModeType: ViewModeType) {
        getCurrentAdapter()?.clearItems()
        setUpLayout()
        requestFetch()
    }

    private fun setUpLayout() {
        viewModeNavigator.setUpLayoutManager(this@MainActivity,
                config.viewModeType, gallery_list, { getCurrentAdapter() }, onGalleryClicked)
    }

    private val onGalleryClicked: OnGalleryClickedListener = { basic: Basic, imageView: ImageView ->
        Timber.d("onGalleryClicked ${basic}")
        when (basic) {
            is Medium -> {
                if (basic.mediaType == MediaType.VIDEO) {
                    startVideoPlayer(basic)
                } else {
                    startDetailActivity(imageView, basic)
                }
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
        if (!canBack())
            return

        super.onBackPressed()
    }

    override fun onDestroy() {
        MemoryCacheDrawable.allStopAnimations()
        super.onDestroy()
    }
}
