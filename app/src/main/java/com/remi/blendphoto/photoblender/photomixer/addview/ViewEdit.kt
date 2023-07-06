package com.remi.blendphoto.photoblender.photomixer.addview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.tabs.TabLayout
import com.makeramen.roundedimageview.RoundedImageView
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.utils.Utils
import com.remi.blendphoto.photoblender.photomixer.utils.UtilsView.textCustom
import com.remi.blendphoto.photoblender.photomixer.viewcustom.CustomViewBlend
import com.remi.blendphoto.photoblender.photomixer.viewcustom.erase.HoverView

@SuppressLint("ResourceType")
class ViewEdit(context: Context) : RelativeLayout(context) {

    companion object {
        var w = 0f
    }

    val viewToolbar: ViewToolbar
    val rlBlendRoot: RelativeLayout
    val ivBlendRoot: CustomViewBlend
    val ivErase: HoverView
    val sbOpacity: ViewSeekbar

    val rlBlend: RelativeLayout
    private val ivBlend : ImageView
    val tvBlend : TextView

    val ivConvert: ImageView
    val ivAvatar: ImageAvatar
    val ivAvatar2: ImageAvatar

    val vShowBlend: RelativeLayout
    val rcvShowBlend : RecyclerView

    val rlMain: RelativeLayout
    val v: View
    val viewChooseImgBlend: ViewChooseImgBlend

    val viewChooseErase: ViewChooseErase

    val viewLoading: LottieAnimationView

