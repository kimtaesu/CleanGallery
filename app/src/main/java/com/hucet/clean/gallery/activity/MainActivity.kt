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
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.activity.cache.MemoryCacheDrawable
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.config.ReadOnlyConfigs
import com.hucet.clean.gallery.extension.createFilterDialog
import com.hucet.clean.gallery.extension.createSortDialog
import com.hucet.clean.gallery.extension.startAsAnimation
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.fragment.GalleryDetailFragment
import com.hucet.clean.gallery.gallery.fragment.ListGalleryFragment
import com.hucet.clean.gallery.gallery.fragment.ViewModeType
import com.hucet.clean.gallery.model.Medium
import com.hucet.clean.gallery.preference.SettingActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import permissions.dispatcher.*
import timber.log.Timber
import javax.inject.Inject


@RuntimePermissions
class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var config: ApplicationConfig

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    private val galleryFragment: ListGalleryFragment = ListGalleryFragment.newInstance()

    lateinit var readOnlyConfigs: ReadOnlyConfigs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        readOnlyConfigs = config.getReadOnlyConfigs(this)
        showGallaeryWithPermissionCheck()
        initToolbar()
    }

    override fun onDestroy() {
        MemoryCacheDrawable.allStopAnimations()
        super.onDestroy()
    }

    private fun updateCategory(item: MenuItem, categoryMode: CategoryMode) {
        when (categoryMode) {
            CategoryMode.DIRECTORY -> {
                item.startAsAnimation(MemoryCacheDrawable.getDrawable(R.drawable.ic_directory2date_animation, this))
            }
            CategoryMode.DATE -> {
                item.startAsAnimation(MemoryCacheDrawable.getDrawable(R.drawable.ic_date2directory_animation, this))
            }
        }
    }

    private fun updateViewMode(item: MenuItem, viewMode: ViewModeType) {
        when (viewMode) {
            ViewModeType.GRID -> {
                item.startAsAnimation(MemoryCacheDrawable.getDrawable(R.drawable.ic_grid2list_animation, this))
            }
            ViewModeType.LINEAR -> {
                item.startAsAnimation(MemoryCacheDrawable.getDrawable(R.drawable.ic_list2grid_animation, this))
            }
        }
    }


    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val viewModeItem = menu?.findItem(R.id.action_view_mode)
        if (viewModeItem != null)
            updateViewMode(viewModeItem, readOnlyConfigs.getViewModeType())
        val categoryItem = menu?.findItem(R.id.action_category_mode)
        if (categoryItem != null)
            updateCategory(categoryItem, readOnlyConfigs.getCategoryMode())
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item?.itemId) {
        R.id.action_settings -> {
            startSettingActivity()
            true
        }
        R.id.action_category_mode -> {
            if (isFragmentShown(galleryFragment)) {
                val categoryMode = readOnlyConfigs.getCategoryMode().toggle()
                readOnlyConfigs = config.setReadOnlyConfigs(this, categoryMode)
                galleryFragment.onCategoryModeChanged(categoryMode)
                updateCategory(item, categoryMode)
            }
            true
        }
        R.id.action_view_mode -> {
            if (isFragmentShown(galleryFragment)) {
                val viewMode = readOnlyConfigs.getViewModeType().toggle()
                readOnlyConfigs = config.setReadOnlyConfigs(this, viewMode)
                galleryFragment.onViewModeChanged(viewMode)
                updateViewMode(item, viewMode)
            }
            true
        }
        R.id.action_sort -> {
            AlertDialog.Builder(this).createSortDialog(readOnlyConfigs, {
                readOnlyConfigs = config.setReadOnlyConfigs(this, it)
            }).show()
            true
        }
        R.id.action_filter -> {
            AlertDialog.Builder(this).createFilterDialog(readOnlyConfigs, {
                readOnlyConfigs = config.setReadOnlyConfigs(this, it)
            }).show()
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

    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
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

    val onGalleryClicked: (Medium, ImageView?) -> Unit = { medium: Medium, imageView: ImageView? ->
        Timber.d("onGalleryClicked ${medium}")
        if (medium.isVideo) {
            startVideoPlayer(medium)
        } else {
            val transitionName = ViewCompat.getTransitionName(imageView)
            supportFragmentManager.beginTransaction()
                    .addSharedElement(imageView, transitionName)
                    .replace(R.id.content, GalleryDetailFragment.newInstance(medium, transitionName), GalleryDetailFragment.TAG)
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
