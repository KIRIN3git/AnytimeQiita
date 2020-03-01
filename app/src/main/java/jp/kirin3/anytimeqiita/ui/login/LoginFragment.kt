package jp.kirin3.anytimeqiita.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import jp.kirin3.anytimeqiita.R

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel =
            ViewModelProviders.of(this).get(LoginViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_login, container, false)


        val loginWebView:WebView = root.findViewById(R.id.fragment_login_webview)
        //loginWebView.loadUrl(loginViewModel.createUrl())
        loginWebView.loadUrl("https://qiita.com/api/v2/oauth/authorize?client_id=2d2713c9fb8be9972a134670392dc4df46388034&scope=read_qiita");



        return root
    }
}