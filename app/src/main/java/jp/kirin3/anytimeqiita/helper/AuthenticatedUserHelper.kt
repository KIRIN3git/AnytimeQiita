package jp.kirin3.anytimeqiita.helper

import android.content.Context
import jp.kirin3.anytimeqiita.api.ApiClient
import jp.kirin3.anytimeqiita.api.ApiClient.fetchAuthenticatedUser
import jp.kirin3.anytimeqiita.data.AuthenticatedUserResponceData
import jp.kirin3.anytimeqiita.database.AuthenticatedUserDatabase.deleteAuthenticatedUserData
import jp.kirin3.anytimeqiita.database.AuthenticatedUserDatabase.insertAuthenticatedUserData
import jp.kirin3.anytimeqiita.database.AuthenticatedUserDatabase.selectAuthenticatedUserData
import jp.kirin3.anytimeqiita.model.AuthenticatedUserModel
import kirin3.jp.mljanken.util.LogUtils
import java.util.concurrent.CountDownLatch


object AuthenticatedUserHelper {

    val authenticatedModel: AuthenticatedUserModel = AuthenticatedUserModel()

    fun loadAuthenticatedUser(context: Context?, accessToken: String?, latch: CountDownLatch) {
        if (context == null) return

        fetchAuthenticatedUser(
            accessToken,
            object : ApiClient.AuthenticatedUserApiCallback {
                override fun onTasksLoaded(responseData: AuthenticatedUserResponceData) {
                    LogUtils.LOGI("GET AuthenticatedUser responseData.id = " + responseData.id)
                    // データをキャッシュ保存
                    authenticatedModel.setAuthenticatedUserFromCache(responseData)

                    // データをデータベース保存
                    insertAuthenticatedUserData(responseData)
                    latch.apply { countDown() }
                }

                override fun onDataNotAvailable() {
                    LogUtils.LOGI("Fail fetchAuthenticatedUser")

                    authenticatedModel.setAuthenticatedUserFromCache(null)
                    deleteAuthenticatedUserData()

                    latch.apply { countDown() }
                }
            })
    }

    fun setAuthnticatedUserToCated(){
        val userData = selectAuthenticatedUserData()
        if(userData != null){
             authenticatedModel.setAuthenticatedUserFromCache(userData)
        }
    }

    fun hasAuthenticatedUser(): Boolean {

        if (authenticatedModel.hasAuthenticatedUserIdFromCache()) {
            return true
        }

        val user = selectAuthenticatedUserData()
        if (user != null) {
            return true
        }

        return false
    }

    fun getAuthenticatedUserIdFromCashe(): String? {
        authenticatedModel.getAuthenticatedUserFromCache()?.let {
            return it.id
        }
        return null
    }
}