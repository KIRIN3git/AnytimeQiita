package jp.kirin3.anytimeqiita.ui.stocks

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.stocks_low.view.*

class StocksRecyclerViewHolder(view:View): RecyclerView.ViewHolder(view){

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
    var textView = view.titleTextView
    var iconImageView = view.iconImageView
    var dateTextView = view.dateTextView
    var lgtmTextView = view.lgtmTextView
}