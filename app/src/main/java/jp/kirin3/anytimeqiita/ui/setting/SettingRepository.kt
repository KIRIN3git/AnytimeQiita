package jp.kirin3.anytimeqiita.ui.setting

import android.content.Context
import androidx.lifecycle.ViewModel
import jp.kirin3.anytimeqiita.api.ApiClient
import jp.kirin3.anytimeqiita.data.AccessTokenResponseData
import jp.kirin3.anytimeqiita.data.AuthenticatedUserData
import jp.kirin3.anytimeqiita.database.AuthenticatedUserDatabase
import jp.kirin3.anytimeqiita.model.LoginModel
import kirin3.jp.mljanken.util.LogUtils

class SettingRepository() : ViewModel(), SettingDataSource {

    companion object {
        private var INSTANCE: SettingRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.

         * @param tasksRemoteDataSource the backend data source
         * *
         * @param tasksLocalDataSource  the device storage data source
         * *
         * @return the [TasksRepository] instance
         */
        fun getInstance(): SettingRepository {
            return INSTANCE ?: SettingRepository()
                .apply { INSTANCE = this }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        fun destroyInstance() {
            INSTANCE = null
        }

        interface LoadCallback {
            fun onSuccess()
            fun onFailure()
        }
    }

    override fun loadAccessToken(
        context: Context?,
        code: String?,
        callback: SettingDataSource.LoadAccessTokenCallback
    ) {
        if (context == null || code == null) {
            return
        }

        // アクセストークンの初期化
        LoginModel.clearAccessToken(context)

        ApiClient.fetchAccessToken(code, object : ApiClient.AccessTokenApiCallback {
            override fun onTasksLoaded(responseData: AccessTokenResponseData) {
                LogUtils.LOGI("")
                LoginModel.setAccessToken(context, responseData.token)
                callback.onAccessTokenLoaded()
            }

            override fun onDataNotAvailable() {
                LogUtils.LOGI("")
                callback.onAccessTokenNotAvailable()
            }
        })
    }

    override fun loadAuthenticatedUser(
        context: Context?,
        accessToken: String?,
        callback: SettingDataSource.LoadAuthenticatedUserCallback
    ) {
        if (context == null) return

        ApiClient.fetchAuthenticatedUser(
            accessToken,
            object : ApiClient.AuthenticatedUserApiCallback {
                override fun onTasksLoaded(responseData: AuthenticatedUserData) {
                    LogUtils.LOGI("GET AuthenticatedUser responseData.id = " + responseData.id)

                    // データをデータベース保存
                    LoginModel.insertAuthenticatedUserData(responseData)
                    callback.onAuthenticatedUserLoaded()
                }

                override fun onDataNotAvailable() {
                    LogUtils.LOGI("Fail fetchAuthenticatedUser")

                    LoginModel.clearAuthenticatedUser()
                    callback.onAuthenticatedUserNotAvailable()
                }
            })
    }
}