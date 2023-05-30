package com.game.shooting.item

import android.content.Context
import android.support.v4.content.ContextCompat
import com.game.shooting.R

/**
 * 弾の設定
 */
class Bullet(left: Float, top: Float, radius: Float,
             screenWidth: Int, context: Context) : Circle(left, top, radius, screenWidth, context) {

    // 弾を生成
    init  {
        // 描画設定
        this.paint.color = ContextCompat.getColor(context, R.color.colorAccent)
        this.paint.alpha = 200
    }

    // 弾をy軸方向に移動
    override fun move(x: Float, y: Float) {
        this.left = this.left + direction * x * speed
        this.top = this.top - y * 2
        this.right = this.right + direction * x * speed
        this.bottom = this.bottom - y * 2
    }
}