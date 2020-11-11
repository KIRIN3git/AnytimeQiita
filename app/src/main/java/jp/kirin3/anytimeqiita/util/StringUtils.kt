package jp.kirin3.anytimeqiita.util


object StringUtils {

    /**
     * 文字列のnull or 空チェック
     */
    fun isEmpty(data: String?): Boolean {
        if (data == null) {
            return true
        } else if (data.equals("")) {
            return true
        }
        return false
    }
}