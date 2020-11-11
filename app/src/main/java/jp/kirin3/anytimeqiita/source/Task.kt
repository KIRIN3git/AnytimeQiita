package jp.kirin3.anytimeqiita.source

import java.util.*

//TODO: KIRIN3 ROOM OR REALM?

data class Task @JvmOverloads constructor(
    var title: String = "",
    var description: String = "",
    var id: String = UUID.randomUUID().toString()
) {

    var isCompleted = false

    val titleForList: String
        get() = if (title.isNotEmpty()) title else description

    val isActive
        get() = !isCompleted

    val isEmpty
        get() = title.isEmpty() && description.isEmpty()
}