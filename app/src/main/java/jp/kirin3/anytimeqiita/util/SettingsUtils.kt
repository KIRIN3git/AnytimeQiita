package kirin3.jp.mljanken.util

import android.content.Context
import androidx.preference.PreferenceManager
import jp.kirin3.anytimeqiita.ui.stocks.FoldersRepository
import kirin3.jp.mljanken.util.LogUtils.LOGD

object SettingsUtils {

    private const val TAG = "TAG"

    private const val PREF_SETTING_CODE = "pref_setting_code"
    private const val PREF_SETTING_TOKEN = "pref_setting_token"
    private const val PREF_SETTING_WEBVIEW_POSITION = "pref_setting_webview_position"
    private const val PREF_SETTING_WEBVIEW_URL = "pref_setting_webview_url"
    private const val PREF_CREATE_FIRST_FOLDERS_FLG = "pref_create_first_folder_flg"
    private const val PREF_CREATE_FOLDERS_SEQID = "pref_create_folders_seqid"
    private const val PREF_STOCK_PAGE_COUNT = "pref_stock_page_count"
    private const val PREF_STOCK_LOADING_COMPLETED = "pref_stock_loading_completed"

    fun setQiitaCode(context: Context, code: String) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putString(PREF_SETTING_CODE, code).apply()
        LOGD("SetPref code = " + code)
    }

    fun getQiitaCode(context: Context): String? {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD("GetPref code = " + sp.getString(PREF_SETTING_CODE, ""))
        return sp.getString(PREF_SETTING_CODE, null)
    }

    fun setQiitaAccessToken(context: Context, token: String) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putString(PREF_SETTING_TOKEN, token).apply()
        LOGD("SetPref token = " + token)
    }

    fun getQiitaAccessToken(context: Context): String? {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD("GetPref token = " + sp.getString(PREF_SETTING_TOKEN, ""))
        return sp.getString(PREF_SETTING_TOKEN, "")
    }

    fun setWebViewPosition(context: Context?, position: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_SETTING_WEBVIEW_POSITION, position).apply()
        LOGD("SetPref position = " + position)
    }

    fun getWebViewPosition(context: Context?): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD("GetPref position = " + sp.getInt(PREF_SETTING_WEBVIEW_POSITION, 0))
        return sp.getInt(PREF_SETTING_WEBVIEW_POSITION, 0)
    }

    fun setWebViewUrl(context: Context?, url: String) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putString(PREF_SETTING_WEBVIEW_URL, url).apply()
        LOGD("SetPref url = " + url)
    }

    fun getWebViewUrl(context: Context?): String? {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD("GetPref url = " + sp.getString(PREF_SETTING_WEBVIEW_URL, null))
        return sp.getString(PREF_SETTING_WEBVIEW_URL, null)
    }

    fun setCreateFirstFoldersFlg(context: Context?, flg: Boolean) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putBoolean(PREF_CREATE_FIRST_FOLDERS_FLG, flg).apply()
        LOGD("SetPref " + PREF_CREATE_FIRST_FOLDERS_FLG + " = " + flg)
    }

    fun getCreateFirstFoldersFlg(context: Context?): Boolean {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(
            "GetPref " + PREF_CREATE_FIRST_FOLDERS_FLG + " = " + sp.getBoolean(
                PREF_CREATE_FIRST_FOLDERS_FLG,
                false
            )
        )
        return sp.getBoolean(PREF_CREATE_FIRST_FOLDERS_FLG, false)
    }

    fun setFolderSeqid(context: Context?, seqid: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_CREATE_FOLDERS_SEQID, seqid).apply()
        LOGD("SetPref $PREF_CREATE_FOLDERS_SEQID = $seqid")
    }

    fun getFolderSeqid(context: Context?): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(
            "GetPref $PREF_CREATE_FOLDERS_SEQID = " + sp.getInt(
                PREF_CREATE_FOLDERS_SEQID,
                FoldersRepository.FOLDERS_FIRST_SEQID
            )
        )
        return sp.getInt(PREF_CREATE_FOLDERS_SEQID, FoldersRepository.FOLDERS_FIRST_SEQID)
    }

    fun setStockPageCount(context: Context?, pageCount: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_STOCK_PAGE_COUNT, pageCount).apply()
        LOGD("SetPref $PREF_STOCK_PAGE_COUNT = $pageCount")
    }

    fun getStockPageCount(context: Context?): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(
            "GetPref $PREF_STOCK_PAGE_COUNT = " + sp.getInt(
                PREF_STOCK_PAGE_COUNT,
                1
            )
        )
        return sp.getInt(PREF_STOCK_PAGE_COUNT, 1)
    }

    fun setStockLoadingCompleted(context: Context?, isCompleted: Boolean) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putBoolean(PREF_STOCK_LOADING_COMPLETED, isCompleted).apply()
        LOGD("SetPref $PREF_STOCK_LOADING_COMPLETED = $isCompleted")
    }

    fun getStockLoadingCompleted(context: Context?): Boolean {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(
            "GetPref $PREF_STOCK_LOADING_COMPLETED = " + sp.getBoolean(
                PREF_STOCK_LOADING_COMPLETED,
                false
            )
        )
        return sp.getBoolean(PREF_STOCK_LOADING_COMPLETED, false)
    }

}

