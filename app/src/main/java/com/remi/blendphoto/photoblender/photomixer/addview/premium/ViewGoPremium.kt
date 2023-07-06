package com.remi.blendphoto.photoblender.photomixer.addview.premium

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.remi.blendphoto.photoblender.photomixer.R
import com.remi.blendphoto.photoblender.photomixer.utils.Utils
import com.remi.blendphoto.photoblender.photomixer.utils.UtilsView.textCustom
import com.google.android.material.card.MaterialCardView

@SuppressLint("ResourceType")
class ViewGoPremium(context: Context) : RelativeLayout(context) {

    companion object {
        var w = 0f
    }

    val cardExperience: MaterialCardView
    val cardStandard : MaterialCardView
    val cardSavings : MaterialCardView

    init {
        w = resources.displayMetrics.widthPixels / 100F

        val ivBg = ImageView(context).apply {
            setImageResource(R.drawable.im_bg_premium_2)
            scaleType = ImageView.ScaleType.FIT_XY
        }
        addView(ivBg, -1, -1)

        val ivPremium = ImageView(context).apply {
            id = 1221
            setImageResource(R.drawable.ic_premium)
        }
        addView(ivPremium, LayoutParams((15.37f * w).toInt(), (15.37f * w).toInt()).apply {
            addRule(CENTER_HORIZONTAL, TRUE)
            topMargin = (27.59f * w).toInt()
        })

        val tv = TextView(context).apply {
            id = 1223
            textCustom(
                "Go Premium",
                ContextCompat.getColor(context, R.color.black_main),
                8.14f * w,
                "alegreya_medium",
                context
            )
        }
        addView(tv, LayoutParams(-2, -2).apply {
            addRule(BELOW, ivPremium.id)
            addRule(CENTER_HORIZONTAL, TRUE)
            topMargin = (3.425f * w).toInt()
        })

        val tvDes = TextView(context).apply {
            id = 1224
            textCustom(
                "Upgrade to premium today to use the full \nfeatures of the app",
                ContextCompat.getColor(context, R.color.black_main),
                3.33f * w,
                "alegreya_regular",
                context
            )
            gravity = Gravity.CENTER
        }
        addView(tvDes, LayoutParams(-2, -2).apply {
            addRule(BELOW, tv.id)
            addRule(CENTER_HORIZONTAL, TRUE)
            topMargin = (0.37f * w).toInt()
        })

        val iv = ImageView(context).apply {
            id = 1225
            setImageResource(R.drawable.im_bg_premium_3)
        }
        addView(iv, LayoutParams(-1, (12.407f * w).toInt()).apply {
            leftMargin = (18.518f * w).toInt()
            rightMargin = (18.518f * w).toInt()
            topMargin = (5f * w).toInt()
            addRule(BELOW, tvDes.id)
        })

        val tvContinue = TextView(context).apply {
            id = 1226
            textCustom(
                "Go Premium",
                ContextCompat.getColor(context, R.color.black_main),
                5.92f * w,
                "alegreya_regular",
                context
            )
            gravity = Gravity.CENTER
            background = Utils.createBackground(
                intArrayOf(ContextCompat.getColor(context, R.color.color_main)),
                (2.5f * w).toInt(),
                -1, -1
            )
        }
        addView(tvContinue, LayoutParams(-1, (16.94f * w).toInt()).apply {
            addRule(ALIGN_PARENT_BOTTOM, TRUE)
            leftMargin = (4.44f * w).toInt()
            rightMargin = (4.44f * w).toInt()
            bottomMargin = (10.185f * w).toInt()
        })

        val tvDes2 = TextView(context).apply {
            id = 1227
            textCustom(
                "The system will automatically expire after each cycle, \nyou can cancel it in the settings.",
                Color.parseColor("#A4A4A4"),
                2.96f * w,
                "alegreya_regular",
                context
            )
            gravity = Gravity.CENTER
        }
        addView(tvDes2, LayoutParams(-2, -2).apply {
            addRule(CENTER_HORIZONTAL, TRUE)
            addRule(ABOVE, tvContinue.id)
            bottomMargin = (4.81f * w).toInt()
        })

        val rl = RelativeLayout(context)

        cardStandard = MaterialCardView(context).apply {
            radius = 10.5f * w
            id = 1998
        }
        val rlStandard = RelativeLayout(context).apply {
            background = Utils.createBackground(
                intArrayOf(Color.parseColor("#DADADA")), -1, -1, -1
            )
        }
        val llPriceStandard = LinearLayout(context).apply { orientation = LinearLayout.HORIZONTAL }
        val tvPriceStandard = TextView(context).apply {
            textCustom(
                resources.getString(R.string.price_standard),
                ContextCompat.getColor(context, R.color.black_main),
                5.92f * w,
                "alegreya_regular",
                context
            )
        }
        llPriceStandard.addView(tvPriceStandard, -2, -2)
        val tvMonth = TextView(context).apply {
            textCustom(
                resources.getString(R.string.month),
                ContextCompat.getColor(context, R.color.black_main),
                3.7f * w,
                "alegreya_regular",
                context
            )
        }
        llPriceStandard.addView(tvMonth, -2, -2)
        rlStandard.addView(llPriceStandard, LayoutParams(-2, -2).apply {
            addRule(CENTER_IN_PARENT, TRUE)
        })
        val tvStandard = TextView(context).apply {
            textCustom(
                resources.getString(R.string.standard),
                ContextCompat.getColor(context, R.color.black_main),
                2.778f * w,
                "alegreya_regular",
                context
            )
            background = Utils.createBackground(
                intArrayOf(ContextCompat.getColor(context, R.color.color_main)),
                (2.5f * w).toInt(), -1, -1
            )
            gravity = Gravity.CENTER
        }
        rlStandard.addView(tvStandard, LayoutParams((19.44f * w).toInt(), (8.2f * w).toInt()).apply {
            addRule(ALIGN_PARENT_END, TRUE)
            topMargin = (-1.2f * w).toInt()
        })
        cardStandard.addView(rlStandard, -1, -1)
        rl.addView(cardStandard, LayoutParams(-1, (18.7f * w).toInt()).apply {
            leftMargin = (11.85f * w).toInt()
            rightMargin = (11.85f * w).toInt()
            topMargin = (1.48f * w).toInt()
            bottomMargin = (1.48f * w).toInt()
            addRule(CENTER_IN_PARENT, TRUE)
        })

        cardExperience = MaterialCardView(context).apply { radius = 10.5f * w }
        val rlExperience = RelativeLayout(context).apply {
            background = Utils.createBackground(
                intArrayOf(Color.parseColor("#DADADA")), -1, -1, -1
            )
        }
        val llPriceExperience = LinearLayout(context).apply { orientation = LinearLayout.HORIZONTAL }
        val tvPriceExperience = TextView(context).apply {
            textCustom(
                resources.getString(R.string.price_experience),
                ContextCompat.getColor(context, R.color.black_main),
                5.92f * w,
                "alegreya_regular",
                context
            )
        }
        llPriceExperience.addView(tvPriceExperience, -2, -2)
        val tvWeek = TextView(context).apply {
            textCustom(
                resources.getString(R.string.week),
                ContextCompat.getColor(context, R.color.black_main),
                3.7f * w,
                "alegreya_regular",
                context
            )
        }
        llPriceExperience.addView(tvWeek, -2, -2)
        rlExperience.addView(llPriceExperience, LayoutParams(-2, -2).apply {
            addRule(CENTER_IN_PARENT, TRUE)
        })
        val tvExperience = TextView(context).apply {
            textCustom(
                resources.getString(R.string.experience),
                ContextCompat.getColor(context, R.color.black_main),
                2.778f * w,
                "alegreya_regular",
                context
            )
            background = Utils.createBackground(
                intArrayOf(Color.parseColor("#21D03D")),
                (2.5f * w).toInt(), -1, -1
            )
            gravity = Gravity.CENTER
        }
        rlExperience.addView(tvExperience, LayoutParams((19.44f * w).toInt(), (8.2f * w).toInt()).apply {
            addRule(ALIGN_PARENT_END, TRUE)
            topMargin = (-1.2f * w).toInt()
        })
        cardExperience.addView(rlExperience, -1, -1)
        rl.addView(cardExperience, LayoutParams(-1, (18.7f * w).toInt()).apply {
            leftMargin = (11.85f * w).toInt()
            rightMargin = (11.85f * w).toInt()
            topMargin = (1.48f * w).toInt()
            bottomMargin = (1.48f * w).toInt()
            addRule(ABOVE, cardStandard.id)
        })

        cardSavings = MaterialCardView(context).apply { radius = 10.5f * w }
        val rlSavings = RelativeLayout(context).apply {
            background = Utils.createBackground(
                intArrayOf(Color.parseColor("#DADADA")), -1, -1, -1
            )
        }
        val llPriceSavings = LinearLayout(context).apply { orientation = LinearLayout.HORIZONTAL }
        val tvPriceSavings = TextView(context).apply {
            textCustom(
                resources.getString(R.string.price_savings),
                ContextCompat.getColor(context, R.color.black_main),
                5.92f * w,
                "alegreya_regular",
                context
            )
        }
        llPriceSavings.addView(tvPriceSavings, -2, -2)
        val tvYear = TextView(context).apply {
            textCustom(
                resources.getString(R.string.year),
                ContextCompat.getColor(context, R.color.black_main),
                3.7f * w,
                "alegreya_regular",
                context
            )
        }
        llPriceSavings.addView(tvYear, -2, -2)
        rlSavings.addView(llPriceSavings, LayoutParams(-2, -2).apply {
            addRule(CENTER_IN_PARENT, TRUE)
        })
        val tvSavings = TextView(context).apply {
            textCustom(
                resources.getString(R.string.savings),
                ContextCompat.getColor(context, R.color.black_main),
                2.778f * w,
                "alegreya_regular",
                context
            )
            background = Utils.createBackground(
                intArrayOf(Color.parseColor("#FF5959")),
                (2.5f * w).toInt(), -1, -1
            )
            gravity = Gravity.CENTER
        }
        rlSavings.addView(tvSavings, LayoutParams((19.44f * w).toInt(), (8.2f * w).toInt()).apply {
            addRule(ALIGN_PARENT_END, TRUE)
            topMargin = (-1.2f * w).toInt()
        })
        cardSavings.addView(rlSavings, -1, -1)
        rl.addView(cardSavings, LayoutParams(-1, (18.7f * w).toInt()).apply {
            leftMargin = (11.85f * w).toInt()
            rightMargin = (11.85f * w).toInt()
            topMargin = (1.48f * w).toInt()
            bottomMargin = (1.48f * w).toInt()
            addRule(BELOW, cardStandard.id)
        })

        addView(rl, LayoutParams(-1, -1).apply {
            addRule(ABOVE, tvDes2.id)
            addRule(BELOW, iv.id)
        })

        chooseOption(1)
    }

