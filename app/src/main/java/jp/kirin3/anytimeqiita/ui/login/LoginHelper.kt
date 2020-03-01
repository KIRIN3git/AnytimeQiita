package jp.kirin3.anytimeqiita.ui.login

import android.content.Context
import android.content.Intent
import jp.kirin3.anytimeqiita.ui.home.LoginViewModel
import kirin3.jp.mljanken.util.SettingsUtils

object LoginHelper {

    val loginModel: LoginViewModel = LoginViewModel()

    fun requestLogin(context: Context?) {
        loginModel.requestLoginIntent(context)
    }

    fun processLogin(context: Context?, intent: Intent) {

        if (context == null) return

        loginModel.processLogin(intent)?.let {
            SettingsUtils.setUserCode(context, it)
        }

        SettingsUtils.getUserCode(context)
    }
}