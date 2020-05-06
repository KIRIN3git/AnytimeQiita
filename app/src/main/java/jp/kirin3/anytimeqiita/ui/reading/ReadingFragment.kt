package jp.kirin3.anytimeqiita.ui.reading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import jp.kirin3.anytimeqiita.R
import kirin3.jp.mljanken.util.LogUtils

class ReadingFragment : Fragment() {

    private lateinit var loginModel: LoginModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LogUtils.LOGI("")
        loginModel =
            ViewModelProviders.of(this).get(LoginModel::class.java)
        val root = inflater.inflate(R.layout.fragment_reading, container, false)
        val textView: TextView = root.findViewById(R.id.text_reading)
        loginModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }

    override fun onResume() {
        super.onResume()
        LogUtils.LOGI("")
    }

}