package jp.kirin3.anytimeqiita.ui.reading

import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProviders
import jp.kirin3.anytimeqiita.BaseFragment
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.injection.Injection
import kirin3.jp.mljanken.util.LogUtils.LOGI
import kirin3.jp.mljanken.util.SettingsUtils

class ReadingFragment : BaseFragment(), ReadingContract.View {

    private lateinit var loginModel: LoginModel
    private lateinit var webView: WebView
    private lateinit var fragmentReadingProgress: ProgressBar
    private var refreshFlg: Boolean = false
    private var startTime: Long = 0
    private var endTime: Long = 0

    override lateinit var presenter: ReadingContract.Presenter

    companion object {
        const val TITLE_PARAM = "TITLE"
        const val URL_PARAM = "URL"
        const val IS_REFRESH_WEBVIEW_PARAM = "IS_REFRESH"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LOGI("")

        setTitle(getString(R.string.title_reading))

        loginModel =
            ViewModelProviders.of(this).get(LoginModel::class.java)
        val root = inflater.inflate(R.layout.fragment_reading, container, false)

        presenter = ReadingPresenter(
            Injection.provideGraphRepository(),
            this
        )

        getParam()

        webView = root.findViewById(R.id.fragment_reading_webview)
        fragmentReadingProgress = root.findViewById(R.id.fragment_reading_progress)
        setWebView()

        // DEMO DATA
        presenter.setRandamDemoReadingTime()

        return root
    }

    // メニュー設定関数 ここから
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.reading_menu, menu)
        menu.findItem(R.id.menu_go_to_top).isVisible = true
        menu.findItem(R.id.menu_reload).isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_reload -> {
                webView.reload()
                webView.scrollY = 0
            }
            R.id.menu_go_to_top -> webView.scrollY = 0
        }
        return true
    }
    // メニュー設定関数 ここまで

    fun getParam() {
        arguments?.run {
            getString(TITLE_PARAM)?.let {
                SettingsUtils.setWebViewTitle(context, it)
            }
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
            }
        }

        setTitle(SettingsUtils.getWebViewTitle(context))
        webView.loadUrl(SettingsUtils.getWebViewUrl(context))
        webView.scrollY = SettingsUtils.getWebViewPosition(context)
    }
}