package jp.kirin3.anytimeqiita.ui.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import jp.kirin3.anytimeqiita.R
import kirin3.jp.mljanken.util.LogUtils.LOGD

class RecordFragment : Fragment() {

    private lateinit var recordViewModel: RecordViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LOGD("")
        recordViewModel =
            ViewModelProviders.of(this).get(RecordViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_record, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        recordViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}