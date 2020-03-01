package kirin3.jp.mljanken.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.RectF
import android.os.Build
import android.view.Display
import android.view.WindowManager
import jp.kirin3.anytimeqiita.R

object ViewUtils {

    internal var mirrorX: Float = 0.toFloat()
    internal var mirrorY: Float = 0.toFloat()

    /*
     * DP→DX変換
     * dpToPx(COUNTDONW_TEXT_SIZE_DP,context.getResources())
     */
    fun dpToPx(dp: Float, resources: Resources): Float {

        return dp * getDensity(resources) + 0.5f
    }

    /*
     * density(密度取得)
     */
    fun getDensity(resources: Resources): Float {
        return resources.displayMetrics.density
    }

    /*
     * 画像密度倍率を取得
     */
    fun getDisplayMagnification(resources: Resources): Float {
        val dpi = getDensityDpi(resources)
        return if (dpi <= 120)
            0.75f
        else if (dpi <= 160)
            1.0f
        else if (dpi <= 240)
            1.5f
        else if (dpi <= 320)
            2.0f
        else if (dpi <= 480)
            3.0f
        else if (dpi <= 640)
            4.0f
        else
            4.0f
    }


    /*
     * densityDpi(スクリーン密度取得)
     */
    fun getDensityDpi(resources: Resources): Float {
        return resources.displayMetrics.densityDpi.toFloat()
    }

    /*
     * 画像の横幅倍率を取得(100を基準)
     */
    fun getXDisplayMagnification(resources: Resources): Float {
        return getYDpi(resources) / 100
    }

    /*
     * xdpi
     */
    fun getXDpi(resources: Resources): Float {
        return resources.displayMetrics.xdpi
    }

    /*
     * ydpi
     */
    fun getYDpi(resources: Resources): Float {
        return resources.displayMetrics.ydpi
    }

    // テキストビューの反転表示
    fun mirrorDrowText(canvas: Canvas, paint: Paint, x: Float, y: Float, text: String) {

        canvas.drawText(text, x - paint.measureText(text) / 2, y - (paint.descent() + paint.ascent()) / 2, paint)

        // 反転表示
        mirrorX = canvas.width - x
        mirrorY = canvas.height - y

        canvas.rotate(180f, mirrorX, mirrorY)
        canvas.drawText(
            text,
            mirrorX - paint.measureText(text) / 2,
            mirrorY - (paint.descent() + paint.ascent()) / 2,
            paint
        )
        //        canvas.rotate(180,mirrorX,mirrorY);
    }

    // テキストビューの反転表示
    // 背景もプラス
    fun mirrorDrowTextPlusRect(canvas: Canvas, paint: Paint, paint2: Paint, x: Float, y: Float, text: String) {

        // 余白
        val sabun = 30f
        // 丸み
        val rx = 30f
        val ry = 30f
        var rectF: RectF


        rectF = RectF(
            x - paint.measureText(text) / 2 - sabun,
            y + (paint.descent() + paint.ascent()),
            x - paint.measureText(text) / 2 + paint.measureText(text) + sabun,
            y - (paint.descent() + paint.ascent())
        )

        canvas.drawRoundRect(
            rectF,
            rx, // 角丸を表す円のrx
            ry, // 角丸を表す円のry
            paint2
        )

        canvas.drawText(text, x - paint.measureText(text) / 2, y - (paint.descent() + paint.ascent()) / 2, paint)

        // 反転表示
        mirrorX = canvas.width - x
        mirrorY = canvas.height - y


        rectF = RectF(
            mirrorX - paint.measureText(text) / 2 - sabun,
            mirrorY + (paint.descent() + paint.ascent()),
            mirrorX - paint.measureText(text) / 2 + paint.measureText(text) + sabun,
            mirrorY - (paint.descent() + paint.ascent())
        )

        canvas.drawRoundRect(
            rectF,
            rx, // 角丸を表す円のrx
            ry, // 角丸を表す円のry
            paint2
        )

        canvas.rotate(180f, mirrorX, mirrorY)
        canvas.drawText(
            text,
            mirrorX - paint.measureText(text) / 2,
            mirrorY - (paint.descent() + paint.ascent()) / 2,
            paint
        )

    }

    // 端末のサイズを取得(Pointクラス px)
    fun getDisplaySize(context: Context): Point {

        val winMan = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = winMan.defaultDisplay
        val real = Point(0, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            // Android 4.2以上
            display.getRealSize(real)
            return real

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            // Android 3.2以上
            try {
                val getRawWidth = Display::class.java.getMethod("getRawWidth")
                val getRawHeight = Display::class.java.getMethod("getRawHeight")
                val width = getRawWidth.invoke(display) as Int
                val height = getRawHeight.invoke(display) as Int
                real.set(width, height)
                return real

            } catch (e: Exception) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace()
            }

        }
        return real
    }


    /* タブレット端末かの判定を行う
     * bools.xmlにて判定
     */
    fun checkTablet(resources: Resources): Boolean {
        return resources.getBoolean(R.bool.is_tablet)
    }
}
