package jp.kirin3.anytimeqiita.ui.folder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.ui.reading.FolderViewModel
import kirin3.jp.mljanken.util.LogUtils.LOGI

class FolderFragment : Fragment(), FolderContract.View {

    private lateinit var folderViewModel: FolderViewModel
    private lateinit var headText: TextView
    override lateinit var presenter: FolderContract.Presenter

    companion object {

        private val ARGUMENT_TASK_ID = "TASK_ID"

        private val REQUEST_EDIT_TASK = 1

        fun newInstance(taskId: String?) =
            FolderFragment().apply {
                arguments = Bundle().apply { putString(ARGUMENT_TASK_ID, taskId) }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LOGI("")
        folderViewModel =
            ViewModelProviders.of(this).get(FolderViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_folder, container, false)
        headText = root.findViewById(R.id.fragment_folder_head_text)



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