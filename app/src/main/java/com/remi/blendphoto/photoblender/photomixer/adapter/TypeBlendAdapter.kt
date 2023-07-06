package com.remi.blendphoto.photoblender.photomixer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.remi.blendphoto.photoblender.photomixer.addview.viewitem.ViewItemBlend
import com.remi.blendphoto.photoblender.photomixer.callback.ICallBackItem
import com.remi.blendphoto.photoblender.photomixer.model.BlendModel

class TypeBlendAdapter(context: Context, callBack: ICallBackItem): RecyclerView.Adapter<TypeBlendAdapter.BlendHolder>() {

    private val context: Context
    private val callBack: ICallBackItem
    private var lstBlend: ArrayList<BlendModel>
    private var w = 0F

    init {
        this.context = context
        this.callBack = callBack
        this.lstBlend = ArrayList()
        w = context.resources.displayMetrics.widthPixels / 100F
    }

    fun setData(lstBlend: ArrayList<BlendModel>) {
        this.lstBlend = lstBlend

        changeNotify()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlendHolder {
        return BlendHolder(ViewItemBlend(context))
    }

    override fun getItemCount(): Int {
        if (lstBlend.isNotEmpty()) return lstBlend.size

        return 0
    }

    override fun onBindViewHolder(holder: BlendHolder, position: Int) {
        holder.onBind(position)
    }

    inner class BlendHolder(itemView: ViewItemBlend): ViewHolder(itemView){

        private val viewItemBlend: ViewItemBlend

        init {
            this.viewItemBlend = itemView
            this.viewItemBlend.layoutParams =
                RelativeLayout.LayoutParams((43.518f * w).toInt(), (10.6f * w).toInt())
        }

        fun onBind(position: Int) {
            val blend = lstBlend[position]

            if (position == lstBlend.size - 1) viewItemBlend.vLine.visibility = View.GONE
            else viewItemBlend.vLine.visibility = View.VISIBLE

            viewItemBlend.tv.text = blend.nameBlend

            if (blend.isCheck) viewItemBlend.iv.visibility = View.VISIBLE
            else viewItemBlend.iv.visibility = View.GONE

            viewItemBlend.setOnClickListener {
                setCurrent(blend)
                callBack.callBack(blend, position)
            }
        }
    }

    fun setCurrent(blendModel: BlendModel) {
        for (blend in lstBlend)
            blend.isCheck = blend.nameBlend == blendModel.nameBlend

        changeNotify()
    }

    fun getPosition(blendModel: BlendModel): Int {
        for (blend in lstBlend)
            if (blend.nameBlend == blendModel.nameBlend) {
                val index = lstBlend.indexOf(blend)
                return if (index != 0 && index != lstBlend.size - 1) index - 1
                else index
            }
        return -1
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeNotify() {
        notifyDataSetChanged()
    }
}