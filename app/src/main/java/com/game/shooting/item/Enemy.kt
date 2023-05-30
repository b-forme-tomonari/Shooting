package com.game.shooting.item

import android.content.Context
import android.support.v4.content.ContextCompat
import com.game.shooting.R

/**
 * 敵の設定
 */
class Enemy(left: Float, top: Float, radius: Float,
             screenWidth: Int, context: Context) : Circle(left, top, radius, screenWidth, context) {

    // 敵を生成
    init  {
        // 描画設定
        this.paint.color = ContextCompat.getColor(context, R.color.teal_700)
        this.paint.alpha = 200

        this.hp = 1
    }

    // 弾を移動
    override fun move(x: Float, y: Float) {
        // 画面の左端の場合
        if(left + x < 0) {
            direction = 1
        }
        // 画面の右端の場合
        else if(right + x > screenWidth){
            direction = -1
        }

        // 描画位置
        this.left = this.left + direction * x * speed
        this.top = this.top + y * speed
        this.right = this.right + direction * x * speed
        this.bottom = this.bottom + y * speed
    }
}