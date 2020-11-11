package jp.kirin3.anytimeqiita.data

data class AccessTokenRequestData(
    val client_id: String,
    val client_secret: String,
    val code: String
)