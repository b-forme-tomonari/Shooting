package com.game.shooting.item

import android.content.Context
import android.support.v4.content.ContextCompat
import com.game.shooting.R

/**
 * ボス敵の設定
 */
class BossEnemy(left: Float, top: Float, radius: Float,
                screenWidth: Int, context: Context) : Circle(left, top, radius, screenWidth, context) {

    // 敵を生成
    init  {
        // 描画設定
        this.paint.color = ContextCompat.getColor(context, R.color.teal_700)
        this.paint.alpha = 200

        // 体力設定
        this.hp = 100
    }
}