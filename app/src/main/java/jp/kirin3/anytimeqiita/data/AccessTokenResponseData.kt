package jp.kirin3.anytimeqiita.data;

data class AccessTokenResponseData(
    val client_id: String,
    val scopes: Array<String>,
    val token: String
)

