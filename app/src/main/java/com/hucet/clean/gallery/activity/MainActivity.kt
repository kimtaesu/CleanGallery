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
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.gallery.category.CategoryType
import com.hucet.clean.gallery.gallery.fragment.ViewModeType
import com.hucet.clean.gallery.preference.SettingActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.layout_options_view.*


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
        initViewMode()
        initCategory()
        initToolbar()
        showGallaeryWithPermissionCheck()

    }

    private fun initCategory() {
        fun updateCategory(categoryType: CategoryType) {
            when (categoryType) {
                CategoryType.DIRECTORY -> {
                    category_mode.setImageResource(R.drawable.ic_category_directory_black_24dp)
                }
                CategoryType.DATE -> {
                    category_mode.setImageResource(R.drawable.ic_category_date_black_24dp)
                }
            }
        }
        updateCategory(config.categoryType)
        category_mode.setOnClickListener {
            config.categoryType = config.categoryType.toggle()
            updateCategory(config.categoryType)
        }
    }

    private fun initViewMode() {
        fun updateViewMode(viewModeType: ViewModeType) {
            when (viewModeType) {
                ViewModeType.GRID -> {
                    view_mode.setImageResource(R.drawable.ic_view_grid_black_24dp)
                }
                ViewModeType.LINEAR -> {
                    view_mode.setImageResource(R.drawable.ic_view_list_black_24dp)
                }
            }
        }
        updateViewMode(config.viewModeType)
        view_mode.setOnClickListener {
            config.viewModeType = config.viewModeType.toggle()
            updateViewMode(config.viewModeType)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.action_settings -> {
            startSettingActivity()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
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
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(medium.path))
            intent.setDataAndType(Uri.parse(medium.path), "video/*")
            startActivity(intent)
        } else {
            supportFragmentManager.beginTransaction()
                    .hide(galleryFragment)
                    .add(R.id.content, GalleryDetailFragment.newInstance(medium), GalleryDetailFragment.TAG)
                    .addToBackStack(null)
                    .commit()
        }
    }

    private fun startSettingActivity() {
        startActivity(Intent(this, SettingActivity::class.java))
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
