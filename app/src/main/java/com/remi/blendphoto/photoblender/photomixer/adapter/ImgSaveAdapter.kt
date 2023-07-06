package com.remi.blendphoto.photoblender.photomixer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.media.Image
import android.net.Uri
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.callback.ICallBackItem
import com.remi.blendphoto.photoblender.photomixer.model.picture.PicModel

class ImgSaveAdapter(context: Context, callBack: ICallBackItem): RecyclerView.Adapter<ImgSaveAdapter.ImgSaveHolder>() {

    private val context: Context
    private val callBack: ICallBackItem
    private var lstImgSave: ArrayList<PicModel>
    private var w = 0F

    init {
        this.context = context
        this.callBack = callBack
        this.lstImgSave = ArrayList()

        w = context.resources.displayMetrics.widthPixels / 100F
    }

    fun setData(lstImgSave: ArrayList<PicModel>) {
        this.lstImgSave = lstImgSave

        notifyChange()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgSaveHolder {
        return ImgSaveHolder(RoundedImageView(context))
    }

    override fun getItemCount(): Int {
        if (lstImgSave.isNotEmpty()) return lstImgSave.size
        return 0
    }

    override fun onBindViewHolder(holder: ImgSaveHolder, position: Int) {
        holder.onBind(position)
    }

    inner class ImgSaveHolder(viewItem: RoundedImageView): RecyclerView.ViewHolder(viewItem) {

        private val iv : RoundedImageView

        init {
            this.iv = viewItem.apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                elevation = 2 * w
                cornerRadius = 2.5f * w
            }
        }

        fun onBind(position: Int) {
            val pic = lstImgSave[position]

            when (position % 2) {
                0 -> iv.layoutParams =
                    RecyclerView.LayoutParams((43.33f * w).toInt(), (43.33f * w).toInt()).apply {
                        leftMargin = (4.44f * w).toInt()
                        rightMargin = (2.22f * w).toInt()
                        topMargin = (2.22f * w).toInt()
                        bottomMargin = (2.22f * w).toInt()
                    }
                else -> iv.layoutParams =
                    RecyclerView.LayoutParams((43.33f * w).toInt(), (43.33f * w).toInt()).apply {
                        leftMargin = (2.22f * w).toInt()
                        rightMargin = (4.44f * w).toInt()
                        topMargin = (2.22f * w).toInt()
                        bottomMargin = (2.22f * w).toInt()
                    }
            }

            if (!pic.isCheck)
                iv.apply {
                    borderColor = ContextCompat.getColor(context, R.color.white)
                    borderWidth = 0F
                }
            else iv.apply {
                borderColor = ContextCompat.getColor(context, R.color.color_main)
                borderWidth = 0.34f * w
            }

            Glide.with(context)
                .load(pic.uri)
                .placeholder(R.drawable.im_place_holder)
                .into(iv)

            iv.setOnClickListener {
                callBack.callBack(pic, position)
                setCurrent(position)
            }
        }
    }

    fun setCurrent(pos: Int) {
        for (i in lstImgSave.indices) lstImgSave[i].isCheck = i == pos

        notifyChange()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifyChange() {
        notifyDataSetChanged()
    }
}