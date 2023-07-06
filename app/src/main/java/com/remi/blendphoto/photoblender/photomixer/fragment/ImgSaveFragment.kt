package com.remi.blendphoto.photoblender.photomixer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import com.remi.blendphoto.photoblender.photomixer.adapter.ImgSaveAdapter
import com.remi.blendphoto.photoblender.photomixer.addview.ViewImgSave
import com.remi.blendphoto.photoblender.photomixer.callback.ICallBackItem
import com.remi.blendphoto.photoblender.photomixer.data.DataSave
import com.remi.blendphoto.photoblender.photomixer.model.picture.PicModel
import com.remi.blendphoto.photoblender.photomixer.utils.Constant

class ImgSaveFragment(callBack: ICallBackItem): Fragment() {

    private lateinit var viewImgSave: ViewImgSave

    private val callBack: ICallBackItem
    private var imgSaveAdapter : ImgSaveAdapter? = null

    companion object {
        fun newInstance(callBack: ICallBackItem): ImgSaveFragment {
            val args = Bundle()

            val fragment = ImgSaveFragment(callBack)
            fragment.arguments = args
            return fragment
        }
    }

    init {
        this.callBack = callBack
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewImgSave = ViewImgSave(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return viewImgSave
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewImgSave.viewToolbar.ivBack.setOnClickListener {
            parentFragmentManager.popBackStack("ImgSaveFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        imgSaveAdapter = ImgSaveAdapter(requireContext(), callBack)
        resetData()

        viewImgSave.rcvSave.layoutManager = GridLayoutManager(requireContext(), 2)
        viewImgSave.rcvSave.adapter = imgSaveAdapter
    }

    fun resetData() {
        imgSaveAdapter?.setData(DataSave.getImgSave(requireContext(), Constant.FOLDER_IMG_SAVE))
    }
}