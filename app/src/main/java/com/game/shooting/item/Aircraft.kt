package com.game.shooting.item

import android.content.Context
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.support.v4.content.ContextCompat
import com.game.shooting.R

/**
 * 機体の設定
 */
class Aircraft() : Path() {

    private val halfHeight = 50f
    // 三角形の頂点
    var p1 = PointF(0f, 0f)
    var p2 = PointF(0f, 0f)
    var p3 = PointF(0f, 0f)
    // 描画設定
    val paint = Paint()
    // 体力
    var hp = 100
    // x座標
    var x = 0f
    // y座標
    var y = 0f

    // 機体を生成
    constructor(x: Float, y: Float, context: Context) : this() {
        move(x, y)
        // 描画設定
        this.paint.color = ContextCompat.getColor(context, R.color.purple_500)
        this.paint.style = Paint.Style.FILL
        this.paint.alpha = 200
    }

    // 機体の描画設定と頂点の保存
    fun move(x: Float, y: Float) {
        this.x = x
        this.y = y
        this.moveTo(x, y - halfHeight)
        this.lineTo(x - halfHeight, y + 2 * halfHeight)
        this.lineTo(x + halfHeight, y + 2 * halfHeight)
        this.close()
        this.p1 = PointF(x, y - halfHeight)
        this.p2 = PointF(x - halfHeight, y + 2 * halfHeight)
        this.p3 = PointF(x + halfHeight, y + 2 * halfHeight)
    }
}