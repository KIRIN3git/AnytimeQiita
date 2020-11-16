package jp.kirin3.anytimeqiita.ui.reading

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.kirin3.anytimeqiita.model.AccessTokenModel
import jp.kirin3.anytimeqiita.model.AuthenticatedUserModel
import jp.kirin3.anytimeqiita.util.StringUtils
import kirin3.jp.mljanken.util.LogUtils.LOGE
import kirin3.jp.mljanken.util.LogUtils.LOGI
import kirin3.jp.mljanken.util.SettingsUtils

/**
 * ログインモデル
 *
 * QiitaCode：Qiitaログイン後パラメータとしてアプリに通知される。その後、Preferenceに保存。
 * AccessToken：QiitaCodeを使用してQiitaAPIにて取得。その後、Preferenceに保存。
 * AuthenticatedUser：AccessTokenを使用してQiitaAPIにて取得されるログインユーザーのIDが保存されているデータ。
 * 　　　　　　　　　　 その後、キャッシュとDBに保存。
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

        fun getLoginStatus(context: Context?): LoginStatus {
            if (context == null) return LoginStatus.NOT_LOGIN

            if (StringUtils.isEmpty(SettingsUtils.getQiitaCode(context))) {
                return LoginStatus.NOT_CODE
            }
            if (StringUtils.isEmpty(SettingsUtils.getQiitaAccessToken(context))) {
                return LoginStatus.NOT_ACCESS_TOKEN
            }
            if (AuthenticatedUserModel.getAuthenticatedUserIdFromCacheOrDb() == null) {
                return LoginStatus.NOT_AUTHENTICATED_USER
            }
            return LoginStatus.COMPLETE
        }

        fun accessQiitaLoginPage(context: Context?) {
            if (context == null) {
                LOGE("NOT CONTEXT")
                return
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(createUrl()))
            context.startActivity(intent)
        }

        fun clearLoginCode(context: Context?) {
            if (context == null) return
            SettingsUtils.setQiitaCode(context, "")
        }

        fun clearAllLoginInfo(context: Context?) {
            clearLoginCode(context)
            AccessTokenModel.clearQiitaAccessToken(context)
            AuthenticatedUserModel.clearAuthenticatedUserFromDbAndCache()
        }

        private fun createUrl(): String {
            val url = QIITA_URL + "?client_id=" + QIITA_CLIENT_ID +
                    "&scope=" + QIITA_SCOPE +
                    "&state=" + createState()
            LOGI("LOGIN URL = " + url)

            return url
        }

        private fun createState(): String {
            val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

            state = (1..STATE_LENGTH)
                .map { kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("")

            return state
        }

        fun isStateNotSame(paramState: String): Boolean {
            if (state.equals(paramState)) return false
            return true
        }

        fun isLoginCompleted(context: Context?): Boolean {
            getLoginStatus(context).let {
                if (it == LoginStatus.COMPLETE) return true
            }
            return false
        }

        /**
         * QiitaLogin後か判定
         */
        fun hasLoginParamInPreference(intent: Intent): Boolean {
            return intent.action == Intent.ACTION_VIEW
        }

        /**
         * QiitaLoginで取得するパラメータをセット
         */
        fun setQiitaLoginCode(intent: Intent, context: Context) {
            LOGI("analyzeLoginIntent")

            intent.data?.also {
                it.getQueryParameter("state")?.also { paramState ->
                    if (isStateNotSame(paramState)) {
                        return
                    }
                }
                it.getQueryParameter("code")?.also { paramCode ->
                    SettingsUtils.setQiitaCode(context, paramCode)
                }
            }
        }

        // LIVE DATA
        private val _text = MutableLiveData<String>().apply {
            value = "This is setting Fragment"
        }
        val text: LiveData<String> = _text
    }
}