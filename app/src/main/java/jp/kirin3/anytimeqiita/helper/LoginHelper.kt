package jp.kirin3.anytimeqiita.helper

import android.content.Context
import android.content.Intent
import jp.kirin3.anytimeqiita.ui.reading.LoginModel
import kirin3.jp.mljanken.util.LogUtils.LOGI
import kirin3.jp.mljanken.util.SettingsUtils

object LoginHelper {

    val loginModel: LoginModel = LoginModel()

    fun accessLoginPage(context: Context?) {
        loginModel.startActionViewIntent(context)
    }


    fun setLoginParamToPrefarence(context: Context?, intent: Intent): Boolean {
        if (context == null) return false

        if (loginModel.analyzeLoginIntent(intent) && loginModel.checkStateSame()) {
            LOGI("LOGIN SUCCESS!")
            SettingsUtils.setQiitaCode(context, loginModel.paramCode)
            return true
        } else {
            LOGI("LOGIN FAIL!")
            return false
        }
    }

    fun getQiitaCode(context: Context?): String {
        if (context == null) return ""

        val code = SettingsUtils.getQiitaCode(context) ?: run {
            return ""
        }
        return code
    }
}