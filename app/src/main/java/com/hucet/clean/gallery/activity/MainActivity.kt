package com.hucet.clean.gallery.activity

import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.gallery.fragment.GalleryDetailFragment
import com.hucet.clean.gallery.gallery.fragment.ListGalleryFragment
import com.hucet.clean.gallery.model.Medium
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import permissions.dispatcher.*
import timber.log.Timber
import javax.inject.Inject
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuItem
import com.hucet.clean.gallery.activity.cache.MemoryCacheDrawable
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.extension.showFilterDialog
import com.hucet.clean.gallery.extension.showSortDialog
import com.hucet.clean.gallery.extension.startAsAnimatable
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.fragment.ViewModeType
import com.hucet.clean.gallery.preference.SettingActivity
import kotlinx.android.synthetic.main.activity_main.*


@RuntimePermissions
class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var config: ApplicationConfig

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    val galleryFragment: ListGalleryFragment = ListGalleryFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showGallaeryWithPermissionCheck()
        initToolbar()
    }

    private fun updateCategory(item: MenuItem, categoryMode: CategoryMode) {
        when (categoryMode) {
            CategoryMode.DIRECTORY -> {
                item.setIcon(MemoryCacheDrawable.getDrawable(R.drawable.ic_directory2date_animation, this))
                item.icon.startAsAnimatable()
            }
            CategoryMode.DATE -> {
                item.setIcon(MemoryCacheDrawable.getDrawable(R.drawable.ic_date2directory_animation, this))
                item.icon.startAsAnimatable()
            }
        }
    }

    private fun updateViewMode(item: MenuItem, viewMode: ViewModeType) {
        when (viewMode) {
            ViewModeType.GRID -> {
                item.setIcon(MemoryCacheDrawable.getDrawable(R.drawable.ic_grid_to_list_animation, this))
                item.icon.startAsAnimatable()
            }
            ViewModeType.LINEAR -> {
                item.setIcon(MemoryCacheDrawable.getDrawable(R.drawable.ic_list2grid_animation, this))
                item.icon.startAsAnimatable()
            }
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val viewMode = menu?.findItem(R.id.action_view_mode)
        if (viewMode != null)
            updateViewMode(viewMode, config.viewModeType)
        val categoryMode = menu?.findItem(R.id.action_category_mode)
        if (categoryMode != null)
            updateCategory(categoryMode, config.categoryMode)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item?.itemId) {
        R.id.action_settings -> {
            startSettingActivity()
            true
        }
        R.id.action_category_mode -> {
            if (isFragmentShown(galleryFragment)) {
                val categoryMode = config.categoryMode.toggle()
                galleryFragment.onCategoryModeChanged(categoryMode)
                updateCategory(item, categoryMode)
            }
            true
        }
        R.id.action_view_mode -> {
            if (isFragmentShown(galleryFragment)) {
                val viewMode = config.viewModeType.toggle()
                galleryFragment.onViewModeChanged(viewMode)
                updateViewMode(item, viewMode)
            }
            true
        }
        R.id.action_sort -> {
            AlertDialog.Builder(this).showSortDialog()
            true
        }
        R.id.action_filter -> {
            AlertDialog.Builder(this).showFilterDialog()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun startSettingActivity() {
        startActivity(Intent(this, SettingActivity::class.java))
    }

    private fun isFragmentShown(fragment: Fragment): Boolean {
        return fragment != null && fragment.isVisible
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showGallaery() {
        supportFragmentManager.beginTransaction()
                .add(R.id.content, galleryFragment)
                .commit()
    }

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

    val onGalleryClicked: (Medium) -> Unit = { medium: Medium ->
        Timber.d("onGalleryClicked ${medium}")
        if (medium.isVideo) {
            startVideoPlayer(medium)
        } else {
            supportFragmentManager.beginTransaction()
                    .hide(galleryFragment)
                    .add(R.id.content, GalleryDetailFragment.newInstance(medium), GalleryDetailFragment.TAG)
                    .addToBackStack(null)
                    .commit()
        }
    }


    private fun startVideoPlayer(medium: Medium) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(medium.path))
        intent.setDataAndType(Uri.parse(medium.path), "video/*")
        startActivity(intent)

    }

    override fun onBackPressed() {
        if (GalleryDetailFragment.isVisible(supportFragmentManager)) {
            supportFragmentManager.popBackStackImmediate()
            return
        }

        if (!galleryFragment.onBackPressed())
            return

        super.onBackPressed()
    }
}
