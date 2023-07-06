package com.remi.blendphoto.photoblender.photomixer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.addview.viewitem.ViewItemChooseBlend
import com.remi.blendphoto.photoblender.photomixer.callback.ICallBackItem
import com.remi.blendphoto.photoblender.photomixer.model.ImgBlendModel

class BlendAdapter(context: Context, callBack: ICallBackItem) :
    RecyclerView.Adapter<BlendAdapter.BorderHolder>() {

    private val context: Context
    private var lstPicBlendModel: ArrayList<ImgBlendModel>
    private val callBack: ICallBackItem
    private var w = 0F

    init {
        this.context = context
        this.callBack = callBack

        lstPicBlendModel = ArrayList()
        w = context.resources.displayMetrics.widthPixels / 100F
    }

    fun setData(lstPicBlendModel: ArrayList<ImgBlendModel>) {
        this.lstPicBlendModel = lstPicBlendModel

        notifyChange()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BorderHolder {
        return BorderHolder(ViewItemChooseBlend(context))
    }

    override fun onBindViewHolder(holder: BorderHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return if (lstPicBlendModel.isNotEmpty()) lstPicBlendModel.size
        else 0
    }

    inner class BorderHolder(itemView: ViewItemChooseBlend) : RecyclerView.ViewHolder(itemView) {

        private val itemImgBlend : ViewItemChooseBlend

        init {
            this.itemImgBlend = itemView
            val params =
                RelativeLayout.LayoutParams((18.51f * w).toInt(), (18.51f * w).toInt())
            params.setMargins(
                (1.67f * w).toInt(),
                (1.67f * w).toInt(),
                (1.67f * w).toInt(),
                (1.67f * w).toInt()
            )
            itemImgBlend.layoutParams = params

            val paramsPremium =
                RelativeLayout.LayoutParams((8.24f * w).toInt(), (5.18f * w).toInt()).apply {
                    addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
                    addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
                }
            itemImgBlend.iv.layoutParams = paramsPremium
        }

        fun onBind(pos: Int) {
            val imgBlend = lstPicBlendModel[pos]

            if (imgBlend.isPremium) {
                itemImgBlend.iv.visibility = View.VISIBLE
                itemImgBlend.iv.setImageResource(R.drawable.ic_premium_blend)
            } else if (imgBlend.isAds) {
                itemImgBlend.iv.visibility = View.VISIBLE
                itemImgBlend.iv.setImageResource(R.drawable.ic_ads_blend)
            } else itemImgBlend.iv.visibility = View.GONE

            if (imgBlend.isCheck) itemImgBlend.ivChoose.visibility = View.VISIBLE
            else itemImgBlend.ivChoose.visibility = View.GONE

            if (imgBlend.uri == "" && imgBlend.bucket == "")
                Glide.with(context)
                    .asBitmap()
                    .load(R.drawable.ic_import)
                    .into(itemImgBlend.ivBlend as ImageView)
            else
                Glide.with(context)
                    .load(Uri.parse("file:///android_asset/blend/${imgBlend.bucket}/${imgBlend.uri}"))
                    .into(itemImgBlend.ivBlend)

            itemImgBlend.setOnClickListener {
                if ((imgBlend.isPremium || !imgBlend.isAds) && pos != 0)
                    setCurrent(imgBlend)
                callBack.callBack(imgBlend, pos)
            }
        }
    }

    fun setCurrent(imgBlendModel: ImgBlendModel) {
        for (imgBlend in lstPicBlendModel) imgBlend.isCheck = imgBlend.uri == imgBlendModel.uri

        notifyChange()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifyChange() {
        notifyDataSetChanged()
    }
}