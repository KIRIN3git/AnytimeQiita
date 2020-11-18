package jp.kirin3.anytimeqiita.model

import android.content.Context
import androidx.lifecycle.ViewModel
import jp.kirin3.anytimeqiita.api.ApiClient
import jp.kirin3.anytimeqiita.data.AuthenticatedUserData
import jp.kirin3.anytimeqiita.database.AuthenticatedUserDatabase
import jp.kirin3.anytimeqiita.util.StringUtils
import kirin3.jp.mljanken.util.LogUtils

class AuthenticatedUserModel : ViewModel() {

    interface LoadCallback {
        fun onSuccess()
        fun onFailure()
    }

    companion object {
        private var cacheAuthenticatedUser: AuthenticatedUserData? = null

        fun setAuthenticatedUserToCache(userData: AuthenticatedUserData?) {
            cacheAuthenticatedUser = userData
        }

        fun getAuthenticatedUserFromCache(): AuthenticatedUserData? {
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

        fun clearAuthenticatedUserFromDbAndCache() {
            AuthenticatedUserDatabase.deleteAuthenticatedUserData()
            cacheAuthenticatedUser = null
        }

//        fun loadAuthenticatedUser(context: Context?, accessToken: String?, callback: LoadCallback) {
//            if (context == null) return
//
//            ApiClient.fetchAuthenticatedUser(
//                accessToken,
//                object : ApiClient.AuthenticatedUserApiCallback {
//                    override fun onTasksLoaded(responseData: AuthenticatedUserData) {
//                        LogUtils.LOGI("GET AuthenticatedUser responseData.id = " + responseData.id)
//                        // データをキャッシュ保存
//                        setAuthenticatedUserToCache(
//                            responseData
//                        )
//
//                        // データをデータベース保存
//                        AuthenticatedUserDatabase.insertAuthenticatedUserData(responseData)
//                        callback.onSuccess()
//                    }
//
//                    override fun onDataNotAvailable() {
//                        LogUtils.LOGI("Fail fetchAuthenticatedUser")
//
//                        setAuthenticatedUserToCache(
//                            null
//                        )
//                        AuthenticatedUserDatabase.deleteAuthenticatedUserData()
//                        callback.onFailure()
//                    }
//                })
//        }

        fun setAuthnticatedUserFromDbToCache() {
            val userData = AuthenticatedUserDatabase.selectAuthenticatedUserData()
            if (userData != null) {
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

        fun getAuthenticatedUserIdFromCacheOrDb(): String? {
            getAuthenticatedUserFromCache()?.let {
                return it.id
            }
            setAuthnticatedUserFromDbToCache()
            getAuthenticatedUserFromCache()?.let {
                return it.id
            }
            return null
        }
    }
}