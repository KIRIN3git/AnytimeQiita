package jp.kirin3.anytimeqiita.helper

import android.content.Context
import jp.kirin3.anytimeqiita.api.ApiClient
import jp.kirin3.anytimeqiita.data.AccessTokenResponseData
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.SettingsUtils
import java.util.concurrent.CountDownLatch

object AccessTokenHelper {
    fun loadAccessToken(context: Context?, code: String?, latch: CountDownLatch) {
        if (context == null || code == null) {
            return
        }

        // アクセストークンの初期化
        SettingsUtils.setQiitaAccessToken(context, "")

        ApiClient.fetchAccessToken(code, object : ApiClient.AccessTokenApiCallback {
            override fun onTasksLoaded(responseData: AccessTokenResponseData) {
                LogUtils.LOGI("")
                SettingsUtils.setQiitaAccessToken(context, responseData.token)

                latch.apply { countDown() }
            }

            override fun onDataNotAvailable() {
                LogUtils.LOGI("")
                latch.apply { countDown() }
            }
        })
    }

    fun getQiitaAccessToken(context: Context?): String {
        if (context == null) return ""

        val accessToken = SettingsUtils.getQiitaAccessToken(context) ?: run {
            return ""
        }
        return accessToken
    }

}