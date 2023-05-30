package com.game.shooting.view

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import com.game.shooting.ResultActivity
import com.game.shooting.SecondActivity
import com.game.shooting.item.Enemy

/**
 * 1ステージのビュー
 */
class FirstView(context: Context) : ShootingView(context) {

    override fun onDraw(canvas: Canvas) {
        // 機体のHPが0以下の場合
        if(aircraft.hp <= 0) {
            // アニメーションとタイマーをクリア
            this.clearAnimation()

            val intent = Intent(context, ResultActivity::class.java)
            // スコアを結果画面表示するために保存
            intent.putExtra("SCORE", score)
            // 結果画面に移動
            context.startActivity(intent)
        }
        // スコアがクリアスコア以上の場合
        else if(score >= clearScore) {
            // アニメーションとタイマーをクリア
            this.clearAnimation()

            val intent = Intent(context, SecondActivity::class.java)
            // スコアを結果画面表示するために保存
            intent.putExtra("SCORE", score)
            // 結果画面に移動
            context.startActivity(intent)

        }
        else {
            super.onDraw(canvas)
            // 敵が上限以下の場合は敵を追加
            if(enemyList.size <= limitEnemy) {
                val enemy = Enemy(
                    (Math.random() * screenWidth).toFloat(),
                    (-500..-50).random().toFloat(), (25..40).random().toFloat(),
                    width, context)
                enemyList.add(enemy)
            }
        }
    }
}
