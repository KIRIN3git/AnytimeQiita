package jp.kirin3.anytimeqiita.ui.reading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import jp.kirin3.anytimeqiita.R

class BookmarkFragment : Fragment() {

    private lateinit var bookmarkViewModel: BookmarkViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bookmarkViewModel =
            ViewModelProviders.of(this).get(BookmarkViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_bookmark, container, false)

        return root
    }
}