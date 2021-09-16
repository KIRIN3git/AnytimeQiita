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
import jp.kirin3.anytimeqiita.util.ReadingFileHelper
import kirin3.jp.mljanken.util.LogUtils.LOGI
import kirin3.jp.mljanken.util.SharedPreferencesUtils

class ReadingFragment : BaseFragment(), ReadingContract.View {

    private lateinit var viewModel: ReadingViewModel
    private lateinit var webView: WebView
    private lateinit var fragmentReadingProgress: ProgressBar
    private var isRefresh: Boolean = false

    override lateinit var presenter: ReadingContract.Presenter

    var isLoadNewWebView = false

    companion object {
        const val TITLE_PARAM = "TITLE"
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

        setTitle(getString(R.string.title_reading))

        viewModel = ViewModelProviders.of(this).get(ReadingViewModel::class.java)

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
            getString(URL_PARAM)?.let {
                SharedPreferencesUtils.setWebViewUrl(context, it)
                isLoadNewWebView = true
            }
            getBoolean(IS_REFRESH_WEBVIEW_PARAM)?.let {
                isRefresh = it
                if (isRefresh) {
                    SharedPreferencesUtils.setWebViewPosition(context, 0)
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
        SharedPreferencesUtils.setWebViewPosition(context, webView.scrollY)
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
            webView.loadUrl(SharedPreferencesUtils.getWebViewUrl(context))
        } else {
            webView.loadUrl(READER_FILE_PREFIX + ReadingFileHelper.getReadingFileFullPath(context))
        }

        setTitle(SharedPreferencesUtils.getWebViewTitle(context))
        webView.loadUrl(SharedPreferencesUtils.getWebViewUrl(context))
        webView.scrollY = SharedPreferencesUtils.getWebViewPosition(context)
    }
}