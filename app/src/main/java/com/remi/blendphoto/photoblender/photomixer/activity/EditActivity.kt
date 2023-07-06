package com.remi.blendphoto.photoblender.photomixer.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BlendMode
import android.graphics.PorterDuff.Mode
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.remi.blendphoto.addtext.customview.OnSeekbarResult
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.activity.base.BaseActivity
import com.remi.blendphoto.photoblender.photomixer.activity.premium.GoPremiumActivity
import com.remi.blendphoto.photoblender.photomixer.adapter.TypeBlendAdapter
import com.remi.blendphoto.photoblender.photomixer.adapter.ViewPagerAddFragmentsAdapter
import com.remi.blendphoto.photoblender.photomixer.addview.ViewEdit
import com.remi.blendphoto.photoblender.photomixer.addview.dialog.ViewDialogText
import com.remi.blendphoto.photoblender.photomixer.addview.viewitem.ViewItemTabBlend
import com.remi.blendphoto.photoblender.photomixer.callback.ICallBackCheck
import com.remi.blendphoto.photoblender.photomixer.callback.ICallBackItem
import com.remi.blendphoto.photoblender.photomixer.data.DataBlend
import com.remi.blendphoto.photoblender.photomixer.fragment.FilterImgFragment
import com.remi.blendphoto.photoblender.photomixer.fragment.ItemPagerBlendFragment
import com.remi.blendphoto.photoblender.photomixer.fragment.PickPictureFragment
import com.remi.blendphoto.photoblender.photomixer.model.BlendModel
import com.remi.blendphoto.photoblender.photomixer.model.ImgBlendModel
import com.remi.blendphoto.photoblender.photomixer.model.picture.PicModel
import com.remi.blendphoto.photoblender.photomixer.sharepref.DataLocalManager
import com.remi.blendphoto.photoblender.photomixer.utils.Constant
import com.remi.blendphoto.photoblender.photomixer.utils.Utils
import com.remi.blendphoto.photoblender.photomixer.utils.UtilsBitmap
import com.yalantis.ucrop.UCrop
import java.io.File

class EditActivity : BaseActivity() {

    lateinit var viewEdit: ViewEdit
    private var w = 0F

    private var typeBlendAdapter: TypeBlendAdapter? = null

    private var blendModel: BlendModel? = null
    private var imgBlendModel: ImgBlendModel? = null
    private lateinit var currentImg: Bitmap
    private lateinit var currentImgBlend: Bitmap
    private lateinit var intent: Intent
    private var typeErase = 0
    private var indexSizeErase = 5
    private var indexSizeUnErase = 5
    private var isFirst = true
    private var isAvatar = true
    private var isErase = false
    private var isErased = false

    private var isFilterFragVisible = false
    private var isShowUpBlend = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewEdit = ViewEdit(this@EditActivity)
        setContentView(viewEdit)
        w = resources.displayMetrics.widthPixels / 100F

