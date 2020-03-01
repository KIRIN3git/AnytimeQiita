package jp.kirin3.anytimeqiita.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.concurrent.ThreadLocalRandom

class SettingViewModel : ViewModel() {

    companion object{
        private val QIITA_URL = "https://qiita.com/api/v2/oauth/authorize"
        private val QIITA_PARAM_CRIENT_ID = "client_id"
        private val QIITA_PARAM_SCOPE = "scope"
        private val QIITA_PARAM_STATE = "state"
        private val QIITA_CLIENT_ID = "2d2713c9fb8be9972a134670392dc4df46388034"
        private val QIITA_SCOPE = "read_qiita"

        private val STATE_LENGTH = 24
        private var state:String = ""
    }


    public fun createUrl():String {
        return QIITA_URL + "?" + QIITA_PARAM_CRIENT_ID + "=" + QIITA_CLIENT_ID +
                "&" + QIITA_PARAM_SCOPE + "=" + QIITA_SCOPE +
                "&" + QIITA_PARAM_STATE + "=" + createState()
    }

    private fun createState():String {
        val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        state =  (1..STATE_LENGTH)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")

        return state
    }

    private fun checkState(checkState:String):Boolean{
        return checkState.equals(state)
    }


    private val _text = MutableLiveData<String>().apply {
        value = "This is setting Fragment"
    }
    val text: LiveData<String> = _text
}