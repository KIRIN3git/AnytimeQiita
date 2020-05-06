package jp.kirin3.anytimeqiita.ui.stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import jp.kirin3.anytimeqiita.R

class StockFragment : Fragment() {

    private lateinit var notificationsViewModel: StockViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(StockViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_stock, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        notificationsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}