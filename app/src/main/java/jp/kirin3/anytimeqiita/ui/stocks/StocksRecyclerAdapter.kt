package jp.kirin3.anytimeqiita.ui.stocks

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.data.StocksResponseData
import kirin3.jp.mljanken.util.LogUtils.LOGI
import kirin3.jp.mljanken.util.SharedPreferencesUtils
import kirin3.jp.mljanken.util.TimeUtils

class StocksRecyclerAdapter(
    private val context: Context,
    private val itemClickListener: StocksRecyclerViewHolder.ItemClickListener,
    private val stocksList: MutableList<StocksResponseData>
) : RecyclerView.Adapter<StocksRecyclerViewHolder>() {

    private var recyclerView: RecyclerView? = null

    fun addItem(addList: List<StocksResponseData>) {
        stocksList.addAll(addList)
        notifyDataSetChanged()
    }

    fun clearItem() {
        stocksList.clear()
        notifyDataSetChanged()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null

    }

    override fun onBindViewHolder(holder: StocksRecyclerViewHolder, position: Int) {
        holder?.let {

            val settingCheckBoxData = SharedPreferencesUtils.getSettingCheckBoxData(context)

            setIcon(
                it.iconCardView,
                it.iconImageView,
                stocksList[position].user?.profile_image_url,
                settingCheckBoxData?.setting_show_icon
            )
            setTitle(it.titleTextView, stocksList[position].title)

            setTitle(it.seqTextView, stocksList[position].sequence.toString())

            setLgtmAndDate(
                it.lgtmTextView,
                stocksList[position].likes_count.toString(),
                settingCheckBoxData?.setting_show_lgtm,
                it.dateTextView,
                TimeUtils.getStringYymmddFromDate(stocksList[position].updated_at),
                settingCheckBoxData?.setting_update_time
            )
        }
    }

    private fun setIcon(
        cardView: CardView,
        imageView: ImageView,
        imageUrl: String?,
        isVisible: Boolean?
    ) {
        if (isVisible == false) {
            cardView.isVisible = false
        } else {
            Picasso.get().load(imageUrl).into(imageView)
            imageView.isVisible = true
        }
    }

    private fun setTitle(textView: TextView, title: String) {
        textView.text = title
    }

    private fun setLgtmAndDate(
        lgtmTextView: TextView,
        lgtm: String,
        isLgtmVisible: Boolean?,
        dateTextView: TextView,
        date: String,
        isDateVisible: Boolean?
    ) {
        if (isLgtmVisible == false) {
            lgtmTextView.isVisible = false
        } else {
            lgtmTextView.text = lgtm
            lgtmTextView.isVisible = true
        }

        if (isDateVisible == false) {
            dateTextView.isVisible = false
        } else {
            dateTextView.text = date
            dateTextView.isVisible = true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StocksRecyclerViewHolder {

        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.stocks_low, parent, false)

        view.setOnClickListener { view ->
            recyclerView?.let {
                itemClickListener.onItemClick(
                    stocksList[it.getChildAdapterPosition(view)].id,
                    stocksList[it.getChildAdapterPosition(view)].title,
                    stocksList[it.getChildAdapterPosition(view)].url
                )
            }
        }

        return StocksRecyclerViewHolder(view)
    }

    // recyclerViewのコンテンツのサイズ
    override fun getItemCount(): Int {
        return stocksList.size
    }


}