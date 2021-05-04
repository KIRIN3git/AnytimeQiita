package jp.kirin3.anytimeqiita.manager

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import jp.kirin3.anytimeqiita.R
import jp.kirin3.anytimeqiita.ui.reading.ReadingFragment

object TransitionManager {

    fun transitionReadingFragment(
        fragment: Fragment,
        packageManager: PackageManager?,
        title: String,
        url: String,
        isRefresh: Boolean
    ) {
        packageManager ?: return

        val params = bundleOf(
            ReadingFragment.TITLE_PARAM to title,
            ReadingFragment.URL_PARAM to url,
            ReadingFragment.IS_REFRESH_WEBVIEW_PARAM to isRefresh
        )
        fragment.findNavController().navigate(R.id.bottom_navigation_reading, params)
    }

    fun transitionExternalBrowser(
        fragment: Fragment,
        packageManager: PackageManager?,
        url: String
    ) {
        packageManager ?: return
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            fragment.activity?.startActivity(intent)
        }

    }
}