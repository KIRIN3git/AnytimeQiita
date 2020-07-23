package jp.kirin3.anytimeqiita.model

import android.content.Context
import androidx.lifecycle.ViewModel
import jp.kirin3.anytimeqiita.api.ApiClient
import jp.kirin3.anytimeqiita.data.AuthenticatedUserResponceData
import jp.kirin3.anytimeqiita.database.AuthenticatedUserDatabase
import jp.kirin3.anytimeqiita.util.StringUtils
import kirin3.jp.mljanken.util.LogUtils
import java.util.concurrent.CountDownLatch

class AuthenticatedUserModel : ViewModel() {

    companion object {
        // companion object {
        private var cacheAuthenticatedUser: AuthenticatedUserResponceData? = null
        // }


        fun setAuthenticatedUserToCache(userData: AuthenticatedUserResponceData?) {
            cacheAuthenticatedUser = userData
        }

        fun getAuthenticatedUserFromCache(): AuthenticatedUserResponceData? {
            return cacheAuthenticatedUser
        }

        fun getAuthenticatedUserIdFromCache(): String? {
            return cacheAuthenticatedUser?.id
        }

        fun hasAuthenticatedUserIdFromCache(): Boolean {
            val user = getAuthenticatedUserFromCache() ?: let {
                return false
            }
            if (StringUtils.isEmpty(user.id)) {
                return false
            }
            return true
        }

        fun clearAuthenticatedUserAllInfo(){
            AuthenticatedUserDatabase.deleteAuthenticatedUserData()
            cacheAuthenticatedUser = null
        }

        fun loadAuthenticatedUser(context: Context?, accessToken: String?, latch: CountDownLatch) {
            if (context == null) return

            ApiClient.fetchAuthenticatedUser(
                accessToken,
                object : ApiClient.AuthenticatedUserApiCallback {
                    override fun onTasksLoaded(responseData: AuthenticatedUserResponceData) {
                        LogUtils.LOGI("GET AuthenticatedUser responseData.id = " + responseData.id)
                        // データをキャッシュ保存
                        setAuthenticatedUserToCache(
                            responseData
                        )

                        // データをデータベース保存
                        AuthenticatedUserDatabase.insertAuthenticatedUserData(responseData)
                        latch.apply { countDown() }
                    }

                    override fun onDataNotAvailable() {
                        LogUtils.LOGI("Fail fetchAuthenticatedUser")

                        setAuthenticatedUserToCache(
                            null
                        )
                        AuthenticatedUserDatabase.deleteAuthenticatedUserData()

                        latch.apply { countDown() }
                    }
                })
        }

        fun setAuthnticatedUserToCache(){
            val userData = AuthenticatedUserDatabase.selectAuthenticatedUserData()
            if(userData != null){
                setAuthenticatedUserToCache(userData)
            }
        }

        fun hasAuthenticatedUser(): Boolean {

            if (hasAuthenticatedUserIdFromCache()) {
                return true
            }

            val user = AuthenticatedUserDatabase.selectAuthenticatedUserData()
            if (user != null) {
                return true
            }

            return false
        }

        fun getAuthenticatedUserIdFromCashe(): String? {
            getAuthenticatedUserFromCache()?.let {
                return it.id
            }
            return null
        }
    }
}