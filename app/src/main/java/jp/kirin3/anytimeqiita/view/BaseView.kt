package jp.kirin3.anytimeqiita.view

interface BaseView<T> {
    // View(Fragment)はPresenterのインスタンスが必須
    var presenter: T
}