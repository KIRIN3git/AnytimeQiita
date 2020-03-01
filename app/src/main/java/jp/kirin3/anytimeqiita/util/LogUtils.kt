package kirin3.jp.mljanken.util

import android.util.Log
import jp.kirin3.anytimeqiita.Config


object LogUtils {
    private val LOG_PREFIX = "util_"

    fun makeLogTag(cls: Class<*>): String {
        return makeLogTag(cls.simpleName)
    }

    private fun makeLogTag(str: String): String {
        return LOG_PREFIX + str
    }


    fun LOGD(tag: String, message: String) {
        if (Config.IS_DOGFOOD_BUILD) {
            Log.d(tag, message)
        }
    }

    fun LOGV(tag: String, message: String) {
        if (Config.IS_DOGFOOD_BUILD) {
            Log.v(tag, message)
        }
    }

    fun LOGI(tag: String, message: String) {
        if (Config.IS_DOGFOOD_BUILD) {
            Log.i(tag, message)
        }
    }

    fun LOGW(tag: String, message: String) {
        if (Config.IS_DOGFOOD_BUILD) {
            Log.w(tag, message)
        }
    }

    fun LOGE(tag: String, message: String) {
        if (Config.IS_DOGFOOD_BUILD) {
            Log.e(tag, message)
        }
    }

}