    fun chooseOption(option: Int) {
        when(option) {
            0 -> {
                cardExperience.apply {
                    strokeWidth = (0.55f * w).toInt()
                    strokeColor = ContextCompat.getColor(context, R.color.color_main)
                }

                cardStandard.apply {
                    strokeWidth = (0.55f * w).toInt()
                    strokeColor = ContextCompat.getColor(context, R.color.trans)
                }
                cardSavings.apply {
                    strokeWidth = (0.55f * w).toInt()
                    strokeColor = ContextCompat.getColor(context, R.color.trans)
                }
            }
            1 -> {
                cardStandard.apply {
                    strokeWidth = (0.55f * w).toInt()
                    strokeColor = ContextCompat.getColor(context, R.color.color_main)
                }

                cardExperience.apply {
                    strokeWidth = (0.55f * w).toInt()
                    strokeColor = ContextCompat.getColor(context, R.color.trans)
                }
                cardSavings.apply {
                    strokeWidth = (0.55f * w).toInt()
                    strokeColor = ContextCompat.getColor(context, R.color.trans)
                }
            }
            2 -> {
                cardSavings.apply {
                    strokeWidth = (0.55f * w).toInt()
                    strokeColor = ContextCompat.getColor(context, R.color.color_main)
                }

                cardExperience.apply {
                    strokeWidth = (0.55f * w).toInt()
                    strokeColor = ContextCompat.getColor(context, R.color.trans)
                }
                cardStandard.apply {
                    strokeWidth = (0.55f * w).toInt()
                    strokeColor = ContextCompat.getColor(context, R.color.trans)
                }
            }
        }
    }
}