package jp.kirin3.anytimeqiita.model

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import jp.kirin3.anytimeqiita.data.AuthenticatedUserData
import jp.kirin3.anytimeqiita.database.AuthenticatedUserDatabase
import kirin3.jp.mljanken.util.LogUtils.LOGE
import kirin3.jp.mljanken.util.LogUtils.LOGI
import kirin3.jp.mljanken.util.SettingsUtils

/**
 * ログインモデル
 *
 * State：Qiitaログイン画面アクセス時に送信される
 * 　利用シーン：同一値が返却されることを確認してセキュリティを高めるのに使用される
 * QiitaCode：Qiitaログイン後パラメータとしてアプリに通知され、Preferenceに保存される
 * 　利用シーン：AccessTokenを取得するのに使用される
 * AccessToken：QiitaCodeを使用してQiitaAPIにて取得されPreferenceに保存。
 * 　利用シーン：各APIを叩くのに使用される。
 * AuthenticatedUser：AccessTokenを使用してQiitaAPIにて取得されるログインユーザーデータ、DBに保存。
 * 　利用シーン：IDの表示やIDを利用して各APIを叩くのに使用される
 */
class LoginModel : ViewModel() {

    companion object {
        private const val QIITA_URL = "https://qiita.com/api/v2/oauth/authorize"
        private const val QIITA_CLIENT_ID = "2d2713c9fb8be9972a134670392dc4df46388034"
        private const val QIITA_SCOPE = "read_qiita"
        private const val STATE_LENGTH = 24

        enum class LoginStatus {
            NOT_LOGIN,
            NOT_CODE,
            NOT_ACCESS_TOKEN,
            NOT_AUTHENTICATED_USER,
            COMPLETE
        }

        private var state: String = ""


        fun isLoginCompleted(context: Context?): Boolean {
            getLoginStatus(context).let {
                if (it == LoginStatus.COMPLETE) return true
            }
            return false
        }

        private fun getLoginStatus(context: Context?): LoginStatus {
            if (context == null) return LoginStatus.NOT_LOGIN

            if (getQiitaCode(context) == null) {
                return LoginStatus.NOT_CODE
            } else if (getAccessToken(context) == null) {
                return LoginStatus.NOT_ACCESS_TOKEN
            } else if (getAuthenticatedUserId() == null) {
                return LoginStatus.NOT_AUTHENTICATED_USER
            }
            return LoginStatus.COMPLETE
        }

        fun clearAllLoginInfo(context: Context?) {
            clearQiitaCode(context)
            clearAccessToken(context)
            clearAuthenticatedUser()
        }

        fun clearAllUserSetting(context: Context?) {
            context ?: return

            SettingsUtils.clearAllPreference(context)
            MyRealmModel.resetRealm(context)
        }

        /**
         * 以下、Qiitaログイン関係
         */
        fun accessQiitaLoginPage(context: Context?) {
            if (context == null) {
                LOGE("NOT CONTEXT")
                return
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(createUrl()))
            context.startActivity(intent)
        }

        private fun createUrl(): String {
            val url = QIITA_URL + "?client_id=" + QIITA_CLIENT_ID +
                    "&scope=" + QIITA_SCOPE +
                    "&state=" + createState()
            LOGI("LOGIN URL = $url")

            return url
        }

        // Qiitaログイン後か（ACTION_VIEWのデータがあるか？）判定
        fun hasLoginParamInIntent(intent: Intent): Boolean {
            return intent.action == Intent.ACTION_VIEW
        }

        // Qiitaログインで取得するパラメータをハンドリング
        fun handleQiitaLoginParam(intent: Intent, context: Context) {
            LOGI("analyzeLoginIntent")

            intent.data?.also {
                it.getQueryParameter("state")?.also { paramState ->
                    // 送信したStateが返却されたStateと不一致の場合はログインできない
                    if (isStateNotSame(paramState)) {
                        LOGE("Sate not same!!")
                        return
                    }
                }
                it.getQueryParameter("code")?.also { paramCode ->
                    SettingsUtils.setQiitaCode(context, paramCode)
                }
            }
        }
        // Qiitaログイン関係ここまで

        /**
         * 以下、Stateデータ関係
         */
        private fun createState(): String {
            val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

            state = (1..STATE_LENGTH)
                .map { kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("")

            return state
        }

        private fun isStateNotSame(paramState: String): Boolean {
            if (state == paramState) return false
            return true
        }
        // Stateデータ関係ここまで


        /**
         * 以下、QiitaCodeデータ関係
         */
        fun getQiitaCode(context: Context?): String? {
            if (context == null) return null
            return SettingsUtils.getQiitaCode(context)
        }

        private fun clearQiitaCode(context: Context?) {
            if (context == null) return
            SettingsUtils.setQiitaCode(context, "")
        }
        // QiitaCodeデータ関係ここまで

        /**
         * 以下、AccessTokenデータ関係
         */
        fun setAccessToken(context: Context?, token: String) {
            if (context == null) return
            SettingsUtils.setAccessToken(context, token)
        }

        fun getAccessToken(context: Context?): String? {
            if (context == null) return null
            return SettingsUtils.getAccessToken(context)
        }

        fun clearAccessToken(context: Context?) {
            if (context == null) return
            SettingsUtils.setAccessToken(context, "")
        }
        // AccessTokenデータ関係ここまで


        /**
         * 以下、AuthenticatedUserデータ関係
         */
        fun hasAuthenticatedUser(): Boolean {
            val user = AuthenticatedUserDatabase.selectAuthenticatedUserData()
            if (user != null) {
                return true
            }

            return false
        }

        fun getAuthenticatedUserId(): String? {
            return AuthenticatedUserDatabase.selectAuthenticatedUserData()?.id
        }

        fun insertAuthenticatedUserData(data: AuthenticatedUserData) {
            AuthenticatedUserDatabase.insertAuthenticatedUserData(data)
        }

        fun clearAuthenticatedUser() {
            AuthenticatedUserDatabase.deleteAuthenticatedUserData()
        }
        // AuthenticatedUserデータ関係ここまで
    }
}