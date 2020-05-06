package jp.kirin3.anytimeqiita.helper

import android.content.Context
import jp.kirin3.anytimeqiita.api.ApiClient
import jp.kirin3.anytimeqiita.data.AuthenticatedUserResponceData
import jp.kirin3.anytimeqiita.model.AuthenticatedUserModel
import jp.kirin3.anytimeqiita.util.StringUtils
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.SettingsUtils
import java.util.concurrent.CountDownLatch

object AuthenticatedUserHelper {

    val authenticatedModel: AuthenticatedUserModel = AuthenticatedUserModel()

    fun loadAuthenticatedUser(context: Context?, accessToken: String?, latch: CountDownLatch) {
        if (context == null) return
        // データをキャッシュする処理
        ApiClient.fetchAuthenticatedUser(
            accessToken,
            object : ApiClient.AuthenticatedUserApiCallback {
                override fun onTasksLoaded(responseData: AuthenticatedUserResponceData) {
                    LogUtils.LOGI("GET AuthenticatedUser responseData.id = " + responseData.id)
                    authenticatedModel.setAuthenticatedUser(responseData)
                    latch.apply { countDown() }
                }

                override fun onDataNotAvailable() {
                    LogUtils.LOGI("")
                    latch.apply { countDown() }
                }
            })
    }

    fun hasAuthenticatedUser(): Boolean {

        var authenticateuser = authenticatedModel.getAuthenticatedUser()?:let{
            return false
        }
        if(StringUtils.isEmpty(authenticateuser.id)){
            return false
        }

        return true
    }
}