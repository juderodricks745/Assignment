package com.interview.assignment.ui.dash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.interview.assignment.BuildConfig
import com.interview.assignment.R
import com.interview.assignment.data.wrappers.BottomWear
import com.interview.assignment.data.wrappers.TopWear
import com.interview.assignment.databinding.ActivityDashBinding
import com.interview.assignment.databinding.LayoutBottomDialogBinding
import com.interview.assignment.ui.base.BaseActivity
import com.interview.assignment.ui.base.IPermissionManager
import com.interview.assignment.ui.dash.adapter.BottomWearablesAdapter
import com.interview.assignment.ui.dash.adapter.TopWearablesAdapter
import com.interview.assignment.utils.AppConstants
import com.interview.assignment.utils.FilePathUtils
import com.interview.assignment.utils.gone
import com.interview.assignment.utils.visible
import java.io.File
import java.util.*

class DashActivity : BaseActivity(), View.OnClickListener {

    private var topWearPage = 0
    private var isTopWear = false
    private var bottomWearPage = 0
    private var capturedPhotoPath = ""
    private var topWearablesList = ArrayList<TopWear>()
    private var bottomWearablesList = ArrayList<BottomWear>()
    private lateinit var model: DashViewModel
    private lateinit var binding: ActivityDashBinding
    private var imageCapture: BottomSheetDialog? = null
    private var topWearAdapter: TopWearablesAdapter? = null
    private var bottomWearAdapter: BottomWearablesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dash)
        model = ViewModelProviders.of(this).get(DashViewModel::class.java)
        binding.model = model

        initEvents()
        createBottomDialog()
        setUpTopWearAdapter()
        setUpBottomWearAdapter()
        observeEvents()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.fabRefresh -> {
                binding.vpTopWear.currentItem = Math.abs(Random().nextInt(topWearablesList.size))
                binding.vpBottomWear.currentItem = Math.abs(Random().nextInt(bottomWearablesList.size))
            }
            R.id.fabFavorite -> {
                model.favOperation(topWearPage, bottomWearPage)
            }
            R.id.fabAddTop -> {
                isTopWear = true
                imageCapture?.show()
            }
            R.id.fabAddBottom -> {
                isTopWear = false
                imageCapture?.show()
            }
        }
    }

    private fun initEvents() {
        binding.fabRefresh.setOnClickListener(this)
        binding.fabFavorite.setOnClickListener(this)
        binding.fabAddTop.setOnClickListener(this)
        binding.fabAddBottom.setOnClickListener(this)
    }

    private fun setUpTopWearAdapter() {
        topWearAdapter = TopWearablesAdapter(supportFragmentManager)
        binding.vpTopWear.adapter = topWearAdapter

        binding.vpTopWear.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                topWearPage = position
                model.checkFavorite(topWearPage, bottomWearPage)
            }
        })
    }

    private fun setUpBottomWearAdapter() {
        bottomWearAdapter = BottomWearablesAdapter(supportFragmentManager)
        binding.vpBottomWear.adapter = bottomWearAdapter

        binding.vpBottomWear.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                bottomWearPage = position
                model.checkFavorite(topWearPage, bottomWearPage)
            }
        })

    }

    private fun observeEvents() {
        model.getTopWear()?.observe(this, androidx.lifecycle.Observer {
            if (it.isNotEmpty()) {
                binding.lblTopWear.gone()
            }
            topWearablesList.clear()
            topWearablesList.addAll(it)
            topWearAdapter?.addTopWearables(topWearablesList)
        })

        model.getBottomWear()?.observe(this, androidx.lifecycle.Observer {
            if (it.isNotEmpty()) {
                binding.lblBottomWear.gone()
            }
            bottomWearablesList.clear()
            bottomWearablesList.addAll(it)
            bottomWearAdapter?.addBottomWearables(bottomWearablesList)
        })

        model.favObserver().observe(this, Observer {
            if (it) binding.fabFavorite.visible() else binding.fabFavorite.gone()
        })

        model.refreshObserver().observe(this, Observer {
            if (it) binding.fabRefresh.visible() else binding.fabRefresh.gone()
        })
    }

    private fun createBottomDialog() {
        imageCapture = BottomSheetDialog(this)
        val bottomBinding:LayoutBottomDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.layout_bottom_dialog, null, false)
        imageCapture?.setContentView(bottomBinding.root)

        bottomBinding.lblCaptureCamera.setOnClickListener {
            IPermissionManager.ALL.withOperation {
                photoCapture()
                imageCapture?.dismiss()
            }
        }

        bottomBinding.lblGallery.setOnClickListener {
            IPermissionManager.READWRITE.withOperation {
                photoGallery()
                imageCapture?.dismiss()
            }
        }
    }

    private fun photoCapture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            val photoFile: File? = model.getImageSaveLocation()
            photoFile?.let {
                capturedPhotoPath = it.absolutePath
                val photoUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, it)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(intent, AppConstants.CAPTURE_PHOTO)
            }
        }
    }

    private fun photoGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, AppConstants.GALLERY_PHOTO)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AppConstants.CAPTURE_PHOTO && resultCode == Activity.RESULT_OK) {
            if(isTopWear) {
                val topWear = TopWear().apply { filePath = capturedPhotoPath }
                model.insertTopWear(topWear)
            } else {
                val bottomWear = BottomWear().apply { filePath = capturedPhotoPath }
                model.insertBottomWear(bottomWear)
            }
        } else if (requestCode == AppConstants.GALLERY_PHOTO && resultCode == Activity.RESULT_OK) {
           val galleryPath = FilePathUtils.getPath(this, data?.data!!)
            if(isTopWear) {
                val topWear = TopWear().apply { filePath = galleryPath }
                model.insertTopWear(topWear)
            } else {
                val bottomWear = BottomWear().apply { filePath = galleryPath }
                model.insertBottomWear(bottomWear)
            }
        }
    }

    private fun Array<String>.withOperation(body: () -> Unit) {
        requestPermission(this, object : IPermissionManager.CallBackListener {
            override fun onGiven() {
                body()
            }

            override fun onDenied() {
                alert(AppConstants.DISABLE_MSG) {
                    goToSettings()
                }
            }

            override fun onPermanentDenied() {
                alert(AppConstants.DISABLE_MSG) {
                    goToSettings()
                }
            }
        })
    }
}
