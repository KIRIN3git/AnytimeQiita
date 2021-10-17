package jp.kirin3.anytimeqiita.ui.stocks

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.stocks_low.view.*

class StocksRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    /**
     * 本来はここにリストクリック処理を追記するが今回はインターセプトのリスナーを使用(2)
     */
    interface ItemClickListener {
        fun onItemClick(id: String, title: String, url: String)
    }

    var seqTextView: TextView = view.seqTextView
    var titleTextView: TextView = view.titleTextView
    var iconCardView: CardView = view.iconCardView
    var iconImageView: ImageView = view.iconImageView
    var dateTextView: TextView = view.dateTextView
    var lgtmTextView: TextView = view.lgtmTextView
}