package jp.kirin3.anytimeqiita.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat


object DialogUtils {

    /**
     * ダイアログタイトルTextViewの取得
     */
    fun getDialogText(
        context: Context?,
        resources: Resources,
        title: String,
        colorId: Int
    ): TextView? {

        if (context == null) return null

        // タイトル部分のTextView
        val paddingLeftRight =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics)
                .toInt()
        val paddingTopBottom =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics)
                .toInt()
        val textView = TextView(context)
        // タイトルの背景色
        textView.setBackgroundColor(ContextCompat.getColor(context, colorId))
        // タイトルの文字色
        textView.setTextColor(Color.WHITE)
        textView.layoutParams =
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        textView.setPadding(paddingLeftRight, paddingTopBottom, paddingLeftRight, paddingTopBottom)
        // テキスト
        textView.text = title
        // テキストサイズ
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)

        return textView
    }
}