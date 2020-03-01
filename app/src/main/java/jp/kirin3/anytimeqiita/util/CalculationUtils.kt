package kirin3.jp.mljanken.util

import java.math.BigDecimal

object CalculationUtils {
    /**
     * 勝率を取得
     */
    fun getProbability(win_num: Int, lose_num: Int): Float {

        if (win_num == 0) return 0.0f
        else if (lose_num == 0) return 100.0f
        else {
            val win_num_d = win_num.toDouble()
            val lose_num_d = lose_num.toDouble()
            val probability = (win_num_d / (win_num_d + lose_num_d)) * 100.0f
            var bd = BigDecimal(probability)
            bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP)
            val ret_probability = bd.toFloat()

            return ret_probability
        }
    }

    fun getProbability2(win_num: Int, lose_num: Int): Double {

        if (win_num == 0) return 0.0
        else if (lose_num == 0) return 100.0
        else {
            val win_num_d = win_num.toDouble()
            val lose_num_d = lose_num.toDouble()
            val probability = (win_num_d / (win_num_d + lose_num_d)) * 100.0
            var bd = BigDecimal(probability)
            bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP)
            val ret_probability = bd.toDouble()

            return ret_probability
        }
    }
}