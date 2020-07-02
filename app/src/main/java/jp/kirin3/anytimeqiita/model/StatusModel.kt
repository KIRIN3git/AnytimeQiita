package jp.kirin3.anytimeqiita.ui.reading

import android.content.Context
import androidx.lifecycle.ViewModel
import jp.kirin3.anytimeqiita.helper.AuthenticatedUserHelper
import jp.kirin3.anytimeqiita.util.StringUtils
import kirin3.jp.mljanken.util.SettingsUtils

class StatusModel : ViewModel() {

    enum class Status {
        NOT_LOGIN,
        NOT_CODE,
        NOT_ACCESS_TOKEN,
        NOT_AUTHENTICATED_USER,
        COMPLETE
    }

    companion object {
        fun getStatus(context: Context?): Status {
            if (context == null) return Status.NOT_LOGIN

            if (StringUtils.isEmpty(SettingsUtils.getQiitaCode(context))) {
                return Status.NOT_CODE
            }
            if (StringUtils.isEmpty(SettingsUtils.getQiitaAccessToken(context))) {
                return Status.NOT_ACCESS_TOKEN
            }
            if (AuthenticatedUserHelper.getAuthenticatedUserIdFromCashe() == null) {
                return Status.NOT_AUTHENTICATED_USER
            }
            return Status.COMPLETE
        }
    }
}
