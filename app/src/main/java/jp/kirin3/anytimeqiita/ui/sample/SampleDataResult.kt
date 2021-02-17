package jp.kirin3.anytimeqiita.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SampleDataResult(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String
)