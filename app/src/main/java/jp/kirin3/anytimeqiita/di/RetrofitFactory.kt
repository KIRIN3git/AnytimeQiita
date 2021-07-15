package jp.kirin3.anytimeqiita.di

import retrofit2.Retrofit

interface RetrofitFactory {
    fun buildRetrofit(): Retrofit
    fun getHostName(): String
}