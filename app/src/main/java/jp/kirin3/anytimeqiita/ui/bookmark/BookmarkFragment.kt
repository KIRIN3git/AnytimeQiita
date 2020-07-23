package jp.kirin3.anytimeqiita.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.injection.Injection
import jp.kirin3.anytimeqiita.ui.reading.BookmarkViewModel
import kirin3.jp.mljanken.util.LogUtils.LOGI

class BookmarkFragment : Fragment(), BookmarkContract.View {

    private lateinit var bookmarkViewModel: BookmarkViewModel
    private lateinit var headText: TextView
    override lateinit var presenter: BookmarkContract.Presenter

    companion object {

        private val ARGUMENT_TASK_ID = "TASK_ID"

        private val REQUEST_EDIT_TASK = 1

        fun newInstance(taskId: String?) =
            BookmarkFragment().apply {
                arguments = Bundle().apply { putString(ARGUMENT_TASK_ID, taskId) }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LOGI("")
        bookmarkViewModel =
            ViewModelProviders.of(this).get(BookmarkViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_bookmark, container, false)
        headText = root.findViewById(R.id.fragment_bookmark_head_text)



//        val pd = ProgressDialog(context)
//        pd.setMessage("読み込み中")
//        pd.show()


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LOGI("")
    }

    override fun onResume() {
        super.onResume()
        LOGI("")

    }

    override fun showMessage(msg: String) {
        LOGI("")
        headText.setText(msg)
    }
}