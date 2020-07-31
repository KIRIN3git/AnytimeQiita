package jp.kirin3.anytimeqiita.ui.reading

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReadingViewModel : ViewModel() {

    companion object{
        var webViewPosition = 0


    }
    private val _text = MutableLiveData<String>().apply {
        value = "This is reading Fragment"
    }
    val text: LiveData<String> = _text
}