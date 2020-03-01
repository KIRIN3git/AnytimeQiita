package kirin3.jp.mljanken.util

import android.content.Context
//import com.google.firebase.firestore.FirebaseFirestore
import kirin3.jp.mljanken.util.LogUtils.LOGD
import java.util.*


object CloudFirestoreHelper {
/*
    private val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    fun getInitDb(context: Context): FirebaseFirestore {
        var db = FirebaseFirestore.getInstance()
        return db
    }

    data class UserItem(
        val a1_gender: Int = 0,
        val a2_age: Int = 0,
        val a3_prefecture: Int = 0,
        val b1_battel_num: Int = 0,
        val b2_win_num: Int = 0,
        val b3_drow_num: Int = 0,
        val b4_lose_num: Int = 0,
        val b5_probability: Double = 0.0,
        val b6_max_chain_win_num: Int = 0,
        val b7_max_chain_lose_num: Int = 0,
        val c1_most_choice: Int = 0,
        val c2_first_choice: Int = 0,
        val c3_most_chain_choice: Int = 0,
        val c4_excellence_mode: Int = 0,
        val d1_mode1_rate: Double = 0.0,
        val d2_mode2_rate: Double = 0.0,
        val d3_mode3_rate: Double = 0.0,
        val d4_mode4_rate: Double = 0.0,
        val d5_mode5_rate: Double = 0.0,
        val d6_mode6_rate: Double = 0.0,
        val d7_mode7_rate: Double = 0.0,
        val d8_mode8_rate: Double = 0.0,
        val d9_mode9_rate: Double = 0.0,
        val y1_debug_flg: Boolean = false,
        val z1_upd_time: Date = Date()
    )


    /*
     * コレクション、ドキュメント、フィールドを登録
     * ・コレクション、ドキュメントが同名だと上書き保存
     * ・ .documnentを外せば、ランダムでユニークなIDが自動で付与される
     */
    fun addUserData(db: FirebaseFirestore, user: UserItem, collection: String, document: String) {
        db.collection(collection)
            .document(document)
            .set(user)
            .addOnSuccessListener { documentReference ->
                LOGD(TAG, "addUserData document:$document")
            }
            .addOnFailureListener { e -> LOGD(TAG, "Error adding document" + e) }
    }
    */

}


