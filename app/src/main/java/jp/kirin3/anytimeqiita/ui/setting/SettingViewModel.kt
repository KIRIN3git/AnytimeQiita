package jp.kirin3.anytimeqiita.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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


    private fun checkState(checkState:String):Boolean{
        return checkState.equals(state)
    }


    private val _text = MutableLiveData<String>().apply {
        value = "This is setting Fragment"
    }
    val text: LiveData<String> = _text
}