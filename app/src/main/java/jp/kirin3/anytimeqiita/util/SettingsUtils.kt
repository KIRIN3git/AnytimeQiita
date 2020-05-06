package kirin3.jp.mljanken.util

import android.content.Context
import androidx.preference.PreferenceManager
import kirin3.jp.mljanken.util.LogUtils.LOGD

object SettingsUtils {

    val TAG = "TAG"


    val PREF_SETTING_UUID = "pref_setting_uuid"
    val PREF_SETTING_GENDER = "pref_setting_gender"
    val PREF_SETTING_AGE = "pref_setting_age"
    val PREF_SETTING_PREFECTURE = "pref_setting_prefecture"
    val PREF_SETTING_BATTEL_NUM = "pref_setting_battle_num"
    val PREF_SETTING_WIN_NUM = "pref_setting_win_num"
    val PREF_SETTING_DROW_NUM = "pref_setting_drow_num"
    val PREF_SETTING_LOSE_NUM = "pref_setting_lose_num"
    val PREF_SETTING_NOW_CHAIN_WIN_NUM = "pref_setting_now_chain_win_num"
    val PREF_SETTING_NOW_CHAIN_LOSE_NUM = "pref_setting_now_chain_lose_num"
    val PREF_SETTING_MAX_CHAIN_WIN_NUM = "pref_setting_max_chain_win_num"
    val PREF_SETTING_MAX_CHAIN_LOSE_NUM = "pref_setting_max_chain_lose_num"

    val PREF_SETTING_CODE = "pref_setting_code"
    val PREF_SETTING_TOKEN = "pref_setting_token"

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


    /**
     * UUIDを保存
     */
    fun setSettingUuid(context: Context, uuid: String) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putString(PREF_SETTING_UUID, uuid).apply()
    }

    /**
     * 性別のIDを保存
     */
    fun setSettingRadioIdGender(context: Context, gender_id: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_SETTING_GENDER, gender_id).apply()
    }

    /**
     * 年代のIDを保存
     */
    fun setSettingRadioIdAge(context: Context, age_id: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_SETTING_AGE, age_id).apply()
    }

    /**
     * 出身県のIDを保存
     */
    fun setSettingRadioIdPrefecture(context: Context, prefecture_id: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_SETTING_PREFECTURE, prefecture_id).apply()
    }

    /**
     * 勝負数を保存
     */
    fun setSettingBattleNum(context: Context, battle_num: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_SETTING_BATTEL_NUM, battle_num).apply()
    }

    /**
     * 総合勝ち数を保存
     */
    fun setSettingWinNum(context: Context, win_num: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_SETTING_WIN_NUM, win_num).apply()
    }

    /**
     * 総合あいこ数を保存
     */
    fun setSettingDrowNum(context: Context, drow_num: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_SETTING_DROW_NUM, drow_num).apply()
    }

    /**
     * 総合負け数を保存
     */
    fun setSettingLoseNum(context: Context, lose_num: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_SETTING_LOSE_NUM, lose_num).apply()
    }

    /**
     * 現在の連勝数を保存
     */
    fun setSettingNowChainWinNum(context: Context, chain_win_num: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_SETTING_NOW_CHAIN_WIN_NUM, chain_win_num).apply()
    }

    /**
     * 現在の連敗数を保存
     */
    fun setSettingNowChainLoseNum(context: Context, chain_lose_num: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_SETTING_NOW_CHAIN_LOSE_NUM, chain_lose_num).apply()
    }

    /**
     * 最大の連勝数を保存
     */
    fun setSettingMaxChainWinNum(context: Context, chain_win_num: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_SETTING_MAX_CHAIN_WIN_NUM, chain_win_num).apply()
    }

    /**
     * 最大の連敗数を保存
     */
    fun setSettingMaxChainLoseNum(context: Context, chain_lose_num: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().putInt(PREF_SETTING_MAX_CHAIN_LOSE_NUM, chain_lose_num).apply()
    }


    /////////////////////////////////////////

    /**
     * UUIDを取得
     */
    fun getSettingUuid(context: Context): String? {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getString(PREF_SETTING_UUID, "")
    }

    /**
     * 性別のIDを取得
     */
    fun getSettingRadioIdGender(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_SETTING_GENDER, 0)
    }

    /**
     * 年代のIDを取得
     */
    fun getSettingRadioIdAge(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_SETTING_AGE, 0)
    }

    /**
     * 出身県のIDを取得
     */
    fun getSettingRadioIdPrefecture(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_SETTING_PREFECTURE, 0)
    }

    /**
     * 勝負数を取得
     */
    fun getSettingBattleNum(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_SETTING_BATTEL_NUM, 0)
    }

    /**
     * 総合勝ち数を取得
     */
    fun getSettingWinNum(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_SETTING_WIN_NUM, 0)
    }

    /**
     * 総合あいこ数を取得
     */
    fun getSettingDrowNum(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_SETTING_DROW_NUM, 0)
    }

    /**
     * 総合負け数を取得
     */
    fun getSettingLoseNum(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_SETTING_LOSE_NUM, 0)
    }

    /**
     * 現在の連勝数を取得
     */
    fun getSettingNowChainWinNum(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_SETTING_NOW_CHAIN_WIN_NUM, 0)
    }

    /**
     * 現在の連敗数を取得
     */
    fun getSettingNowChainLoseNum(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_SETTING_NOW_CHAIN_LOSE_NUM, 0)
    }

    /**
     * 最大の連勝数を取得
     */
    fun getSettingMaxChainWinNum(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_SETTING_MAX_CHAIN_WIN_NUM, 0)
    }

    /**
     * 最大の連敗数を取得
     */
    fun getSettingMaxChainLoseNum(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getInt(PREF_SETTING_MAX_CHAIN_LOSE_NUM, 0)
    }


    /**
     * 勝率を取得
     */
    fun getSettingProbability(context: Context): Double {
        val win_num = getSettingWinNum(context)
        val lose_num = getSettingLoseNum(context)

        return CalculationUtils.getProbability2(win_num, lose_num)

    }
}

