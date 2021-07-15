package jp.kirin3.anytimeqiita.ui.sample.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.util.extensions.inflate
import kotlinx.android.synthetic.main.sample_date_cell_item.view.*
import kotlinx.android.synthetic.main.sample_line_cell_item.view.*
import kotlinx.android.synthetic.main.sample_title_cell_item.view.*
import javax.inject.Inject

/**
@Inject
internal lateinit var adapter: SampleAdapter
でフラグメントにてインジェクトする

データを作成してaddSettingsで受け渡し
ViewModelでmutableListOf<SampleListDto>を作成しておく
private val sampleList = mutableListOf<SampleListDto>()
val samples:List<SampleListDto>
get() {
return sampleList
}

またFragmentでクリックリスナーを登録する必要がある
private fun getClickChangeListener(): XXX.OnClickedListener {
return XXX.OnClickedListener{
xxxx
}
}
private fun setupAdapterCallback() {
adapter.onCheckedChangeListener = getCheckedChangeListener()
}
 */

class SampleAdapter @Inject constructor() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val sampleList: MutableList<SampleListDto> = mutableListOf()

    private lateinit var onClickListener: View.OnClickListener

    companion object {
        private const val EMPTY_TYPE = -1
        private const val TITLE_TYPE = 1
        private const val DATA_TYPE = 2
        private const val LINE_TYPE = 3
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return when (viewType) {
            TITLE_TYPE -> TitleViewHolder(parent.inflate(R.layout.sample_title_cell_item))
            DATA_TYPE -> TitleViewHolder(parent.inflate(R.layout.sample_date_cell_item))
            LINE_TYPE -> TitleViewHolder(parent.inflate(R.layout.sample_line_cell_item))
            else -> EmptyViewHolder(parent.inflate(R.layout.empty))
        }
    }

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (val sample = sampleList[position]) {
            is SampleListTitleDto -> (viewHolder as TitleViewHolder).bind(sample)
            is SampleListDateDto -> (viewHolder as DateViewHolder).bind(
                sample, onClickListener
            )
            is SampleListLineDto -> (viewHolder as LineViewHolder).bind(sample)
            else -> return
        }
    }

    override fun getItemCount(): Int {
        return sampleList.count()
    }

    override fun getItemViewType(position: Int): Int {
        return when (sampleList[position]) {
            is SampleListTitleDto -> TITLE_TYPE
            is SampleListDateDto -> DATA_TYPE
            is SampleListLineDto -> LINE_TYPE
            else -> EMPTY_TYPE
        }
    }


    fun addSettings(settingDtoList: List<SampleListDto>) {
        sampleList.clear()
        sampleList.addAll(settingDtoList)
        notifyDataSetChanged()
    }

    class TitleViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bind(sampleListTitleDto: SampleListTitleDto) {
            itemView.sample_title_text_view.setText(sampleListTitleDto.title)
        }
    }

    class DateViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bind(
            sampleListDateDto: SampleListDateDto,
            onClickListener: View.OnClickListener
        ) {
            itemView.sample_date_text_view.setText(sampleListDateDto.xxx)
            itemView.sample_date_text_view.setOnClickListener(onClickListener)
        }
    }

    class LineViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bind(sampleListLineDto: SampleListLineDto) {
            itemView.sample_line_text_view.setText(sampleListLineDto.yyy)
        }
    }

    class EmptyViewHolder(item: View) : RecyclerView.ViewHolder(item)
}