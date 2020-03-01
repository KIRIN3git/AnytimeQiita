package kirin3.jp.mljanken.util

import timber.log.Timber

object LogUtils {

    fun LOGD(message: String) {
        Timber.d(message)
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