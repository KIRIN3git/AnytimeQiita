package jp.kirin3.anytimeqiita.data

/**
 * Spinnerの表示順、ソートに使われるenumデータクラス
 * StocksResponseDataの値を使用する必要がある
 * 並び順はstocks_spinner.xmlと同一である必要がある
 */
enum class SpinnerData(val column: String?) {
    SEQUENCE("sequence"),
    LGTM("likes_count"),
    UPDATE("updated_at"),
    NAME("title")
}
