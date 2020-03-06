package jp.kirin3.anytimeqiita.Helper

import android.content.Context
import android.content.Intent
import jp.kirin3.anytimeqiita.ui.reading.LoginModel
import kirin3.jp.mljanken.util.SettingsUtils

object LoginHelper {

    val LOGIN_MODEL: LoginModel = LoginModel()

    fun requestLogin(context: Context?) {
        LOGIN_MODEL.requestLoginIntent(context)
    }

    fun processAfterLogin(context: Context?, intent: Intent) {

        if (context == null) return

        LOGIN_MODEL.processLogin(intent)?.let {
            SettingsUtils.setUserCode(context, it)
        }

        SettingsUtils.getUserCode(context)
    }
}