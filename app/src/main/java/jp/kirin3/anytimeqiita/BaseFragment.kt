package jp.kirin3.anytimeqiita

import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    fun setTitle(title: String?) {
        activity?.title = title
    }
}