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
import jp.kirin3.anytimeqiita.util.ReadingFileHelper
import kirin3.jp.mljanken.util.LogUtils.LOGI
import kirin3.jp.mljanken.util.SettingsUtils

class ReadingFragment : Fragment(), ReadingContract.View {

    private lateinit var viewModel: ReadingViewModel
    private lateinit var webView: WebView
    private lateinit var fragmentReadingProgress: ProgressBar
    private var isRefresh: Boolean = false

    override lateinit var presenter: ReadingContract.Presenter

    var isLoadNewWebView = false

    companion object {
        const val URL_PARAM = "URL"
        const val IS_REFRESH_WEBVIEW_PARAM = "IS_REFRESH"
        const val READER_FILE_PREFIX = "file://"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LOGI("")

        viewModel =
            ViewModelProviders.of(this).get(ReadingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_reading, container, false)

        webView = root.findViewById(R.id.fragment_reading_webview)
        fragmentReadingProgress = root.findViewById(R.id.fragment_reading_progress)

        presenter = ReadingPresenter(
            Injection.provideGraphRepository(),
            this
        )

        getParam()
        setWebView()

        presenter.setup(viewModel)

        // DEMO DATA
        //presenter.setRandomDemoReadingTime()

        return root
    }

    fun getParam() {
        arguments?.run {
            getString(URL_PARAM)?.let {
                SettingsUtils.setWebViewUrl(context, it)
                isLoadNewWebView = true
            }
            getBoolean(IS_REFRESH_WEBVIEW_PARAM)?.let {
                isRefresh = it
                if (isRefresh) {
                    SettingsUtils.setWebViewPosition(context, 0)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        LOGI("")
        presenter.setStartTime()
    }

    override fun onPause() {
        super.onPause()
        LOGI("")
        presenter.setEndTime()
        presenter.setReadingTime()
        SettingsUtils.setWebViewPosition(context, webView.scrollY)
    }

    fun setWebView() {
        webView.webViewClient = object : WebViewClient() {
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
                webView.saveWebArchive(ReadingFileHelper.getReadingFileFullPath(context))
            }
        }

        val hasFile = ReadingFileHelper.hasReadingFile(context)
        if (!hasFile && !isLoadNewWebView) return

        if (isLoadNewWebView) {
            webView.loadUrl(SettingsUtils.getWebViewUrl(context))
        } else {
            webView.loadUrl(READER_FILE_PREFIX + ReadingFileHelper.getReadingFileFullPath(context))
        }
        webView.scrollY = SettingsUtils.getWebViewPosition(context)

    }
}