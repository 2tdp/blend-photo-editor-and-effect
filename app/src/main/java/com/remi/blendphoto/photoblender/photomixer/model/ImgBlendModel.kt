package com.remi.blendphoto.photoblender.photomixer.model

import java.io.Serializable

class ImgBlendModel(var bucket: String, var uri: String, var isPremium: Boolean, var isAds: Boolean, var isCheck: Boolean) : Serializable