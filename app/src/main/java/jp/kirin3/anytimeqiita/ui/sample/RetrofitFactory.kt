package jp.kirin3.anytimeqiita.ui.sample

import retrofit2.Retrofit

interface RetrofitFactory {
    fun create(): Retrofit
}