package kirin3.jp.mljanken.util

import timber.log.Timber

object LogUtils {

    fun LOGD(any: Any) {
        if (any == null) return
        Timber.d(any.toString())
    }

    fun LOGV(any: Any) {
        if (any == null) return
        Timber.v(any.toString())
    }

    fun LOGI(any: Any) {
        if (any == "") Timber.i("CHECK_POINT")
        else Timber.i(any.toString())
    }

    fun LOGW(any: Any) {
        if (any == null) return
        Timber.w(any.toString())
    }

    fun LOGE(any: Any) {
        if (any == null) return
        Timber.e(any.toString())
    }
}