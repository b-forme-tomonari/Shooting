package com.game.shooting.item

import android.content.Context
import android.graphics.Paint
import android.graphics.RectF
import android.support.v4.content.ContextCompat
import com.game.shooting.R

/**
 * 円の設定
 */
open class Circle() : RectF() {
    // 半径
    var radius = 0f
    // 描画
    val paint = Paint()
    // 画面幅
    var screenWidth = 0
    // 方向
    var direction = 1
    // 速度
    var speed = 0
    // 体力
    var hp = 0
    // x座標
    var x = 0f
    // y座標
    var y = 0f

    // 敵の生成
    constructor(left: Float, top: Float, radius: Float,
                screenWidth: Int, context: Context) : this() {
        this.radius = radius

        // x座標とy座標
        this.x = left
        this.y = top + this.radius

        // 描画位置
        this.left = left - this.radius
        this.top = top
        this.right = left + this.radius
        this.bottom = top + 2 * this.radius

        // 描画設定
        this.paint.color = ContextCompat.getColor(context, R.color.teal_700)
        this.paint.alpha = 200

        // 方向設定
        this.direction = (-1..1).random()
        // 画面幅設定
        this.screenWidth = screenWidth
        // スピード設定
        this.speed = (1..3).random()
    }

    open fun move(x: Float, y: Float){}

}