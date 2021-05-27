package jp.kirin3.anytimeqiita.data

data class SettingCheckBoxData(
    var reading_use_external_browse: Boolean = false,
    var setting_show_icon: Boolean = true,
    var setting_show_lgtm: Boolean = true,
    var setting_update_time: Boolean = true
)