package jp.kirin3.anytimeqiita.ui.reading

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.injection.Injection
import kirin3.jp.mljanken.util.LogUtils.LOGI
import kirin3.jp.mljanken.util.SettingsUtils

class ReadingFragment : Fragment(), ReadingContract.View {

    private lateinit var loginModel: LoginModel
    private lateinit var readingWebView: WebView
    private lateinit var fragmentReadingProgress: ProgressBar
    private var refreshFlg: Boolean = false
    private var startTime: Long = 0
    private var endTime: Long = 0

    override lateinit var presenter: ReadingContract.Presenter


    companion object {
        const val URL_PARAM = "URL"
        const val IS_REFRESH_WEBVIEW_PARAM = "IS_REFRESH"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LOGI("")
        loginModel =
            ViewModelProviders.of(this).get(LoginModel::class.java)
        val root = inflater.inflate(R.layout.fragment_reading, container, false)

        presenter = ReadingPresenter(
            Injection.provideGraphRepository(),
            this
        )

        getParam()

        readingWebView = root.findViewById(R.id.fragment_reading_webview)
        fragmentReadingProgress = root.findViewById(R.id.fragment_reading_progress)
        setWebView()

        // DEMO DATA
        presenter.setRandamDemoReadingTime()

        return root
    }

    fun getParam() {
        arguments?.run {
            getString(URL_PARAM)?.let {
                SettingsUtils.setWebViewUrl(context, it)
            }
            getBoolean(IS_REFRESH_WEBVIEW_PARAM)?.let {
                refreshFlg = it
                if (it) {
                    SettingsUtils.setWebViewPosition(context, 0)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        LOGI("")
        startTime = System.currentTimeMillis()
    }

    override fun onPause() {
        super.onPause()
        LOGI("")
        endTime = System.currentTimeMillis()
        val readingTime = getReadingTime()
        presenter.addReadingTimeToDb(readingTime)
    }

    private fun getReadingTime(): Int {
        if (startTime == 0L || endTime == 0L) return 0
        return ((endTime - startTime) / (60 * 1000)).toInt()
    }

    override fun onDestroy() {
        super.onDestroy()
        //ReadingViewModel.webViewPosition = readingWebView.getScrollY()
        SettingsUtils.setWebViewPosition(context, readingWebView.getScrollY())
    }

    fun setWebView() {
        readingWebView.webViewClient = object : WebViewClient() {

            // ローディング開始時に呼ばれる
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // ProgressBarの表示
                fragmentReadingProgress.visibility = View.VISIBLE
            }

            // ローディング終了時に呼ばれる
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // ProgressBarの非表示
                fragmentReadingProgress.visibility = View.GONE
            }
        }

        readingWebView.loadUrl(SettingsUtils.getWebViewUrl(context))
        readingWebView.setScrollY(SettingsUtils.getWebViewPosition(context))
    }
}