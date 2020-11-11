package jp.kirin3.anytimeqiita.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import jp.kirin3.anytimeqiita.R
import kotlinx.android.synthetic.main.custom_dialog.*

class CustomDialogFragment : DialogFragment() {

    companion object {
        private const val NAME_PARAMETER = "parameter"

        fun newInstance(name: String): CustomDialogFragment {
            return CustomDialogFragment().also { fragment ->
                fragment.arguments = Bundle().also {
                    it.putString(NAME_PARAMETER, name)
                }
            }
        }
    }

    interface NoticeDialogListener {
        fun onDialogOkClick(dialog: DialogFragment)
    }

    internal var listener: NoticeDialogListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = when {
            parentFragment is NoticeDialogListener -> parentFragment as NoticeDialogListener
            context is NoticeDialogListener -> context
            else -> null
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).also { dialog ->
            dialog.setContentView(R.layout.custom_dialog)
            // NAMEセット
            arguments?.getString(NAME_PARAMETER)?.let {
                dialog.app_name.setText(it)
            }
            // DialogSetto
            dialog.app_name.setOnClickListener() {
                listener?.onDialogOkClick(this)
                dismissAllowingStateLoss()
            }
        }
    }
}

// Parcelizeでの受け渡し可能
