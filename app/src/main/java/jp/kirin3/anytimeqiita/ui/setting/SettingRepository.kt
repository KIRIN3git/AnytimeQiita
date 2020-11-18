package jp.kirin3.anytimeqiita.ui.setting

import android.content.Context
import androidx.lifecycle.ViewModel
import jp.kirin3.anytimeqiita.api.ApiClient
import jp.kirin3.anytimeqiita.data.AccessTokenResponseData
import jp.kirin3.anytimeqiita.data.AuthenticatedUserData
import jp.kirin3.anytimeqiita.database.AuthenticatedUserDatabase
import jp.kirin3.anytimeqiita.model.AuthenticatedUserModel
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.SettingsUtils

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
        SettingsUtils.setQiitaAccessToken(context, "")

        ApiClient.fetchAccessToken(code, object : ApiClient.AccessTokenApiCallback {
            override fun onTasksLoaded(responseData: AccessTokenResponseData) {
                LogUtils.LOGI("")
                SettingsUtils.setQiitaAccessToken(context, responseData.token)
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
                    // データをキャッシュ保存
                    AuthenticatedUserModel.setAuthenticatedUserToCache(
                        responseData
                    )

                    // データをデータベース保存
                    AuthenticatedUserDatabase.insertAuthenticatedUserData(responseData)
                    callback.onAuthenticatedUserLoaded()
                }

                override fun onDataNotAvailable() {
                    LogUtils.LOGI("Fail fetchAuthenticatedUser")

                    AuthenticatedUserModel.setAuthenticatedUserToCache(
                        null
                    )
                    AuthenticatedUserDatabase.deleteAuthenticatedUserData()
                    callback.onAuthenticatedUserNotAvailable()
                }
            })
    }
}