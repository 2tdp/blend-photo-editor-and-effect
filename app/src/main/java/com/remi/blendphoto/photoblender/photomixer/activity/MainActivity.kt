package com.remi.blendphoto.photoblender.photomixer.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.activity.base.BaseActivity
import com.remi.blendphoto.photoblender.photomixer.activity.premium.PremiumActivity
import com.remi.blendphoto.photoblender.photomixer.adapter.CustomPagerAdapter
import com.remi.blendphoto.photoblender.photomixer.addview.ViewHome
import com.remi.blendphoto.photoblender.photomixer.callback.ICallBackCheck
import com.remi.blendphoto.photoblender.photomixer.callback.ICallBackItem
import com.remi.blendphoto.photoblender.photomixer.fragment.PickPictureFragment
import com.remi.blendphoto.photoblender.photomixer.model.picture.PicModel
import com.remi.blendphoto.photoblender.photomixer.sharepref.DataLocalManager
import com.remi.blendphoto.photoblender.photomixer.utils.Constant
import com.remi.blendphoto.photoblender.photomixer.utils.UtilsBitmap
import org.wysaid.nativePort.CGENativeLibrary
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseActivity() {

    lateinit var viewHome: ViewHome

    var w = 0F
    private lateinit var viewPagerAdapter: CustomPagerAdapter
    private var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewHome = ViewHome(this@MainActivity)
        setContentView(viewHome)
        w = resources.displayMetrics.widthPixels / 100F

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED)
                isGranted()
            else setIntent(PremiumActivity::class.java.name, false)
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                isGranted()
            else setIntent(PremiumActivity::class.java.name, false)
        }

        CGENativeLibrary.setLoadImageCallback(object : CGENativeLibrary.LoadImageCallback {
            //Notice: the 'name' passed in is just what you write in the rule, e.g: 1.jpg
            override fun loadImage(name: String, arg: Any?): Bitmap? {
                Log.d(Constant.TAG, "loadImage: $name")
                val am = assets
                val `is`: InputStream = try {
                    am.open("filter/$name")
                } catch (e: IOException) {
                    return BitmapFactory.decodeFile(name)
                }
                return BitmapFactory.decodeStream(`is`)
            }

            override fun loadImageOK(bmp: Bitmap, arg: Any?) {
                //The bitmap is which you returned at 'loadImage'.
                //You can call recycle when this function is called, or just keep it for further usage.
                bmp.recycle()
            }
        }, Object())

        onBackPressedDispatcher.addCallback(this@MainActivity, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed(true, false)
            }
        })

        setUpViewPagerImage()

        Timer().schedule(object : TimerTask() {
            override fun run() {
                Handler(Looper.getMainLooper()).post {
                    viewHome.viewPager.setCurrentItem(viewHome.viewPager.currentItem + 1, true)
                }
            }
        }, 0, 2000)

        evenClick()
    }

    private fun setUpViewPagerImage() {
        viewPagerAdapter = CustomPagerAdapter(this@MainActivity)

        val lstItem = ArrayList<Int>()
        lstItem.add(R.drawable.im_bg_main_1)
        lstItem.add(R.drawable.im_bg_main_2)
        lstItem.add(R.drawable.im_bg_main_3)
        viewPagerAdapter.setData(lstItem)

        viewHome.viewPager.adapter = viewPagerAdapter
    }

    private fun evenClick() {
        viewHome.ivSetting.setOnClickListener {
            setIntent(SettingsActivity::class.java.name, false)
        }
        viewHome.ivGallery.setOnClickListener {
            val pickPictureFragment = PickPictureFragment.newInstance(object : ICallBackItem {
                override fun callBack(ob: Any, position: Int) {
                    DataLocalManager.setPicture(ob as PicModel, Constant.PIC_CHOSE)
                    setIntent(EditActivity::class.java.name, false)
                }
            }, object : ICallBackCheck {
                override fun check(isCheck: Boolean) {

                }
            })
            replaceFragment(supportFragmentManager, pickPictureFragment, true, true, true)
        }
        viewHome.ivCamera.setOnClickListener { takePhoto() }
    }

    private fun takePhoto() {
        val photoFile = createImageFile()
        if (photoFile != null) {
            photoUri = FileProvider.getUriForFile(
                this@MainActivity,
                "com.remi.blendphoto.photoblender.photomixer",
                photoFile
            )
            launchTakePhoto.launch(photoUri)
        }
    }

    private val launchTakePhoto =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSaved ->
            if (isSaved) {
                val size = UtilsBitmap.getImageSize(this@MainActivity, photoUri)
                val ratio = size[0] / size[1]
                val pic = PicModel("-1", "takePhoto", ratio, photoUri.toString(), false)
                DataLocalManager.setPicture(pic, Constant.PIC_CHOSE)
                setIntent(EditActivity::class.java.name, false)
            }
        }

    private fun createImageFile(): File? {
        @SuppressLint("SimpleDateFormat")
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmm ss", Locale.ENGLISH).format(Date())
        val imageFileName = "REMI_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        var image: File? = null
        try {
            image = File.createTempFile(imageFileName, ".png", storageDir)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return image
    }

    private fun isGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Dexter.withContext(this@MainActivity)
                .withPermission(Manifest.permission.READ_MEDIA_IMAGES)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                    }

                    override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {
                        openSettingPermission()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissionRequest: PermissionRequest,
                        permissionToken: PermissionToken
                    ) {
                        Toast.makeText(
                            this@MainActivity,
                            R.string.des_permission,
                            Toast.LENGTH_SHORT
                        ).show()
                        permissionToken.continuePermissionRequest()
                    }
                }).check()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Dexter.withContext(this@MainActivity)
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(object : PermissionListener {
                        override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                        }

                        override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {
                            openSettingPermission()
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            permissionRequest: PermissionRequest,
                            permissionToken: PermissionToken
                        ) {
                            Toast.makeText(
                                this@MainActivity,
                                R.string.des_permission,
                                Toast.LENGTH_SHORT
                            ).show()
                            permissionToken.continuePermissionRequest()
                        }
                    }).check()
            } else {
                Dexter.withContext(this@MainActivity)
                    .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    .withListener(object : MultiplePermissionsListener {
                        override fun onPermissionRationaleShouldBeShown(
                            p0: MutableList<PermissionRequest>?,
                            p1: PermissionToken?
                        ) {
                            openSettingPermission()
                        }

                        override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                        }
                    }).check()
            }
        }
    }

    private fun openSettingPermission() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", this@MainActivity.packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}