package jp.kirin3.anytimeqiita.helper

import android.content.Context
import android.content.Intent
import jp.kirin3.anytimeqiita.ui.reading.LoginModel
import jp.kirin3.anytimeqiita.util.StringUtils
import kirin3.jp.mljanken.util.LogUtils.LOGI
import kirin3.jp.mljanken.util.SettingsUtils
import java.util.concurrent.CountDownLatch

object LoginHelper {

    val loginModel: LoginModel = LoginModel()

    fun accessLoginPage(context: Context?) {
        loginModel.startActionViewIntent(context)
    }

    /**
     * ログインパラメータの有無判断
     * Stateチェックまでは行わない
      */
    fun hasLoginParamToPrefarence(intent: Intent): Boolean {

        if (loginModel.analyzeLoginIntent(intent)){
            return true
        } else{
            return false
        }
    }

    fun setLoginCodeToPrefarence(context: Context?, intent: Intent): Boolean {
        if (context == null) return false

        SettingsUtils.setQiitaCode(context, "")

        if (loginModel.analyzeLoginIntent(intent) && loginModel.checkStateSame()) {
            LOGI("LOGIN SUCCESS!")
            SettingsUtils.setQiitaCode(context, loginModel.paramCode)
            return true
        } else {
            LOGI("LOGIN FAIL!")
            return false
        }
    }

    /**
     * ログイン時全取得処理
     */
    fun processAfterLogin(intent: Intent,context: Context?): Boolean {

        if(context == null){
            return false
        }

        var latch = CountDownLatch(1);
        if (!LoginHelper.setLoginCodeToPrefarence(context, intent)){
            return false
        }

        AccessTokenHelper.loadAccessToken(context, SettingsUtils.getQiitaCode(context), latch)
        latch.await()
        if (StringUtils.isEmpty(SettingsUtils.getQiitaAccessToken(context))) {
            return false
        }

        latch = CountDownLatch(1)
        AuthenticatedUserHelper.loadAuthenticatedUser(
            context,
            SettingsUtils.getQiitaAccessToken(context),
            latch
        )
        latch.await()

        if (AuthenticatedUserHelper.hasAuthenticatedUser()) {
            return true
        } else{
            return false
        }
    }
}