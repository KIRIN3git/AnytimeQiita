package jp.kirin3.anytimeqiita.ui.reading

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kirin3.jp.mljanken.util.LogUtils.LOGD

class FolderViewModel : ViewModel() {

    companion object {
        private val QIITA_URL = "https://qiita.com/api/v2/oauth/authorize"
        private val QIITA_CLIENT_ID = "2d2713c9fb8be9972a134670392dc4df46388034"
        private val QIITA_SCOPE = "read_qiita"

        private val STATE_LENGTH = 24
        private var state: String = ""
    }


    private val _text = MutableLiveData<String>().apply {
        value = "This is folder Fragment"
    }
    val text: LiveData<String> = _text
}