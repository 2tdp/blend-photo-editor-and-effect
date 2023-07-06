package com.remi.blendphoto.photoblender.photomixer.activity

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.activity.base.BaseActivity
import com.remi.blendphoto.photoblender.photomixer.addview.ViewPreImg
import com.remi.blendphoto.photoblender.photomixer.addview.dialog.ViewDialogText
import com.remi.blendphoto.photoblender.photomixer.fragment.PickPictureFragment
import com.remi.blendphoto.photoblender.photomixer.model.picture.PicModel
import com.remi.blendphoto.photoblender.photomixer.sharepref.DataLocalManager
import com.remi.blendphoto.photoblender.photomixer.utils.ActionUtils
import com.remi.blendphoto.photoblender.photomixer.utils.Constant
import com.remi.blendphoto.photoblender.photomixer.utils.Utils
import java.io.File

class PreImgActivity: BaseActivity() {

    private lateinit var viewPreImg: ViewPreImg

    private var w = 0F
    private lateinit var pic : PicModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPreImg = ViewPreImg(this@PreImgActivity)
        setContentView(viewPreImg)
        w = resources.displayMetrics.widthPixels / 100F

        pic = DataLocalManager.getPicture(Constant.IMG_PREVIEW)!!
        if (pic.bucket == Constant.FROM_FILTER) viewPreImg.changeType(Constant.FROM_FILTER)
        else if (pic.bucket == Constant.FROM_SAVE) viewPreImg.changeType(Constant.FROM_SAVE)

        evenClick()
    }

    private fun evenClick() {
        val bm = BitmapFactory.decodeFile(pic.uri)
        Glide.with(this@PreImgActivity)
            .load(bm)
            .into(viewPreImg.ivMain)

        viewPreImg.ivMess.setOnClickListener {
            ActionUtils.shareFile(this@PreImgActivity, bm, Constant.MESS_PACKAGE_NAME)
        }
        viewPreImg.ivFb.setOnClickListener {
            ActionUtils.shareFile(this@PreImgActivity, bm, Constant.FACEBOOK_PACKAGE_NAME)
        }
        viewPreImg.ivIg.setOnClickListener {
            ActionUtils.shareFile(this@PreImgActivity, bm, Constant.INSTAGRAM_PACKAGE_NAME)
        }
        viewPreImg.ivMore.setOnClickListener {
            ActionUtils.shareFile(this@PreImgActivity, bm, null)
        }

        viewPreImg.tvShare.setOnClickListener {
            ActionUtils.shareFile(this@PreImgActivity, bm, null)
        }
        viewPreImg.tvDel.setOnClickListener { showDialogDel() }

        viewPreImg.viewToolbar.ivBack.setOnClickListener { finish() }
    }

    private fun showDialogDel() {
        val viewDialogCancel = ViewDialogText(this@PreImgActivity)
        viewDialogCancel.apply {
            tvTitle.text = getString(R.string.del)
            tvContext.text = getString(R.string.are_you_sure_you_want_to_delete_the_photo)
            tvNo.apply {
                text = getString(R.string.cancel)
                setTextColor(ContextCompat.getColor(this@PreImgActivity, R.color.black))
            }
            tvYes.apply {
                text = getString(R.string.del)
                setTextColor(ContextCompat.getColor(this@PreImgActivity, R.color.red))
            }
        }

        val dialog = AlertDialog.Builder(this@PreImgActivity, R.style.SheetDialog).create()
        dialog.apply {
            setCancelable(false)
            setView(viewDialogCancel)
            show()
        }

        viewDialogCancel.layoutParams.width = (73.889f * w).toInt()
        viewDialogCancel.layoutParams.height = (42.778f * w).toInt()

        viewDialogCancel.ivExit.setOnClickListener { dialog.cancel() }
        viewDialogCancel.tvNo.setOnClickListener {
            dialog.cancel()
            finish()
        }
        viewDialogCancel.tvYes.setOnClickListener {
            dialog.cancel()

            val file = File(pic.uri)
            if (file.exists()) file.delete()

            finish()
        }
    }
}