    init {
        w = resources.displayMetrics.widthPixels / 100F
        setBackgroundColor(ContextCompat.getColor(context, R.color.gray_main))

        viewToolbar = ViewToolbar(context).apply {
            id = 1221
            tvTitle.visibility = GONE
            ll.visibility = VISIBLE
            ivRight.visibility = VISIBLE
        }
        addView(viewToolbar, LayoutParams(-1, (23f * w).toInt()))

        sbOpacity = ViewSeekbar(context).apply { id = 1222 }
        addView(sbOpacity, LayoutParams(-1, (11.2f * w).toInt()).apply {
            addRule(ALIGN_PARENT_BOTTOM, TRUE)
            leftMargin = (9.07f * w).toInt()
            rightMargin = (9.07f * w).toInt()
            bottomMargin = (11.29f * w).toInt()
        })

        val rlImage = RelativeLayout(context).apply {
            id = 1225
            setPadding((0.55f * w).toInt())
        }
        ivAvatar = ImageAvatar(context)
        rlImage.addView(ivAvatar, LayoutParams((19.44f * w).toInt(), -1))

        ivConvert = ImageView(context).apply { setImageResource(R.drawable.ic_convert) }
        rlImage.addView(ivConvert, LayoutParams((6.85f * w).toInt(), (6.85f * w).toInt()).apply {
            addRule(CENTER_IN_PARENT, TRUE)
        })

        ivAvatar2 = ImageAvatar(context)
        rlImage.addView(ivAvatar2, LayoutParams((19.44f * w).toInt(), -1).apply {
            addRule(ALIGN_PARENT_END, TRUE)
        })
        addView(rlImage, LayoutParams(-1, (19.44f * w).toInt()).apply {
            addRule(ABOVE, sbOpacity.id)
            leftMargin = (17.59f * w).toInt()
            rightMargin = (17.59f * w).toInt()
            bottomMargin = (5.9f * w).toInt()
            topMargin = (7.407f * w).toInt()
        })

        rlBlend = RelativeLayout(context).apply {
            id = 1224
            background = Utils.createBackground(
                intArrayOf(ContextCompat.getColor(context, R.color.color_main)),
                (2f * w).toInt(), -1, -1
            )
        }
        ivBlend = ImageView(context).apply {
            setImageResource(R.drawable.ic_show_down)
            id = 1998
        }
        tvBlend = TextView(context).apply {
            textCustom(
                "Darken",
                ContextCompat.getColor(context, R.color.black_main),
                4.44f * w,
                "alegreya_regular",
                context
            )
            gravity = Gravity.CENTER
        }
        rlBlend.addView(tvBlend, LayoutParams(-1, -1).apply {
            addRule(CENTER_VERTICAL, TRUE)
            addRule(LEFT_OF, ivBlend.id)
            leftMargin = (7.314f * w).toInt()
        })
        rlBlend.addView(ivBlend, LayoutParams((7.778f * w).toInt(), (7.778f * w).toInt()).apply {
            addRule(ALIGN_PARENT_END, TRUE)
            addRule(CENTER_VERTICAL, TRUE)
            rightMargin = (0.74f * w).toInt()
        })
        addView(rlBlend, LayoutParams((39.81f * w).toInt(), (9.25f * w).toInt()).apply {
            addRule(ABOVE, rlImage.id)
            addRule(CENTER_HORIZONTAL, TRUE)
            topMargin = (4.44f * w).toInt()
        })

        rlMain = RelativeLayout(context).apply { id = 1997 }

        rlBlendRoot = RelativeLayout(context).apply { id = 1995 }
        ivBlendRoot = CustomViewBlend(context)
        rlBlendRoot.addView(ivBlendRoot, LayoutParams(-1, (109.25f * w).toInt()).apply {
            addRule(CENTER_IN_PARENT, TRUE)
        })

        ivErase = HoverView(context).apply { visibility = GONE }
        rlBlendRoot.addView(ivErase, LayoutParams(-1, (109.25f * w).toInt()).apply {
            addRule(CENTER_IN_PARENT, TRUE)
        })

        v = View(context).apply {
            visibility = GONE
            setBackgroundColor(Color.parseColor("#26000000"))
        }
        rlMain.addView(v, LayoutParams(-1, -1).apply { addRule(BELOW, rlBlendRoot.id) })

        rlMain.addView(rlBlendRoot, LayoutParams(-1, (109.25f * w).toInt()).apply {
            addRule(CENTER_IN_PARENT, TRUE)
        })
        addView(rlMain, LayoutParams(-1, -1).apply {
            addRule(BELOW, viewToolbar.id)
            addRule(ABOVE, rlBlend.id)
        })

        vShowBlend = RelativeLayout(context).apply {
            visibility = GONE
            background =
                Utils.createBackground(intArrayOf(Color.WHITE), (2.5f * w).toInt(), -1, -1)
        }
        rcvShowBlend = RecyclerView(context).apply { isVerticalScrollBarEnabled = true }
        vShowBlend.addView(rcvShowBlend, -1, -1)
        addView(vShowBlend, LayoutParams((43.33f * w).toInt(), (52.31f * w).toInt()).apply {
            addRule(CENTER_HORIZONTAL, TRUE)
            addRule(BELOW, rlBlend.id)
            topMargin = (1.48f * w).toInt()
        })

        viewChooseImgBlend = ViewChooseImgBlend(context).apply {
            visibility = GONE
            setBackgroundColor(Color.parseColor("#26000000"))
        }
        addView(viewChooseImgBlend, LayoutParams(-1, -1).apply {
            addRule(BELOW, rlMain.id)
        })

        viewChooseErase = ViewChooseErase(context).apply {
            visibility = GONE
            setBackgroundColor(Color.parseColor("#26000000"))
        }
        addView(viewChooseErase, LayoutParams(-1, -1).apply {
            addRule(BELOW, rlMain.id)
        })

        viewLoading = LottieAnimationView(context).apply {
            visibility = GONE
            setAnimation(R.raw.iv_loading)
            repeatCount = LottieDrawable.INFINITE
        }
        viewLoading.playAnimation()
        addView(viewLoading, LayoutParams(-1, (55.55f * w).toInt()).apply {
            addRule(CENTER_IN_PARENT, TRUE)
            leftMargin = (5.556f * w).toInt()
            rightMargin = (5.556f * w).toInt()
        })
    }

