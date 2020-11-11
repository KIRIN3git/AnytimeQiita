package jp.kirin3.anytimeqiita.ui.reading

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import jp.kirin3.anytimeqiita.R
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.LogUtils.LOGI
import kirin3.jp.mljanken.util.SettingsUtils
import kotlinx.android.synthetic.main.fragment_reading.*

class ReadingFragment : Fragment() {

    private lateinit var loginModel: LoginModel
    private lateinit var readingWebView: WebView
    private var refreshFlg:Boolean = false

    companion object {
        val URL_PARAM = "URL"
        val REFRESH_FLG_PARAM_FLG = "REFRESH_FLG"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LogUtils.LOGI("")
        loginModel =
            ViewModelProviders.of(this).get(LoginModel::class.java)
        val root = inflater.inflate(R.layout.fragment_reading, container, false)

        getParam()

        readingWebView = root.findViewById(R.id.fragment_reading_webview)
        setWebView()

        return root
    }

    fun getParam(){
        arguments?.run {
            getString(URL_PARAM)?.let {
                SettingsUtils.setWebViewUrl(context, it)
            }
            getBoolean(REFRESH_FLG_PARAM_FLG)?.let {
                refreshFlg = it
                if( it == true){
                    SettingsUtils.setWebViewPosition(context,0)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        LOGI("")
    }

    override fun onDestroy() {
        super.onDestroy()
        //ReadingViewModel.webViewPosition = readingWebView.getScrollY()
        SettingsUtils.setWebViewPosition(context, readingWebView.getScrollY())
    }

    fun setWebView() {
        readingWebView.webViewClient = object: WebViewClient() {

            // ローディング開始時に呼ばれる
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // ProgressBarの表示
                fragment_reading_progress.visibility = View.VISIBLE
            }

            // ローディング終了時に呼ばれる
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // ProgressBarの非表示
                fragment_reading_progress.visibility = View.GONE
            }
        }

        readingWebView.loadUrl(SettingsUtils.getWebViewUrl(context))
        readingWebView.setScrollY(SettingsUtils.getWebViewPosition(context))
    }
}