package com.remi.blendphoto.photoblender.photomixer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.addview.viewitem.ViewItemFilter
import com.remi.blendphoto.photoblender.photomixer.callback.ICallBackItem
import com.remi.textonphoto.writeonphoto.addtext.model.FilterModel

class FilterAdapter(context: Context, callBack: ICallBackItem): RecyclerView.Adapter<FilterAdapter.TattooPremiumHolder>() {

    private val context: Context
    private val callBack: ICallBackItem
    private var lstFilter: ArrayList<FilterModel>
    private var w = 0F

    init {
        this.context = context
        this.callBack = callBack

        lstFilter = ArrayList()
        w = context.resources.displayMetrics.widthPixels / 100F
    }

    fun setDataFilter(lstFilter: ArrayList<FilterModel>) {
        this.lstFilter = lstFilter

        notifyChange()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TattooPremiumHolder {
        return TattooPremiumHolder(ViewItemFilter(context))
    }

    override fun onBindViewHolder(holder: TattooPremiumHolder, position: Int) {
        holder.onBindFilter(position)
    }

    override fun getItemCount(): Int {
        if (lstFilter.isNotEmpty()) return lstFilter.size
        return 0
    }

    inner class TattooPremiumHolder(ivFilter: ViewItemFilter): RecyclerView.ViewHolder(ivFilter) {

        private val itemFilter: ViewItemFilter

        init {
            this.itemFilter = ivFilter
        }

        fun onBindFilter(position: Int) {
            val filter = lstFilter[position]

            when (position) {
                0 -> itemFilter.layoutParams =
                    RecyclerView.LayoutParams((17.59f * w).toInt(), (28.14f * w).toInt()).apply {
                        leftMargin = (4.07f * w).toInt()
                        rightMargin = (2.22f * w).toInt()
                    }
                lstFilter.size - 1 -> itemFilter.layoutParams =
                    RecyclerView.LayoutParams((17.59f * w).toInt(), (28.14f * w).toInt()).apply {
                        leftMargin = (2.22f * w).toInt()
                        rightMargin = (4.07f * w).toInt()
                    }
                else -> itemFilter.layoutParams =
                    RecyclerView.LayoutParams((17.59f * w).toInt(), (28.14f * w).toInt()).apply {
                        leftMargin = (2.22f * w).toInt()
                        rightMargin = (2.22f * w).toInt()
                    }
            }

            if (!filter.isCheck)
                itemFilter.ivFilter.apply {
                    borderColor = ContextCompat.getColor(context, R.color.white)
                    borderWidth = 0.34f * w
                }
            else itemFilter.ivFilter.apply {
                borderColor = ContextCompat.getColor(context, R.color.color_main)
                borderWidth = 0.34f * w
            }

            Glide.with(context)
                .load(filter.bitmap)
                .into(itemFilter.ivFilter)
            itemFilter.tv.text = filter.nameFilter

            itemView.setOnClickListener {
                callBack.callBack(filter, position)
                setCurrent(position)
            }
        }
    }

    fun setCurrent(pos: Int) {
        for (i in lstFilter.indices) lstFilter[i].isCheck = i == pos

        notifyChange()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifyChange() {
        notifyDataSetChanged()
    }
}