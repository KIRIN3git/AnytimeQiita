package jp.kirin3.anytimeqiita.model

import android.content.Context
import androidx.lifecycle.ViewModel
import jp.kirin3.anytimeqiita.api.ApiClient
import jp.kirin3.anytimeqiita.data.AccessTokenResponseData
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.LogUtils.LOGI
import kirin3.jp.mljanken.util.SettingsUtils

class AccessTokenModel : ViewModel() {
    interface LoadCallback {
        fun onSuccess()
        fun onFailure()
    }

    companion object {


        fun clearQiitaAccessToken(context: Context?) {
            if (context == null) return
            SettingsUtils.setQiitaAccessToken(context, "")
        }
    }
}

