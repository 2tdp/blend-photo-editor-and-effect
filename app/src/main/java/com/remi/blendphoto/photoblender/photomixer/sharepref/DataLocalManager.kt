package com.remi.blendphoto.photoblender.photomixer.sharepref

import android.content.Context
import com.remi.blendphoto.photoblender.photomixer.model.picture.BucketPicModel
import com.google.gson.Gson
import com.remi.blendphoto.photoblender.photomixer.model.picture.PicModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class DataLocalManager {
    private var mySharedPreferences: MySharePreferences? = null

    companion object {
        private var instance: DataLocalManager? = null
        fun init(context: Context) {
            instance = DataLocalManager()
            instance!!.mySharedPreferences = MySharePreferences(context)
        }

        private fun getInstance(): DataLocalManager? {
            if (instance == null) instance = DataLocalManager()
            return instance
        }

        fun setFirstInstall(key: String?, isFirst: Boolean) {
            getInstance()!!.mySharedPreferences!!.putBooleanValue(key, isFirst)
        }

        fun getFirstInstall(key: String?): Boolean {
            return getInstance()!!.mySharedPreferences!!.getBooleanValue(key)
        }

        fun setCheck(key: String?, volumeOn: Boolean) {
            getInstance()!!.mySharedPreferences!!.putBooleanValue(key, volumeOn)
        }

        fun getCheck(key: String?): Boolean {
            return getInstance()!!.mySharedPreferences!!.getBooleanValue(key)
        }

        fun setOption(option: String?, key: String?) {
            getInstance()!!.mySharedPreferences!!.putStringwithKey(key, option)
        }

        fun getOption(key: String?): String? {
            return getInstance()!!.mySharedPreferences!!.getStringwithKey(key, "")
        }

        fun setInt(count: Int, key: String?) {
            getInstance()!!.mySharedPreferences!!.putIntWithKey(key, count)
        }

        fun getInt(key: String?): Int {
            return getInstance()!!.mySharedPreferences!!.getIntWithKey(key, -1)
        }

        fun setListInt(lstIndexPremium: ArrayList<Int>, key: String) {
            getInstance()!!.mySharedPreferences!!.putStringwithKey(key, lstIndexPremium.toString())
        }

        fun getListInt(key: String): ArrayList<Int> {
            val lstIndexPremium = ArrayList<Int>()
            try {
                val jsonArray = JSONArray(getInstance()!!.mySharedPreferences!!.getStringwithKey(key, ""))
                for (i in 0 until jsonArray.length())
                    lstIndexPremium.add(jsonArray[i] as Int)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return lstIndexPremium
        }

        fun setPicture(picture: PicModel, key: String) {
            val json = Gson().toJsonTree(picture).asJsonObject.toString()
            getInstance()!!.mySharedPreferences!!.putStringwithKey(key, json)
        }

        fun getPicture(key: String): PicModel? {
            val strJson = getInstance()!!.mySharedPreferences!!.getStringwithKey(key, "")
            var picture: PicModel? = null
            try {
                strJson?.let {
                    picture = Gson().fromJson(JSONObject(it).toString(), PicModel::class.java)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return picture
        }

        fun setListBucket(lstBucket: ArrayList<BucketPicModel>, key: String) {
            val gson = Gson()
            val jsonArray = gson.toJsonTree(lstBucket).asJsonArray
            val json = jsonArray.toString()
            getInstance()!!.mySharedPreferences!!.putStringwithKey(key, json)
        }

        fun getListBucket(key: String): ArrayList<BucketPicModel> {
            val gson = Gson()
            var jsonObject: JSONObject
            val lstBucket: ArrayList<BucketPicModel> = ArrayList()
            val strJson = getInstance()!!.mySharedPreferences!!.getStringwithKey(key, "")
            try {
                val jsonArray = JSONArray(strJson)
                for (i in 0 until jsonArray.length()) {
                    jsonObject = jsonArray.getJSONObject(i)
                    lstBucket.add(gson.fromJson(jsonObject.toString(), BucketPicModel::class.java))
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return lstBucket
        }
    }
}