    fun showErase(option: String) {
        when(option) {
            "show" -> {
                if (!viewChooseErase.isVisible) {
                    viewChooseErase.visibility = VISIBLE
                    viewChooseErase.animation =
                        AnimationUtils.loadAnimation(context, R.anim.slide_up_in)
                    viewChooseErase.animation.setAnimationListener(object : AnimationListener {
                        override fun onAnimationStart(animation: Animation?) {

                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            viewChooseErase.clearAnimation()
                            v.visibility = VISIBLE
                            v.animation =
                                AnimationUtils.loadAnimation(context, R.anim.slide_up_in)
                        }

                        override fun onAnimationRepeat(animation: Animation?) {

                        }

                    })

                    viewToolbar.apply {
                        ll.visibility = GONE
                        ivRight.setImageResource(R.drawable.ic_tick_blend)
                    }
                }
            }
            "hide" -> {
                if (viewChooseErase.isVisible) {
                    v.visibility = GONE
                    v.animation =
                        AnimationUtils.loadAnimation(context, R.anim.slide_down_out)
                    v.animation.setAnimationListener(object : AnimationListener{
                        override fun onAnimationStart(animation: Animation?) {

                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            viewChooseErase.visibility = GONE
                            viewChooseErase.animation =
                                AnimationUtils.loadAnimation(context, R.anim.slide_down_out)

                            viewChooseErase.animation.setAnimationListener(object : AnimationListener{
                                override fun onAnimationStart(animation: Animation?) {
                                }

                                override fun onAnimationEnd(animation: Animation?) {
                                    viewChooseErase.clearAnimation()
                                }

                                override fun onAnimationRepeat(animation: Animation?) {

                                }

                            })
                        }

                        override fun onAnimationRepeat(animation: Animation?) {

                        }

                    })

                    viewToolbar.apply {
                        ll.visibility = VISIBLE
                        ivRight.setImageResource(R.drawable.ic_next)
                    }
                }
            }
        }
    }
    fun chooseAvatar(option: String) {
        when(option) {
            "avatar" -> {
                ivAvatar.iv.apply {
                    borderColor = ContextCompat.getColor(context, R.color.color_main)
                    borderWidth = 0.55f * w
                }
                ivAvatar2.iv.apply {
                    borderColor = ContextCompat.getColor(context, R.color.trans)
                    borderWidth = 0.55f * w
                }
            }
            "avatar2" -> {
                ivAvatar2.iv.apply {
                    borderColor = ContextCompat.getColor(context, R.color.color_main)
                    borderWidth = 0.55f * w
                }
                ivAvatar.iv.apply {
                    borderColor = ContextCompat.getColor(context, R.color.trans)
                    borderWidth = 0.55f * w
                }
            }
        }
    }
    fun showBlend(option: Int) {
        when(option) {
            0 -> {
                if (!vShowBlend.isVisible) {
                    vShowBlend.visibility = View.VISIBLE
                    vShowBlend.animation =
                        AnimationUtils.loadAnimation(context, R.anim.slide_down)
                    vShowBlend.animation.setAnimationListener(object : AnimationListener{
                        override fun onAnimationStart(animation: Animation?) {

                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            vShowBlend.clearAnimation()
                            ivBlend.setImageResource(R.drawable.ic_hide_up)
                        }

                        override fun onAnimationRepeat(animation: Animation?) {

                        }
                    })

                }
            }
            1 -> {
                if (vShowBlend.isVisible) {
                    vShowBlend.visibility = View.GONE
                    vShowBlend.animation =
                        AnimationUtils.loadAnimation(context, R.anim.slide_up)

                    vShowBlend.animation.setAnimationListener(object : AnimationListener{
                        override fun onAnimationStart(animation: Animation?) {

                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            vShowBlend.clearAnimation()
                            ivBlend.setImageResource(R.drawable.ic_show_down)
                        }

                        override fun onAnimationRepeat(animation: Animation?) {

                        }

                    })
                }
            }
        }
    }

