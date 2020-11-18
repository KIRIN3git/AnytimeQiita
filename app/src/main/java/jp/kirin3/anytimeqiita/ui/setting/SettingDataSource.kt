package jp.kirin3.anytimeqiita.ui.setting

import android.content.Context

interface SettingDataSource {
    interface LoadAccessTokenCallback {
        fun onAccessTokenLoaded()
        fun onAccessTokenNotAvailable()
    }

    interface LoadAuthenticatedUserCallback {
        fun onAuthenticatedUserLoaded()
        fun onAuthenticatedUserNotAvailable()
    }

    fun loadAccessToken(
        context: Context?,
        code: String?,
        callback: LoadAccessTokenCallback
    )

    fun loadAuthenticatedUser(
        context: Context?,
        accessToken: String?,
        callback: LoadAuthenticatedUserCallback
    )
}