package kirin3.jp.mljanken.util

import android.content.Context
import android.preference.PreferenceManager
import kirin3.jp.mljanken.util.LogUtils.LOGD

object SettingsUtils {

    val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    enum class Gender {
        NOTHING,
        MEN,
        WOMEN
    }

    enum class Age {
        NOTHING,
        ZERO,
        TEN,
        TWENTY,
        THERTY,
        FORTY,
        FIFTY,
        SIXTY,
        SEVENTY,
        EIGHTY
    }

    val genderItems = arrayOf("", "男性", "女性")

    val ageItems = arrayOf(
        "", "０～１歳", "１０代", "２０代", "３０代",
        "４０代", "５０代", "６０代", "７０代", "８０歳～"
    )

    val prefectureItems = arrayOf(
        "", "北海道", "青森", "岩手", "宮城", "秋田", "山形",
        "福島", "茨城", "栃木", "群馬", "埼玉", "千葉", "東京",
        "神奈川", "新潟", "富山", "石川", "福井", "山梨", "長野",
        "岐阜", "静岡", "愛知", "三重", "滋賀", "京都", "大阪", "兵庫",
        "奈良", "和歌山", "鳥取", "島根", "岡山", "広島", "山口",
        "徳島", "香川", "愛媛", "高知", "福岡", "佐賀", "長崎）",
        "熊本", "大分", "宮崎", "鹿児島", "沖縄"
    )


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


