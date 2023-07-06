package com.remi.blendphoto.photoblender.photomixer.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.remi.blendphoto.photoblender.photomixer.activity.PreImgActivity
import com.remi.blendphoto.photoblender.photomixer.adapter.FilterAdapter
import com.remi.blendphoto.photoblender.photomixer.addview.ViewFilterImg
import com.remi.blendphoto.photoblender.photomixer.callback.ICallBackCheck
import com.remi.blendphoto.photoblender.photomixer.callback.ICallBackItem
import com.remi.blendphoto.photoblender.photomixer.data.DataFilter
import com.remi.blendphoto.photoblender.photomixer.model.picture.PicModel
import com.remi.blendphoto.photoblender.photomixer.sharepref.DataLocalManager
import com.remi.blendphoto.photoblender.photomixer.utils.Constant
import com.remi.blendphoto.photoblender.photomixer.utils.Utils
import com.remi.blendphoto.photoblender.photomixer.utils.UtilsBitmap
import com.remi.textonphoto.writeonphoto.addtext.model.FilterModel
import org.wysaid.nativePort.CGENativeLibrary
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class FilterImgFragment(callBack: ICallBackCheck): Fragment() {

    private lateinit var viewFilterImg: ViewFilterImg

    private var bmFilter: Bitmap? = null
    private val callBack: ICallBackCheck
    private var strImgBlend = ""

    companion object {
        private const val STR_IMG = "imgBlend"
        fun newInstance(str: String, callBack: ICallBackCheck): FilterImgFragment {
            val args = Bundle()

            val fragment = FilterImgFragment(callBack)
            args.putString(STR_IMG, str)
            fragment.arguments = args
            return fragment
        }
    }

    init {
        this.callBack = callBack
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewFilterImg = ViewFilterImg(requireContext())

        strImgBlend = requireArguments().getString(STR_IMG, "")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return viewFilterImg
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        viewFilterImg.viewToolbar.ivBack.setOnClickListener {
            parentFragmentManager.popBackStack("FilterImgFragment",  POP_BACK_STACK_INCLUSIVE)
        }
        viewFilterImg.viewToolbar.tvRight.setOnClickListener {
            if (!viewFilterImg.viewLoading.isVisible) viewFilterImg.viewLoading.visibility = View.VISIBLE

            Thread() {
                bmFilter?.let {
                    val bmSave =
                        Bitmap.createScaledBitmap(
                            it, 1080, 1080 * it.height / it.width, false
                        )

                    val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        .format(Calendar.getInstance(Locale.getDefault()).time)

                    val file = File(Utils.getStore(requireContext()), Constant.FOLDER_IMG_SAVE)
                    if (!file.exists()) Utils.makeFolder(requireContext(), Constant.FOLDER_IMG_SAVE)

                    Utils.saveImage(requireContext(), bmSave, "remi")
                    val strApp = UtilsBitmap.saveBitmapToApp(
                        requireContext(),
                        bmSave,
                        Constant.FOLDER_IMG_SAVE,
                        "imgSave$time"
                    )

                    Handler(Looper.getMainLooper()).post {
                        if (viewFilterImg.viewLoading.isVisible) viewFilterImg.viewLoading.visibility = View.GONE
                        callBack.check(true)
                        DataLocalManager.setPicture(
                            PicModel("", Constant.FROM_FILTER, 0F, strApp, false),
                            Constant.IMG_PREVIEW
                        )
                        Utils.setIntent(requireContext(), PreImgActivity::class.java.name)
                    }
                }
            }.start()
        }
    }

    private fun initView() {
        val bitmap = BitmapFactory.decodeFile(strImgBlend)
        viewFilterImg.ivMain.setImageBitmap(bitmap)

        val filterAdapter = FilterAdapter(requireContext(), object : ICallBackItem {
            override fun callBack(ob: Any, position: Int) {
                val filter = ob as FilterModel
                if (!viewFilterImg.viewLoading.isVisible) viewFilterImg.viewLoading.visibility = View.VISIBLE
                Thread {
                    bmFilter = CGENativeLibrary.cgeFilterImage_MultipleEffects(
                        bitmap, "@adjust lut " + filter.parameterFilter, 0.8f
                    )

                    Handler(Looper.getMainLooper()).post {
                        viewFilterImg.ivMain.setImageBitmap(bmFilter)
                        if (viewFilterImg.viewLoading.isVisible) viewFilterImg.viewLoading.visibility = View.GONE
                    }
                }.start()
            }
        })

        Thread {
            val lstFilter = DataFilter.getDataFilter(
                Bitmap.createScaledBitmap(
                    bitmap,
                    320, 320 * bitmap.height / bitmap.width,
                    false
                )
            )
            Handler(Looper.getMainLooper()).post {
                filterAdapter.setDataFilter(lstFilter)
            }
        }.start()

        viewFilterImg.rcvFilter.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewFilterImg.rcvFilter.adapter = filterAdapter
    }
}