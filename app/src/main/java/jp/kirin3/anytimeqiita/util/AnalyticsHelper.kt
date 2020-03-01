package kirin3.jp.mljanken.util

import android.content.Context
import android.os.Bundle
//import com.google.firebase.analytics.FirebaseAnalytics
//import kirin3.jp.mljanken.Config
import kirin3.jp.mljanken.util.LogUtils.LOGD

object AnalyticsHelper {
/*
    private val TAG = LogUtils.makeLogTag(AnalyticsHelper::class.java)

    private var firebaseAnalytics: FirebaseAnalytics? = null

    /****
     * スプラッシュ画面、など起動時のクラスで呼び出す必要あり
     */
    @Synchronized
    fun initializeAnalytic(context: Context) {
        try {
            firebaseAnalytics = FirebaseAnalytics.getInstance(context)
        } catch (e: Exception) {
            setAnalyticsEnabled(false)
        }
    }

    fun setAnalyticsJanken(result: String,gender: String,age: String,prefecture: String) {
        LOGD(TAG, "setAnalyticsJanken: result[$result] gender[$gender] age[$age] prefecture[$prefecture]")
        if(Config.IS_DOGFOOD_BUILD == false) {
            val bundle = Bundle()
            bundle.putString("result", result)
            bundle.putString("gender", gender)
            bundle.putString("age", age)
            bundle.putString("prefecture", prefecture)
            firebaseAnalytics!!.logEvent("janken", bundle)
        }
    }



    fun setAnalyticsConfig(key: String, value: String) {
        setAnalytics("event001", "CONFIG", key, value)
    }

    // P1,2,3,4の勝利数
    fun setAnalyticsWinNum(key: String, value: Int?) {
        setAnalytics("event002", "WIN_NUM", key, value)
    }

    // インストールからのプレイ完了数を取得
    fun setAnalyticsPlayNum(key: String, value: Int?) {
        setAnalytics("event003", "PLAY_NUM", key, value)
    }

    fun setAnalytics(id: String, name: String, key: String, value: String) {
        LOGD(TAG, "setAnalytics: id[$id]name[$name]key[$key]value[$value]")
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id)
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
        bundle.putString(key, value)
        firebaseAnalytics!!.logEvent("original_event", bundle)
    }

    fun setAnalytics(id: String, name: String, key: String, value: Int?) {
        LOGD(TAG, "setAnalytics: id[$id]name[$name]key[$key]value[$value]")
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id)
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
        bundle.putInt(key, value!!)
        firebaseAnalytics!!.logEvent("original_event", bundle)
    }

    private fun setAnalyticsEnabled(enabled: Boolean) {
        LOGD(TAG, "setAnalyticsEnabled: $enabled")
        if (firebaseAnalytics != null) {
            firebaseAnalytics!!.setAnalyticsCollectionEnabled(enabled)
        }
    }

 */
}
