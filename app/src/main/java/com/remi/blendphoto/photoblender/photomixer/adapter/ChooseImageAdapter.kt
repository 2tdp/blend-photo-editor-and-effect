package com.remi.blendphoto.photoblender.photomixer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.callback.ICallBackItem
import com.remi.blendphoto.photoblender.photomixer.model.picture.PicModel
import com.makeramen.roundedimageview.RoundedImageView

class ChooseImageAdapter(context: Context, callBack: ICallBackItem): RecyclerView.Adapter<ChooseImageAdapter.ChooseBackgroundHolder>() {

    private val context: Context
    private val callBack: ICallBackItem
    private var lstPic: ArrayList<PicModel>

    private var w = 0F

    init {
        this.context = context
        this.callBack = callBack

        w = context.resources.displayMetrics.widthPixels / 100f
        lstPic = ArrayList()
    }

    fun setData(lstPic : ArrayList<PicModel>) {
        this.lstPic = lstPic

        this.lstPic.reverse()
        notifyChange()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseBackgroundHolder {
        return ChooseBackgroundHolder(RoundedImageView(context))
    }

    override fun onBindViewHolder(holder: ChooseBackgroundHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        if (lstPic.isNotEmpty()) return lstPic.size
        return 0
    }

    inner class ChooseBackgroundHolder(itemView: RoundedImageView): RecyclerView.ViewHolder(itemView) {

        init {
            itemView.apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                cornerRadius = 2.5f * w
            }
            itemView.layoutParams =
                RecyclerView.LayoutParams((27.778f * w).toInt(), (27.778f * w).toInt()).apply {
                    leftMargin = (2.22f * w).toInt()
                    rightMargin = (2.22f * w).toInt()
                    bottomMargin = (4.44f * w).toInt()
                }
        }

        fun onBind(position: Int) {
            val picture = lstPic[position]

            Glide.with(context)
                .asBitmap()
                .load(picture.uri)
                .placeholder(R.drawable.im_place_holder)
                .into(itemView as ImageView)

            itemView.setOnClickListener { callBack.callBack(picture, position) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifyChange() {
        notifyDataSetChanged()
    }
}