    fun showChooseBlend(option: String) {
        when(option) {
            "hide" -> {
                if (viewChooseImgBlend.isVisible) {
                    v.visibility = GONE
                    v.animation = AnimationUtils.loadAnimation(context, R.anim.slide_down_out)
                    v.animation.setAnimationListener(object : AnimationListener{
                        override fun onAnimationStart(animation: Animation?) {

                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            v.clearAnimation()
                            viewChooseImgBlend.visibility = GONE
                            viewChooseImgBlend.animation =
                                AnimationUtils.loadAnimation(context, R.anim.slide_down_out)

                            viewChooseImgBlend.animation.setAnimationListener(object : AnimationListener{
                                override fun onAnimationStart(animation: Animation?) {
                                }

                                override fun onAnimationEnd(animation: Animation?) {
                                    viewChooseImgBlend.clearAnimation()
                                }

                                override fun onAnimationRepeat(animation: Animation?) {

                                }

                            })
                        }

                        override fun onAnimationRepeat(animation: Animation?) {

                        }
                    })

                }
            }
            "show" -> {
                if (!viewChooseImgBlend.isVisible) {
                    viewChooseImgBlend.animation =
                        AnimationUtils.loadAnimation(context, R.anim.slide_up_in)
                    viewChooseImgBlend.visibility = VISIBLE
                    viewChooseImgBlend.animation.setAnimationListener(object : AnimationListener {
                        override fun onAnimationStart(animation: Animation?) {

                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            viewChooseImgBlend.clearAnimation()
                            v.visibility = VISIBLE
                            v.animation = AnimationUtils.loadAnimation(context, R.anim.slide_up_in)
                            v.animation.setAnimationListener(object : AnimationListener{
                                override fun onAnimationStart(animation: Animation?) {

                                }

                                override fun onAnimationEnd(animation: Animation?) {
                                    v.clearAnimation()
                                }

                                override fun onAnimationRepeat(animation: Animation?) {

                                }

                            })
                        }

                        override fun onAnimationRepeat(animation: Animation?) {

                        }
                    })
                }
            }
        }
    }

    inner class ViewChooseErase(context: Context): RelativeLayout(context) {

        val ivErase1: ItemErase
        val ivErase2: ItemErase
        val ivErase3: ItemErase
        val ivErase4: ItemErase

        val sbOpacity: ViewSeekbar
        val sbSize: ViewSeekbar

        init {
            isClickable = true
            isFocusable = true

            val cardView = CardView(context).apply {
                setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
                cardElevation = 5.556f * w
                radius = 3.5f * w
            }

            val rl = RelativeLayout(context)

            val ll = LinearLayout(context).apply {
                id = 1551
                orientation = LinearLayout.HORIZONTAL
            }

            ivErase1 = ItemErase(context).apply {
                iv.setImageResource(R.drawable.ic_erase1)
            }
            ll.addView(ivErase1, LinearLayout.LayoutParams(-1, -1, 1F).apply {
                leftMargin = (4.44f * w).toInt()
                rightMargin = (4.44f * w).toInt()
            })
            ivErase2 = ItemErase(context).apply {
                iv.setImageResource(R.drawable.ic_erase2) }
            ll.addView(ivErase2, LinearLayout.LayoutParams(-1, -1, 1F).apply {
                leftMargin = (4.44f * w).toInt()
                rightMargin = (4.44f * w).toInt()
            })
            ivErase3 = ItemErase(context).apply {
                iv.setImageResource(R.drawable.ic_erase3) }
            ll.addView(ivErase3, LinearLayout.LayoutParams(-1, -1, 1F).apply {
                leftMargin = (4.44f * w).toInt()
                rightMargin = (4.44f * w).toInt()
            })
            ivErase4 = ItemErase(context).apply {
                iv.setImageResource(R.drawable.ic_erase4) }
            ll.addView(ivErase4, LinearLayout.LayoutParams(-1, -1, 1F).apply {
                leftMargin = (4.44f * w).toInt()
                rightMargin = (4.44f * w).toInt()
            })

            rl.addView(ll, LayoutParams(-1, (9.07 * w).toInt()).apply {
                topMargin = (9.2f * w).toInt()
                leftMargin = (11.11f * w).toInt()
                rightMargin = (11.11f * w).toInt()
            })

            sbOpacity = ViewSeekbar(context).apply { id = 1645 }
            rl.addView(sbOpacity, LayoutParams(-1, (10.92f * w).toInt()).apply {
                addRule(BELOW, ll.id)
                topMargin = (7.4f * w).toInt()
                leftMargin = (4.62f * w).toInt()
                rightMargin = (4.62f * w).toInt()
            })

            sbSize = ViewSeekbar(context).apply {
                tvSeekBar.text = resources.getString(R.string.size)
                tvProgress.text = resources.getString(R.string.size5)
                seekbar.apply {
                    setMax(10)
                    setProgress(5)
                }
            }
            rl.addView(sbSize, LayoutParams(-1, (10.92f * w).toInt()).apply {
                addRule(BELOW, sbOpacity.id)
                topMargin = (7.4f * w).toInt()
                leftMargin = (4.62f * w).toInt()
                rightMargin = (4.62f * w).toInt()
            })

            cardView.addView(rl, -1, -1)
            addView(cardView, LayoutParams(-1, -1).apply {
                addRule(ALIGN_PARENT_BOTTOM, TRUE)
                topMargin = (4.44f * w).toInt()
                leftMargin = (4.44f * w).toInt()
                rightMargin = (4.44f * w).toInt()
                bottomMargin = (-3.72f * w).toInt()
            })
        }

        fun chooseErase(option: Int) {
            when(option) {
                0 -> {
                    ivErase1.ivTick.visibility = VISIBLE

                    ivErase2.ivTick.visibility = GONE
                    ivErase3.ivTick.visibility = GONE
                    ivErase4.ivTick.visibility = GONE
                }
                1 -> {
                    ivErase2.ivTick.visibility = VISIBLE

                    ivErase1.ivTick.visibility = GONE
                    ivErase3.ivTick.visibility = GONE
                    ivErase4.ivTick.visibility = GONE
                }
                2 -> {
                    ivErase3.ivTick.visibility = VISIBLE

                    ivErase1.ivTick.visibility = GONE
                    ivErase2.ivTick.visibility = GONE
                    ivErase4.ivTick.visibility = GONE
                }
                3 -> {
                    ivErase4.ivTick.visibility = VISIBLE

                    ivErase1.ivTick.visibility = GONE
                    ivErase2.ivTick.visibility = GONE
                    ivErase3.ivTick.visibility = GONE
                }
            }
        }

        inner class ItemErase(context: Context): RelativeLayout(context) {

            val iv: ImageView
            val ivTick: ImageView

            init {
                iv = ImageView(context)
                addView(iv, -1, -1)

                ivTick = ImageView(context).apply { setImageResource(R.drawable.ic_tick_blend) }
                addView(ivTick, LayoutParams((4.44f * w).toInt(), (4.44f * w).toInt()).apply {
                    addRule(CENTER_IN_PARENT, TRUE)
                })
            }
        }
    }