        onBackPressedDispatcher.addCallback(
            this@EditActivity,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isFilterFragVisible) {
                        supportFragmentManager.popBackStack(
                            "FilterImgFragment",
                            POP_BACK_STACK_INCLUSIVE
                        )
                        isFilterFragVisible = false
                    } else if (isShowUpBlend) {
                        viewEdit.showChooseBlend("hide")
                        isShowUpBlend = false
                    } else if (isErase) outErase()
                    else showDialogCancel()
                }
            })

        initView()
        evenClick()
    }

    override fun onResume() {
        super.onResume()
        if (!isFirst) {
            if (UCrop.getOutput(intent) == null) finish()
            else {
                if (imgBlendModel == null)
                    setBlend(
                        true,
                        "in_app",
                        ImgBlendModel("blend_color", "image_5.webp", false, false, true)
                    )
                else setBlend(true, "in_app", imgBlendModel!!)
            }
        } else isFirst = false
    }

    private fun initView() {
        viewEdit.chooseAvatar("avatar")
        isAvatar = true
        viewEdit.sbOpacity.seekbar.setProgress((viewEdit.ivBlendRoot.getAlphaPaint() * 100 / 255))
        val pic = DataLocalManager.getPicture(Constant.PIC_CHOSE)
        pic?.let { goToCrop(pic) }
    }

    private fun evenClick() {
        viewEdit.rlBlend.setOnClickListener {
            if (checkLoading()) return@setOnClickListener
            if (viewEdit.vShowBlend.isVisible) viewEdit.showBlend(1)
            else {
                viewEdit.showBlend(0)
                showUpBlend()
            }
        }
        viewEdit.ivAvatar.ivEdit.setOnClickListener {
            if (checkLoading()) return@setOnClickListener
            val pickPictureFragment = PickPictureFragment.newInstance(object : ICallBackItem {
                override fun callBack(ob: Any, position: Int) {
                    DataLocalManager.setPicture(ob as PicModel, Constant.PIC_CHOSE)
                    goToCrop(ob)
                    isFirst = false
                }
            }, object : ICallBackCheck {
                override fun check(isCheck: Boolean) {

                }
            })
            replaceFragment(supportFragmentManager, pickPictureFragment, true, true, true)
        }
        viewEdit.ivAvatar.setOnClickListener {
            if (checkLoading()) return@setOnClickListener
            viewEdit.chooseAvatar("avatar")
            isAvatar = true
            viewEdit.sbOpacity.seekbar.setProgress((viewEdit.ivBlendRoot.getAlphaPaint() * 100 / 255))
        }
        viewEdit.ivAvatar2.setOnClickListener {
            if (checkLoading()) return@setOnClickListener
            viewEdit.chooseAvatar("avatar2")
            isAvatar = false
            viewEdit.sbOpacity.seekbar.setProgress((viewEdit.ivBlendRoot.getAlphaPaintBlend() * 100 / 255))
        }
        viewEdit.ivAvatar2.ivEdit.setOnClickListener {
            if (checkLoading()) return@setOnClickListener
            isShowUpBlend = true
            viewEdit.showChooseBlend("show")
            chooseImgBlend()
        }
        viewEdit.rlMain.setOnClickListener {
            if (checkLoading()) return@setOnClickListener
            if (viewEdit.viewChooseImgBlend.isVisible) viewEdit.showChooseBlend("hide")
            if (viewEdit.viewChooseErase.isVisible) {
                outErase()
                viewEdit.showErase("hide")
            }
        }
        viewEdit.viewToolbar.ivBack.setOnClickListener { showDialogCancel() }

        //swap
        viewEdit.ivConvert.setOnClickListener {
            if (checkLoading()) return@setOnClickListener
            viewEdit.ivBlendRoot.isErase = false
            swap()
        }

        //flip
        viewEdit.viewToolbar.ivFlipX.setOnClickListener {
            if (checkLoading()) return@setOnClickListener
            if (!isAvatar) viewEdit.ivBlendRoot.flipX()
            else {
                viewEdit.ivBlendRoot.flipErase(true, false)
                currentImg = UtilsBitmap.createFlippedBitmap(currentImg, true, false)
            }
        }
        viewEdit.viewToolbar.ivFlipY.setOnClickListener {
            if (checkLoading()) return@setOnClickListener
            if (!isAvatar) viewEdit.ivBlendRoot.flipY()
            else {
                viewEdit.ivBlendRoot.flipErase(false, true)
                currentImg = UtilsBitmap.createFlippedBitmap(currentImg, false, true)
            }
        }

        //click erase
        viewEdit.viewToolbar.ivErase.setOnClickListener {
            if (checkLoading()) return@setOnClickListener
            isErase = true
            viewEdit.ivBlendRoot.apply {
                isMove = false
                isErase = false

                invalidate()
            }
            if (!isErased) viewEdit.ivErase.setBitmap(currentImg)
            viewEdit.ivErase.apply {
                visibility = View.VISIBLE
                setEraseOffset(typeErase, (19.07 * w).toInt())
            }
            viewEdit.viewChooseErase.sbOpacity.seekbar.setProgress(
                if (typeErase == 2 || typeErase == 3) viewEdit.ivErase.getAlphaErase() * 100 / 255
                else viewEdit.ivErase.getAlphaUnErase() * 100 / 255
            )
            viewEdit.apply {
                showErase("show")
                viewChooseErase.chooseErase(typeErase)
            }
        }
        viewEdit.viewChooseErase.ivErase1.setOnClickListener {
            if (checkLoading()) return@setOnClickListener
            viewEdit.viewChooseErase.apply {
                chooseErase(0)
                viewEdit.viewChooseErase.sbOpacity.apply {
                    tvProgress.text = (viewEdit.ivErase.getAlphaUnErase() * 100 / 255).toString()
                    seekbar.setProgress(viewEdit.ivErase.getAlphaUnErase() * 100 / 255)
                }
                viewEdit.viewChooseErase.sbSize.apply {
                    tvProgress.text = (indexSizeUnErase).toString()
                    seekbar.setProgress(indexSizeUnErase)
                }
            }
            viewEdit.ivErase.setEraseOffset(0, (indexSizeUnErase * 3.81f * w).toInt())
            typeErase = 0

        }
        viewEdit.viewChooseErase.ivErase2.setOnClickListener {
            if (checkLoading()) return@setOnClickListener
            viewEdit.viewChooseErase.apply {
                chooseErase(1)
                viewEdit.viewChooseErase.sbOpacity.apply {
                    tvProgress.text = (viewEdit.ivErase.getAlphaUnErase() * 100 / 255).toString()
                    seekbar.setProgress(viewEdit.ivErase.getAlphaUnErase() * 100 / 255)
                }
                viewEdit.viewChooseErase.sbSize.apply {
                    tvProgress.text = (indexSizeUnErase).toString()
                    seekbar.setProgress(indexSizeUnErase)
                }
            }
            viewEdit.ivErase.setEraseOffset(1, (indexSizeUnErase * 3.81f * w).toInt())
            typeErase = 1
        }
        viewEdit.viewChooseErase.ivErase3.setOnClickListener {
            if (checkLoading()) return@setOnClickListener
            viewEdit.viewChooseErase.apply {
                chooseErase(2)
                viewEdit.viewChooseErase.sbOpacity.apply {
                    tvProgress.text = (viewEdit.ivErase.getAlphaErase() * 100 / 255).toString()
                    seekbar.setProgress(viewEdit.ivErase.getAlphaErase() * 100 / 255)
                }
                viewEdit.viewChooseErase.sbSize.apply {
                    tvProgress.text = (indexSizeErase).toString()
                    seekbar.setProgress(indexSizeErase)
                }
            }
            viewEdit.ivErase.setEraseOffset(2, (indexSizeErase * 3.81f * w).toInt())
            typeErase = 2
        }
        viewEdit.viewChooseErase.ivErase4.setOnClickListener {
            if (checkLoading()) return@setOnClickListener
            viewEdit.viewChooseErase.apply {
                chooseErase(3)
                viewEdit.viewChooseErase.sbOpacity.apply {
                    tvProgress.text = (viewEdit.ivErase.getAlphaErase() * 100 / 255).toString()
                    seekbar.setProgress(viewEdit.ivErase.getAlphaErase() * 100 / 255)
                }
                viewEdit.viewChooseErase.sbSize.apply {
                    tvProgress.text = (indexSizeErase).toString()
                    seekbar.setProgress(indexSizeErase)
                }
            }
            viewEdit.ivErase.setEraseOffset(3, (indexSizeErase * 3.81f * w).toInt())
            typeErase = 3
        }
        viewEdit.viewToolbar.ivRight.setOnClickListener {
            if (checkLoading()) return@setOnClickListener
            outErase()
        }

        //alpha erase/un erase
        viewEdit.viewChooseErase.sbOpacity.seekbar.onSeekbarResult = object : OnSeekbarResult {
            override fun onDown(v: View) {

            }

            override fun onMove(v: View, value: Int) {
                if (checkLoading()) return
                viewEdit.viewChooseErase.sbOpacity.tvProgress.text = value.toString()
                if (typeErase == 2 || typeErase == 3)
                    viewEdit.ivErase.setAlphaErase(value * 255 / 100)
                else viewEdit.ivErase.setAlphaUnErase(value * 255 / 100)
            }

            override fun onUp(v: View, value: Int) {

            }

        }

        //size erase/ un erase
        viewEdit.viewChooseErase.sbSize.seekbar.onSeekbarResult = object : OnSeekbarResult {
            override fun onDown(v: View) {

            }

            override fun onMove(v: View, value: Int) {
                if (checkLoading()) return
                viewEdit.viewChooseErase.sbSize.tvProgress.text = value.toString()
                if (typeErase == 0 || typeErase == 1) indexSizeUnErase = value
                else indexSizeErase = value
                if (value == 0) viewEdit.ivErase.setEraseOffset(typeErase, (3.81f * w).toInt())
                else viewEdit.ivErase.setEraseOffset(typeErase, (value * 3.81f * w).toInt())
            }

            override fun onUp(v: View, value: Int) {

            }

        }

        //alpha image/blend
        viewEdit.sbOpacity.seekbar.onSeekbarResult = object : OnSeekbarResult {
            override fun onDown(v: View) {

            }

            override fun onMove(v: View, value: Int) {
                if (checkLoading()) return
                viewEdit.sbOpacity.tvProgress.text = value.toString()
                if (!isAvatar) viewEdit.ivBlendRoot.setAlphaBlend(value * 255 / 100)
                else viewEdit.ivBlendRoot.setAlpha(value * 255 / 100)
            }

            override fun onUp(v: View, value: Int) {

            }

        }
    }

    private fun chooseImgBlend() {
        if (!viewEdit.viewLoading.isVisible) viewEdit.viewLoading.visibility = View.VISIBLE
        val viewPagerAdapter = ViewPagerAddFragmentsAdapter(supportFragmentManager, lifecycle)

        val arrBlend =
            arrayOf("blend_city", "blend_color", "blend_nature", "blend_space", "blend_texture")

        for (name_Blend in arrBlend) {
            val childBlendFragmentModel =
                ItemPagerBlendFragment.newInstance(name_Blend, object : ICallBackItem {
                    override fun callBack(ob: Any, position: Int) {
                        val imgBlend = ob as ImgBlendModel
                        if (position == 0) {
                            val pickPictureFragment =
                                PickPictureFragment.newInstance(object : ICallBackItem {
                                    override fun callBack(ob: Any, position: Int) {
                                        val pic = ob as PicModel
                                        imgBlendModel =
                                            ImgBlendModel(pic.bucket!!, pic.uri, false, false, true)
                                        setBlend(false, "user", imgBlendModel!!)
                                    }
                                }, object : ICallBackCheck {
                                    override fun check(isCheck: Boolean) {

                                    }
                                })
                            replaceFragment(
                                supportFragmentManager,
                                pickPictureFragment,
                                true,
                                true,
                                true
                            )
                        } else {
                            if (imgBlend.isPremium)
                                setIntent(GoPremiumActivity::class.java.name, false)
                            else if (imgBlend.isAds) {
                                Toast.makeText(this@EditActivity, "Run Ads", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                imgBlendModel = imgBlend
                                setBlend(false, "in_app", imgBlend)
                            }
                        }
                    }
                })
            viewPagerAdapter.addFrag(childBlendFragmentModel)
        }

        viewEdit.viewChooseImgBlend.tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                (tab.customView as ViewItemTabBlend).v.visibility = View.VISIBLE
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                (tab.customView as ViewItemTabBlend).v.visibility = View.GONE
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        viewEdit.viewChooseImgBlend.viewPager.offscreenPageLimit = 3
        viewEdit.viewChooseImgBlend.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(
            viewEdit.viewChooseImgBlend.tabLayout,
            viewEdit.viewChooseImgBlend.viewPager
        ) { tab, position ->
            val itemTab = ViewItemTabBlend(this@EditActivity).apply {
                v.visibility = View.GONE
                val text = arrBlend[position].replace("blend_", "")
                tv.text = text.replace(text.substring(0, 1), text.substring(0, 1).uppercase())
                Log.d(Constant.TAG, "initView: ${tv.text}")
                Glide.with(this@EditActivity)
                    .load(
                        UtilsBitmap.getBitmapFromAsset(
                            this@EditActivity,
                            "blend/${arrBlend[position]}",
                            "image_1.webp"
                        )
                    )
                    .placeholder(R.drawable.im_place_holder)
                    .into(iv)
                layoutParams = RelativeLayout.LayoutParams(-1, -1)
            }
            tab.customView = itemTab
        }.attach()

        if (viewEdit.viewLoading.isVisible) viewEdit.viewLoading.visibility = View.GONE
    }

    private fun showUpBlend() {
        if (typeBlendAdapter == null) {
            typeBlendAdapter = TypeBlendAdapter(this@EditActivity, object : ICallBackItem {
                override fun callBack(ob: Any, position: Int) {
                    blendModel = ob as BlendModel
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                        viewEdit.ivBlendRoot.setTypeBlend(blendModel!!.mode as BlendMode)
                    else viewEdit.ivBlendRoot.setTypeBlend(blendModel!!.mode as Mode)
                    viewEdit.showBlend(1)
                    viewEdit.tvBlend.text = blendModel!!.nameBlend
                }
            })
            typeBlendAdapter?.let {
                it.setData(DataBlend.getDataBlend())
                viewEdit.rcvShowBlend.layoutManager =
                    LinearLayoutManager(this@EditActivity, LinearLayoutManager.VERTICAL, false)
                viewEdit.rcvShowBlend.adapter = it
            }
        }
    }

    private fun setBlend(isLoadCrop: Boolean, type: String, imgBlend: ImgBlendModel) {
        val wMain = w * 100
        val hMain = (109.25f * w).toInt()
        val scaleScreen = wMain / hMain

        if (isLoadCrop) {
            val bitmap = UtilsBitmap.getBitmapFromUri(this@EditActivity, UCrop.getOutput(intent))
            bitmap?.let {
                currentImg = if (it.width.toFloat() / it.height > scaleScreen)
                    Bitmap.createScaledBitmap(
                        it,
                        wMain.toInt(),
                        wMain.toInt() * it.height / it.width,
                        false
                    )
                else Bitmap.createScaledBitmap(
                    it,
                    hMain * it.width / it.height,
                    hMain,
                    false
                )

                viewEdit.rlBlendRoot.layoutParams.width = currentImg.width
                viewEdit.rlBlendRoot.layoutParams.height = currentImg.height
                viewEdit.ivBlendRoot.layoutParams.width = currentImg.width
                viewEdit.ivBlendRoot.layoutParams.height = currentImg.height

                viewEdit.ivBlendRoot.setBitmap(currentImg)

                viewEdit.ivAvatar.iv.setImageBitmap(
                    Bitmap.createScaledBitmap(it, 320, 320 * it.height / it.width, false)
                )
                isFirst = true
            }
        }

        var bmBlend: Bitmap? = null
        when (type) {
            "in_app" -> {
                bmBlend = UtilsBitmap.getBitmapFromAsset(
                    this@EditActivity,
                    "blend/${imgBlend.bucket}",
                    imgBlend.uri
                )
            }
            "user" -> {
                bmBlend = UtilsBitmap.getBitmapFromUri(this@EditActivity, Uri.parse(imgBlend.uri))
            }
        }
        bmBlend?.let {
            viewEdit.ivBlendRoot.apply {
                if (it.width.toFloat() / it.height > scaleScreen)
                    currentImgBlend = Bitmap.createScaledBitmap(
                        it,
                        wMain.toInt(),
                        wMain.toInt() * it.height / it.width,
                        false
                    )
                else {
                    currentImgBlend = Bitmap.createScaledBitmap(
                        it,
                        hMain * it.width / it.height,
                        hMain,
                        false
                    )
                }

                setBitmapBlend(currentImgBlend, true)

                if (blendModel != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                        setTypeBlend(blendModel!!.mode as BlendMode)
                    else setTypeBlend(blendModel!!.mode as Mode)
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                        setTypeBlend(BlendMode.DARKEN)
                    else setTypeBlend(Mode.DARKEN)
                }
            }
            viewEdit.ivAvatar2.iv.setImageBitmap(
                Bitmap.createScaledBitmap(it, 320, 320 * it.height / it.width, false)
            )
        }
    }

    private fun goToCrop(pic: PicModel) {
        val uCrop =
            UCrop.of(Uri.parse(pic.uri), Uri.fromFile(File(cacheDir, "remi-blend-editor.png")))
        uCrop.apply {
            useSourceImageAspectRatio()
            withOptions(UCrop.Options().apply {
                setCompressionFormat(Bitmap.CompressFormat.PNG)
                setCompressionQuality(100)
            })
        }
        uCrop.start(this@EditActivity)
        intent = uCrop.getIntent(this@EditActivity)
    }

    private fun swap() {
        Thread() {
            var bmTemp = Bitmap.createBitmap(currentImg)
            currentImg = Bitmap.createBitmap(currentImgBlend)
            currentImgBlend = Bitmap.createBitmap(bmTemp)

            bmTemp = Bitmap.createScaledBitmap(
                currentImg,
                currentImgBlend.width,
                currentImgBlend.height,
                false
            )
            currentImg = Bitmap.createBitmap(bmTemp)
            viewEdit.rlBlendRoot.layoutParams.width = currentImg.width
            viewEdit.rlBlendRoot.layoutParams.height = currentImg.height


            Handler(Looper.getMainLooper()).post {
                viewEdit.ivBlendRoot.setBitmap(currentImg)

                viewEdit.ivAvatar.iv.setImageBitmap(
                    Bitmap.createScaledBitmap(
                        currentImg,
                        320,
                        320 * currentImg.height / currentImg.width,
                        false
                    )
                )

                viewEdit.ivBlendRoot.setBitmapBlend(currentImgBlend, true)
                Glide.with(this@EditActivity)
                    .load(
                        Bitmap.createScaledBitmap(
                            currentImgBlend,
                            320,
                            320 * currentImg.height / currentImg.width,
                            false
                        )
                    )
                    .into(viewEdit.ivAvatar2.iv)

                bmTemp.recycle()
            }
        }.start()
    }

    private fun outErase() {
        if (isErase) {
            viewEdit.ivBlendRoot.apply {
                setBitmapErase(viewEdit.ivErase.saveDrawnBitmap())
                isMove = true
            }
            viewEdit.showErase("hide")
            viewEdit.ivErase.visibility = View.GONE
            isErase = false
            isErased = true
        } else saveImage()
    }

    private fun saveImage() {
        if (!viewEdit.viewLoading.isVisible) viewEdit.viewLoading.visibility = View.VISIBLE
        Thread() {
            val file = File(Utils.getStore(this@EditActivity), Constant.FOLDER_IMG_TEMP)
            if (!file.exists()) Utils.makeFolder(this, Constant.FOLDER_IMG_TEMP)

            val str = UtilsBitmap.saveBitmapToApp(
                this@EditActivity,
                viewEdit.ivBlendRoot.getBitmap()!!,
                Constant.FOLDER_IMG_TEMP,
                "imgBlend"
            )

            Handler(Looper.getMainLooper()).post {
                if (viewEdit.viewLoading.isVisible) viewEdit.viewLoading.visibility = View.GONE
                val filterFragment = FilterImgFragment.newInstance(str, object : ICallBackCheck {
                    override fun check(isCheck: Boolean) {
                        Utils.clearBackStack(supportFragmentManager)
                        finish()
                    }

                })
                replaceFragment(supportFragmentManager, filterFragment, true, true, true)
                isFilterFragVisible = true
            }
        }.start()
    }

    private fun showDialogCancel() {
        val viewDialogCancel = ViewDialogText(this@EditActivity)
        viewDialogCancel.apply {
            tvTitle.text = getString(R.string.cancel)
            tvContext.text = getString(R.string.do_you_want_to_save_the_file)
            tvNo.text = getString(R.string.no)
            tvYes.text = getString(R.string.yes)
        }

        val dialog = AlertDialog.Builder(this@EditActivity, R.style.SheetDialog).create()
        dialog.apply {
            setCancelable(false)
            setView(viewDialogCancel)
            show()
        }

        viewDialogCancel.layoutParams.width = (73.889f * w).toInt()
        viewDialogCancel.layoutParams.height = (38.889f * w).toInt()

        viewDialogCancel.ivExit.setOnClickListener { dialog.cancel() }
        viewDialogCancel.tvNo.setOnClickListener {
            dialog.cancel()
            finish()
        }
        viewDialogCancel.tvYes.setOnClickListener {
            dialog.cancel()
            saveImage()
        }
    }

    private fun checkLoading(): Boolean {
        return viewEdit.viewLoading.isVisible
    }
}