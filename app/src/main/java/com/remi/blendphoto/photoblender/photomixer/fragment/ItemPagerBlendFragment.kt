package com.remi.blendphoto.photoblender.photomixer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.RelativeLayout.LayoutParams
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.adapter.BlendAdapter
import com.remi.blendphoto.photoblender.photomixer.callback.ICallBackDimensional
import com.remi.blendphoto.photoblender.photomixer.callback.ICallBackItem
import com.remi.blendphoto.photoblender.photomixer.data.DataBlend

class ItemPagerBlendFragment(callback: ICallBackItem) : Fragment() {

    private lateinit var nameBlend: String

    var w = 0F
    private var callback: ICallBackItem

    companion object {

        private const val NAME_BLEND = "name_blend"

        fun newInstance(name_border: String, callback: ICallBackItem): ItemPagerBlendFragment {
            val fragment = ItemPagerBlendFragment(callback)
            val args = Bundle()
            args.putString(NAME_BLEND, name_border)
            fragment.arguments = args
            return fragment
        }
    }

    init {
        this.callback = callback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        w = resources.displayMetrics.widthPixels / 100F
        nameBlend = arguments?.getString(NAME_BLEND).toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = RelativeLayout(requireContext()).apply {
            setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray_main))
        }
        initView(view)
        return view
    }

    private fun initView(view: RelativeLayout) {
        val rcvBlend = RecyclerView(requireContext())
        view.addView(rcvBlend,  LayoutParams(-1, -1).apply {
            setMargins(
                (1.67f * w).toInt(),
                (1.67f * w).toInt(),
                (1.67f * w).toInt(),
                (5.525f * w).toInt()
            )
        })

        val blendAdapter = BlendAdapter(requireContext(), callback)

        blendAdapter.setData(DataBlend.getDataImageBlend(requireContext(), nameBlend))

        rcvBlend.layoutManager = GridLayoutManager(requireContext(), 4)
        rcvBlend.adapter = blendAdapter
    }
}