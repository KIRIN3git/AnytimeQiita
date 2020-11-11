package jp.kirin3.anytimeqiita.ui.stocks

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.data.StocksResponseData
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
            it.titleTextView.text = stocksList.get(position).title
            it.lgtmTextView.text = stocksList.get(position).likes_count.toString()

            it.dateTextView.text =
                TimeUtils.formatShortDate(context, stocksList.get(position).updated_at)

            Picasso.get().load(stocksList.get(position).user!!.profile_image_url)
                .into(it.iconImageView);

//            it.itemTextView.text = stocksList.get(position)
//            it.itemImageView.setImageResource(R.mipmap.ic_launcher)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StocksRecyclerViewHolder {

        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.stocks_low, parent, false)

        view.setOnClickListener { view ->
            recyclerView?.let {
                itemClickListener.onItemClick(stocksList[it.getChildAdapterPosition(view)].id, stocksList[it.getChildAdapterPosition(view)].url)
            }
        }

        return StocksRecyclerViewHolder(view)
    }

    // recyclerViewのコンテンツのサイズ
    override fun getItemCount(): Int {
        return stocksList.size
    }


}