    inner class ViewChooseImgBlend(context: Context): RelativeLayout(context) {

        val tabLayout: TabLayout
        val viewPager: ViewPager2

        init {
            isClickable = true
            isFocusable = true

            val cardView = CardView(context).apply {
                setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
                cardElevation = 5.556f * w
                radius = 3.5f * w
            }

            val rl = RelativeLayout(context).apply {
                setBackgroundColor(ContextCompat.getColor(context, R.color.gray_main))
            }

            tabLayout = TabLayout(context).apply {
                id = 1222
                setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                setTabRippleColorResource(R.color.trans)
                setSelectedTabIndicatorColor(ContextCompat.getColor(context, R.color.trans))
                tabMode = TabLayout.MODE_SCROLLABLE
                isHorizontalScrollBarEnabled = false
            }
            rl.addView(tabLayout, LayoutParams(-1, (19.81f * w).toInt()))

            viewPager = ViewPager2(context)
            rl.addView(viewPager, LayoutParams(-1, -1).apply {
                addRule(BELOW, tabLayout.id)
            })

            cardView.addView(rl, LayoutParams(-1, -1))

            addView(cardView, LayoutParams(-1, -1).apply {
                addRule(ALIGN_PARENT_BOTTOM, TRUE)
                topMargin = (4.44f * w).toInt()
                leftMargin = (4.44f * w).toInt()
                rightMargin = (4.44f * w).toInt()
                bottomMargin = (-3.72f * w).toInt()
            })
        }
    }

    inner class ImageAvatar(context: Context) : RelativeLayout(context) {

        val iv: RoundedImageView
        val ivEdit : ImageView

        init {
            iv = RoundedImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                setImageResource(R.drawable.im_place_holder)
//                cornerRadius = 20 * w
                isOval = true
            }
            addView(iv, -1, -1)

            ivEdit = ImageView(context).apply { setImageResource(R.drawable.ic_edit_avatar) }
            addView(ivEdit, LayoutParams((4.44f * w).toInt(), (4.44f * w).toInt()).apply {
                addRule(ALIGN_PARENT_BOTTOM, TRUE)
                addRule(CENTER_HORIZONTAL, TRUE)
                bottomMargin = (2.96f * w).toInt()
            })
        }
    }
}