package com.hucet.clean.gallery

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import android.Manifest.permission
import android.R.string.cancel
import android.annotation.SuppressLint
import android.support.v7.app.AlertDialog
import permissions.dispatcher.*


@RuntimePermissions
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showGallaeryWithPermissionCheck()
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showGallaery() {
//        TODO next step
        supportFragmentManager.beginTransaction()
                .add(android.R.id.content, ListGalleryFragment.newInstance())
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
}
