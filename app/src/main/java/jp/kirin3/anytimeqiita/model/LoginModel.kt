package jp.kirin3.anytimeqiita.ui.reading

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kirin3.jp.mljanken.util.LogUtils.LOGD
import kirin3.jp.mljanken.util.LogUtils.LOGE
import kirin3.jp.mljanken.util.LogUtils.LOGI

class LoginModel : ViewModel() {

    companion object {
        private val QIITA_URL = "https://qiita.com/api/v2/oauth/authorize"
        private val QIITA_CLIENT_ID = "2d2713c9fb8be9972a134670392dc4df46388034"
        private val QIITA_SCOPE = "read_qiita"

        private val STATE_LENGTH = 24

    }

    private var state: String = ""
    private var paramState: String = ""
    var paramCode: String = ""

    fun startActionViewIntent(context: Context?) {
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

    fun checkStateSame(): Boolean {
        if (state.isEmpty() || paramState.isEmpty()) return false
        return true
    }

    @Nullable
    fun analyzeLoginIntent(intent: Intent): Boolean {

        LOGI("analyzeLoginIntent")

        val appLinkAction = intent.action

        if (appLinkAction != Intent.ACTION_VIEW) {
            return false
        }
        val appLinkData = intent.data ?: run {
            return false
        }
        paramCode = appLinkData.getQueryParameter("code") ?: run {
            return false
        }

        paramState = appLinkData.getQueryParameter("state") ?: run {
            return false
        }

        return true
    }


    // LIVE DATA
    private val _text = MutableLiveData<String>().apply {
        value = "This is setting Fragment"
    }
    val text: LiveData<String> = _text
}