    /**
     * UUIDを保存
     */
    fun setSettingUuid(context: Context, uuid: String) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "setSettingUuid:" + uuid)
        sp.edit().putString(PREF_SETTING_UUID, uuid).apply()
    }

    /**
     * 性別のIDを保存
     */
    fun setSettingRadioIdGender(context: Context, gender_id: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "setSettingRadioIdGender:" + gender_id)
        sp.edit().putInt(PREF_SETTING_GENDER, gender_id).apply()
    }

    /**
     * 年代のIDを保存
     */
    fun setSettingRadioIdAge(context: Context, age_id: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "setSettingRadioIdAge:" + age_id)
        sp.edit().putInt(PREF_SETTING_AGE, age_id).apply()
    }

    /**
     * 出身県のIDを保存
     */
    fun setSettingRadioIdPrefecture(context: Context, prefecture_id: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "setSettingRadioIdPrefecture:" + prefecture_id)
        sp.edit().putInt(PREF_SETTING_PREFECTURE, prefecture_id).apply()
    }

    /**
     * 勝負数を保存
     */
    fun setSettingBattleNum(context: Context, battle_num: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "setSettingBattleNum:" + battle_num)
        sp.edit().putInt(PREF_SETTING_BATTEL_NUM, battle_num).apply()
    }

    /**
     * 総合勝ち数を保存
     */
    fun setSettingWinNum(context: Context, win_num: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "setSettingWinNum:" + win_num)
        sp.edit().putInt(PREF_SETTING_WIN_NUM, win_num).apply()
    }

    /**
     * 総合あいこ数を保存
     */
    fun setSettingDrowNum(context: Context, drow_num: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "setSettingDrowNum:" + drow_num)
        sp.edit().putInt(PREF_SETTING_DROW_NUM, drow_num).apply()
    }

    /**
     * 総合負け数を保存
     */
    fun setSettingLoseNum(context: Context, lose_num: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "setSettingLoseNum:" + lose_num)
        sp.edit().putInt(PREF_SETTING_LOSE_NUM, lose_num).apply()
    }

    /**
     * 現在の連勝数を保存
     */
    fun setSettingNowChainWinNum(context: Context, chain_win_num: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "setSettingNowChainWinNum:" + chain_win_num)
        sp.edit().putInt(PREF_SETTING_NOW_CHAIN_WIN_NUM, chain_win_num).apply()
    }

    /**
     * 現在の連敗数を保存
     */
    fun setSettingNowChainLoseNum(context: Context, chain_lose_num: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "setSettingNowChainLoseNum:" + chain_lose_num)
        sp.edit().putInt(PREF_SETTING_NOW_CHAIN_LOSE_NUM, chain_lose_num).apply()
    }

    /**
     * 最大の連勝数を保存
     */
    fun setSettingMaxChainWinNum(context: Context, chain_win_num: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "setSettingMaxChainWinNum:" + chain_win_num)
        sp.edit().putInt(PREF_SETTING_MAX_CHAIN_WIN_NUM, chain_win_num).apply()
    }

    /**
     * 最大の連敗数を保存
     */
    fun setSettingMaxChainLoseNum(context: Context, chain_lose_num: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "setSettingMaxChainLoseNum:" + chain_lose_num)
        sp.edit().putInt(PREF_SETTING_MAX_CHAIN_LOSE_NUM, chain_lose_num).apply()
    }


    /////////////////////////////////////////

    /**
     * UUIDを取得
     */
    fun getSettingUuid(context: Context): String? {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "getSettingUuid:" + sp.getString(PREF_SETTING_UUID, ""))
        return sp.getString(PREF_SETTING_UUID, "")
    }

    /**
     * 性別のIDを取得
     */
    fun getSettingRadioIdGender(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "getSettingRadioIdGender:" + sp.getInt(PREF_SETTING_GENDER, 0))
        return sp.getInt(PREF_SETTING_GENDER, 0)
    }

    /**
     * 年代のIDを取得
     */
    fun getSettingRadioIdAge(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "getSettingRadioIdAge:" + sp.getInt(PREF_SETTING_AGE, 0))
        return sp.getInt(PREF_SETTING_AGE, 0)
    }

    /**
     * 出身県のIDを取得
     */
    fun getSettingRadioIdPrefecture(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "getSettingRadioIdPrefecture:" + sp.getInt(PREF_SETTING_PREFECTURE, 0))
        return sp.getInt(PREF_SETTING_PREFECTURE, 0)
    }

    /**
     * 勝負数を取得
     */
    fun getSettingBattleNum(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "getSettingBattleNum:" + sp.getInt(PREF_SETTING_BATTEL_NUM, 0))
        return sp.getInt(PREF_SETTING_BATTEL_NUM, 0)
    }

    /**
     * 総合勝ち数を取得
     */
    fun getSettingWinNum(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "getSettingWinNum:" + sp.getInt(PREF_SETTING_WIN_NUM, 0))
        return sp.getInt(PREF_SETTING_WIN_NUM, 0)
    }

    /**
     * 総合あいこ数を取得
     */
    fun getSettingDrowNum(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "getSettingDrowNum:" + sp.getInt(PREF_SETTING_DROW_NUM, 0))
        return sp.getInt(PREF_SETTING_DROW_NUM, 0)
    }

    /**
     * 総合負け数を取得
     */
    fun getSettingLoseNum(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "getSettingLoseNum:" + sp.getInt(PREF_SETTING_LOSE_NUM, 0))
        return sp.getInt(PREF_SETTING_LOSE_NUM, 0)
    }

    /**
     * 現在の連勝数を取得
     */
    fun getSettingNowChainWinNum(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "getSettingNowChainWinNum:" + sp.getInt(PREF_SETTING_NOW_CHAIN_WIN_NUM, 0))
        return sp.getInt(PREF_SETTING_NOW_CHAIN_WIN_NUM, 0)
    }

    /**
     * 現在の連敗数を取得
     */
    fun getSettingNowChainLoseNum(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "getSettingNowChainLoseNum:" + sp.getInt(PREF_SETTING_NOW_CHAIN_LOSE_NUM, 0))
        return sp.getInt(PREF_SETTING_NOW_CHAIN_LOSE_NUM, 0)
    }

    /**
     * 最大の連勝数を取得
     */
    fun getSettingMaxChainWinNum(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "getSettingMaxChainWinNum:" + sp.getInt(PREF_SETTING_MAX_CHAIN_WIN_NUM, 0))
        return sp.getInt(PREF_SETTING_MAX_CHAIN_WIN_NUM, 0)
    }

    /**
     * 最大の連敗数を取得
     */
    fun getSettingMaxChainLoseNum(context: Context): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        LOGD(TAG, "getSettingMaxChainLoseNum:" + sp.getInt(PREF_SETTING_MAX_CHAIN_LOSE_NUM, 0))
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

