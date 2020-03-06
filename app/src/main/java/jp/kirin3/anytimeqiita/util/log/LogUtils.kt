package kirin3.jp.mljanken.util

import android.os.Build
import timber.log.Timber

object LogUtils {

    fun LOGD(any: Any) {
        if(any == null) return
        Timber.d(any.toString())
    }

    fun LOGV(message: String) {
        Timber.v(message)
    }

    fun LOGI(message: String) {
        Timber.i(message)
    }

    fun LOGW(message: String) {
        Timber.w(message)
    }

    fun LOGE(message: String) {
        Timber.e(message)
    }
}