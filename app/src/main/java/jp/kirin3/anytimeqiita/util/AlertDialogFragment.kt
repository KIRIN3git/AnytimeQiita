package jp.kirin3.anytimeqiita.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import jp.kirin3.anytimeqiita.R
import kotlinx.android.parcel.Parcelize

class AlertDialogFragment : DialogFragment() {

    companion object {
        private const val PARAMETER = "parameter"

        fun newInstance(parameter: AlertDialogParameter): AlertDialogFragment {
            return AlertDialogFragment().also { fragment ->
                fragment.arguments = Bundle().also { bundle ->
                    bundle.putParcelable(PARAMETER, parameter)
                }
            }
        }
    }

    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
        fun onDialogNeutralClick(dialog: DialogFragment)
        fun onDialogListClick(dialog: DialogFragment, id: Int)
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
        val nonNullContext: Context =
            context ?: throw java.lang.IllegalStateException("AlertDialog context cannot be null")
        val parameter = arguments?.getParcelable<AlertDialogParameter>(PARAMETER)
            ?: throw java.lang.IllegalStateException("AlertDialog parameter cannnot be null")

        return AlertDialog.Builder(nonNullContext).also { builder ->
            parameter.iconId?.let {
                builder.setIcon(it)
            }
            parameter.titleBackgroundColor?.let { color ->
                parameter.title?.let { title ->
                    val customText = DialogUtils.getDialogText(
                        nonNullContext,
                        resources,
                        getString(title),
                        color
                    )
                    builder.setCustomTitle(customText)
                }
            } ?: let {
                parameter.title?.let {
                    builder.setTitle(it)
                }
            }
            parameter.message?.let {
                builder.setMessage(it)
            }
            parameter.positiveButtonText?.let {
                builder.setPositiveButton(it) { _, _ ->
                    listener?.onDialogPositiveClick(this)
                    dismissAllowingStateLoss()
                }
            }
            parameter.negativeButtonText?.let {
                builder.setNegativeButton(it) { _, _ ->
                    listener?.onDialogNegativeClick(this)
                    dismissAllowingStateLoss()
                }
            }
            parameter.neutralButtonText?.let {
                builder.setNeutralButton(it) { _, _ ->
                    listener?.onDialogNeutralClick(this)
                    dismissAllowingStateLoss()
                }
            }
            parameter.cancelable?.let {
                isCancelable = it
            }
            parameter.list?.let {
                builder.setItems(it.toTypedArray()) { dialog, which ->
                    listener?.onDialogListClick(this, which)
                    dismissAllowingStateLoss()
                }
            }

        }.create().also { alertDialog ->
            val positiveButtonTextColor = parameter.positiveButtonTextColor
            val negativeButtonTextColor = parameter.negativeButtonTextColor
            if (positiveButtonTextColor == null && negativeButtonTextColor == null) {
                return alertDialog
            }

            alertDialog.setOnShowListener {
                positiveButtonTextColor?.let {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        ?.setTextColor(ContextCompat.getColor(nonNullContext, it))
                }
                negativeButtonTextColor?.let {
                    alertDialog.getButton(
                        AlertDialog.BUTTON_NEGATIVE
                    )?.setTextColor(ContextCompat.getColor(nonNullContext, it))
                }
            }
        }
    }
}


/**
 * AlertDialogFragmentの引数
 *
 * message設定時にlistは設定できない
 */
@Parcelize
data class AlertDialogParameter(
    val iconId: Int? = null,
    val title: Int? = null,
    val titleBackgroundColor: Int? = null,
    val message: String? = null,
    val positiveButtonText: Int? = R.string.ok,
    val positiveButtonTextColor: Int? = null,
    val negativeButtonText: Int? = null,
    val negativeButtonTextColor: Int? = null,
    val neutralButtonText: Int? = null,
    val neutralButtonTextColor: Int? = null,
    val cancelable: Boolean? = null,
    val list: List<String>? = null
) : Parcelable
