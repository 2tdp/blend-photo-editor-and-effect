package com.remi.blendphoto.photoblender.photomixer.addview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.utils.Utils
import com.remi.blendphoto.photoblender.photomixer.utils.UtilsView.textCustom

@SuppressLint("ResourceType")
class ViewSettings(context: Context) : RelativeLayout(context) {

    companion object{
        var w = 0F
    }

    val viewToolbar: ViewToolbar
    val viewUpgrade: ViewItemSetting
    val viewYourBlend: ViewItemSetting
    val viewRateApp: ViewItemSetting
    val viewFeedBack: ViewItemSetting
    val viewMoreApp: ViewItemSetting
    val viewShareApp: ViewItemSetting
    val viewPP: ViewItemSetting

    val viewInsta: ViewItemSetting
    val viewFb: ViewItemSetting

    init {
        w = resources.displayMetrics.widthPixels / 100F
        setBackgroundColor(Color.WHITE)

        viewToolbar = ViewToolbar(context).apply {
            id = 1221
            tvTitle.text = resources.getString(R.string.setting)
        }
        addView(viewToolbar, LayoutParams(-1, (23f * w).toInt()))

        val scroll = ScrollView(context).apply {
            isVerticalScrollBarEnabled = false
            setBackgroundColor(ContextCompat.getColor(context, R.color.gray_main))
        }
        val rl = RelativeLayout(context)

        val tvForUse = TextView(context).apply {
            id = 1551
            textCustom(
                "For Use",
                ContextCompat.getColor(context, R.color.gray_text_2),
                5.18f * w,
                "alegreya_regular",
                context
            )
        }
        rl.addView(tvForUse, LayoutParams(-2, -2).apply {
            leftMargin = (4.44f * w).toInt()
            topMargin = (4.44f * w).toInt()
        })

        val rlForUse = RelativeLayout(context).apply {
            id = 1998
            background = Utils.createBackground(
                intArrayOf(Color.WHITE),
                (2.5f * w).toInt(),
                -1, -1
            )
        }

        viewUpgrade = ViewItemSetting(context).apply {
            id = 1222
            iv.setImageResource(R.drawable.ic_up_premium)
            tv.text = resources.getString(R.string.upgrade)
        }
        rlForUse.addView(viewUpgrade, LayoutParams(-1, (11.11f * w).toInt()).apply {
            topMargin = (4.44f * w).toInt()
        })
        val v1 = View(context).apply {
            id = 1552
            setBackgroundColor(ContextCompat.getColor(context, R.color.gray_main_2))
        }
        rlForUse.addView(v1, LayoutParams(-1, (0.34f * w).toInt()).apply {
            addRule(BELOW, viewUpgrade.id)
            leftMargin = (19.44f * w).toInt()
            topMargin = (2.96f * w).toInt()
        })

        viewYourBlend = ViewItemSetting(context).apply {
            id = 1223
            iv.setImageResource(R.drawable.ic_your_blend)
            tv.text = resources.getString(R.string.your_blend_photos)
        }
        rlForUse.addView(viewYourBlend, LayoutParams(-1, (11.11f * w).toInt()).apply {
            addRule(BELOW, v1.id)
            topMargin = (2.96f * w).toInt()
        })
        val v2 = View(context).apply {
            id = 1553
            setBackgroundColor(ContextCompat.getColor(context, R.color.gray_main_2))
        }
        rlForUse.addView(v2, LayoutParams(-1, (0.34f * w).toInt()).apply {
            addRule(BELOW, viewYourBlend.id)
            leftMargin = (19.44f * w).toInt()
            topMargin = (2.96f * w).toInt()
        })

        viewRateApp = ViewItemSetting(context).apply {
            id = 1224
            iv.setImageResource(R.drawable.ic_rate_app)
            tv.text = resources.getString(R.string.rate_app)
        }
        rlForUse.addView(viewRateApp, LayoutParams(-1, (11.11f * w).toInt()).apply {
            addRule(BELOW, v2.id)
            topMargin = (4.44f * w).toInt()
        })
        val v3 = View(context).apply {
            id = 1554
            setBackgroundColor(ContextCompat.getColor(context, R.color.gray_main_2))
        }
        rlForUse.addView(v3, LayoutParams(-1, (0.34f * w).toInt()).apply {
            addRule(BELOW, viewRateApp.id)
            leftMargin = (19.44f * w).toInt()
            topMargin = (2.96f * w).toInt()
        })

        viewFeedBack = ViewItemSetting(context).apply {
            id = 1225
            iv.setImageResource(R.drawable.ic_feed_back)
            tv.text = resources.getString(R.string.feedback)
        }
        rlForUse.addView(viewFeedBack, LayoutParams(-1, (11.11f * w).toInt()).apply {
            addRule(BELOW, v3.id)
            topMargin = (4.44f * w).toInt()
        })
        val v4 = View(context).apply {
            id = 1555
            setBackgroundColor(ContextCompat.getColor(context, R.color.gray_main_2))
        }
        rlForUse.addView(v4, LayoutParams(-1, (0.34f * w).toInt()).apply {
            addRule(BELOW, viewFeedBack.id)
            leftMargin = (19.44f * w).toInt()
            topMargin = (2.96f * w).toInt()
        })

        viewMoreApp = ViewItemSetting(context).apply {
            id = 1226
            iv.setImageResource(R.drawable.ic_more_app)
            tv.text = resources.getString(R.string.more_app)
        }
        rlForUse.addView(viewMoreApp, LayoutParams(-1, (11.11f * w).toInt()).apply {
            addRule(BELOW, v4.id)
            topMargin = (4.44f * w).toInt()
        })
        val v5 = View(context).apply {
            id = 1556
            setBackgroundColor(ContextCompat.getColor(context, R.color.gray_main_2))
        }
        rlForUse.addView(v5, LayoutParams(-1, (0.34f * w).toInt()).apply {
            addRule(BELOW, viewMoreApp.id)
            leftMargin = (19.44f * w).toInt()
            topMargin = (2.96f * w).toInt()
        })

        viewShareApp = ViewItemSetting(context).apply {
            id = 1227
            iv.setImageResource(R.drawable.ic_share_app)
            tv.text = resources.getString(R.string.share_app)
        }
        rlForUse.addView(viewShareApp, LayoutParams(-1, (11.11f * w).toInt()).apply {
            addRule(BELOW, v5.id)
            topMargin = (4.44f * w).toInt()
        })
        val v6 = View(context).apply {
            id = 1557
            setBackgroundColor(ContextCompat.getColor(context, R.color.gray_main_2))
        }
        rlForUse.addView(v6, LayoutParams(-1, (0.34f * w).toInt()).apply {
            addRule(BELOW, viewShareApp.id)
            leftMargin = (19.44f * w).toInt()
            topMargin = (2.96f * w).toInt()
        })

        viewPP = ViewItemSetting(context).apply {
            id = 1228
            iv.setImageResource(R.drawable.ic_pp)
            tv.text = resources.getString(R.string.privacy_policy)
        }
        rlForUse.addView(viewPP, LayoutParams(-1, (11.11f * w).toInt()).apply {
            addRule(BELOW, v6.id)
            topMargin = (4.44f * w).toInt()
            bottomMargin = (4.44f * w).toInt()
        })
        rl.addView(rlForUse, LayoutParams(-1, -2).apply {
            addRule(BELOW, tvForUse.id)
            leftMargin = (4.44f * w).toInt()
            rightMargin = (4.44f * w).toInt()
            topMargin = (1.48f * w).toInt()
        })

        val tvFollowUs = TextView(context).apply {
            id = 1331
            textCustom(
                "Follow Us",
                ContextCompat.getColor(context, R.color.gray_text_2),
                5.18f * w,
                "alegreya_regular",
                context
            )
        }
        rl.addView(tvFollowUs, LayoutParams(-2, -2).apply {
            addRule(BELOW, rlForUse.id)
            leftMargin = (4.44f * w).toInt()
            topMargin = (4.44f * w).toInt()
        })

        val rlFollowUs = RelativeLayout(context).apply {
            background = Utils.createBackground(
                intArrayOf(Color.WHITE),
                (2.5f * w).toInt(),
                -1, -1
            )
        }

        viewInsta = ViewItemSetting(context).apply {
            id = 1222
            iv.setImageResource(R.drawable.ic_insta)
            tv.text = resources.getString(R.string.ig)
        }
        rlFollowUs.addView(viewInsta, LayoutParams(-1, (11.11f * w).toInt()).apply {
            topMargin = (4.44f * w).toInt()
        })
        val v12 = View(context).apply {
            id = 1552
            setBackgroundColor(ContextCompat.getColor(context, R.color.gray_main_2))
        }
        rlFollowUs.addView(v12, LayoutParams(-1, (0.34f * w).toInt()).apply {
            addRule(BELOW, viewInsta.id)
            leftMargin = (19.44f * w).toInt()
            topMargin = (2.96f * w).toInt()
        })

        viewFb = ViewItemSetting(context).apply {
            id = 1223
            iv.setImageResource(R.drawable.ic_fb)
            tv.text = resources.getString(R.string.fb)
        }
        rlFollowUs.addView(viewFb, LayoutParams(-1, (11.11f * w).toInt()).apply {
            addRule(BELOW, v12.id)
            topMargin = (2.96f * w).toInt()
            bottomMargin = (4.44f * w).toInt()
        })
        rl.addView(rlFollowUs, LayoutParams(-1, -2).apply {
            addRule(BELOW, tvFollowUs.id)
            leftMargin = (4.44f * w).toInt()
            rightMargin = (4.44f * w).toInt()
            topMargin = (1.48f * w).toInt()
            bottomMargin = (4.44f * w).toInt()
        })

        scroll.addView(rl, -1, -1)

        addView(scroll, LayoutParams(-1, -1).apply {
            addRule(BELOW, viewToolbar.id)
        })
    }

    class ViewItemSetting(context: Context) : RelativeLayout(context) {

        val iv: ImageView
        val tv: TextView

        init {
            iv = ImageView(context).apply { id = 1221 }
            addView(iv, LayoutParams((11.11f * w).toInt(), -1).apply {
                leftMargin = (4.44f * w).toInt()
            })

            tv = TextView(context).apply {
                textCustom(
                    "",
                    ContextCompat.getColor(context, R.color.black),
                    4.44f * w,
                    "alegreya_regular",
                    context
                )
            }
            addView(tv, LayoutParams(-2, -2).apply {
                addRule(CENTER_VERTICAL, TRUE)
                addRule(RIGHT_OF, iv.id)
                leftMargin = (4.44f * w).toInt()
            })

            val ivRight = ImageView(context).apply { setImageResource(R.drawable.ic_right) }
            addView(ivRight, LayoutParams((5.92f * w).toInt(), (5.92f * w).toInt()).apply {
                addRule(ALIGN_PARENT_END, TRUE)
                addRule(CENTER_VERTICAL, TRUE)
                rightMargin = (4.44f * w).toInt()
            })
        }
    }
}