package com.hucet.clean.gallery.activity

import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.gallery.detail.GalleryDetailFragment
import com.hucet.clean.gallery.gallery.list.ListGalleryFragment
import com.hucet.clean.gallery.model.Medium
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import permissions.dispatcher.*
import timber.log.Timber
import javax.inject.Inject


@RuntimePermissions
class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    val galleryFragment: ListGalleryFragment = ListGalleryFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showGallaeryWithPermissionCheck()
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showGallaery() {
        supportFragmentManager.beginTransaction()
                .add(android.R.id.content, galleryFragment)
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
        supportFragmentManager.beginTransaction()
                .hide(galleryFragment)
                .add(android.R.id.content, GalleryDetailFragment.newInstance(medium), GalleryDetailFragment.TAG)
                .addToBackStack(null)
                .commit()

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
