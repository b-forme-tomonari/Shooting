package com.game.shooting.item

import android.content.Context
import android.support.v4.content.ContextCompat
import com.game.shooting.R

/**
 * 弾の設定
 */
class EnemyBullet(left: Float, top: Float, radius: Float,
                  screenWidth: Int, context: Context) : Circle(left, top, radius, screenWidth, context) {

    var xDirection = 1f
    var yDirection = 1f
    var radian = 0f

    // 弾を生成
    init  {
        // 描画設定
        this.paint.color = ContextCompat.getColor(context, R.color.colorPrimary)
        this.paint.alpha = 200
    }

    // 弾をy軸方向に移動
    override fun move(x: Float, y: Float) {
        this.left = this.left + x
        this.top = this.top + y
        this.right = this.right + x
        this.bottom = this.bottom + y

        this.x = left
        this.y = top + this.radius
    }
}