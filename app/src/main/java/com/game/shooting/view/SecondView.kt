package com.game.shooting.view

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import com.game.shooting.R
import com.game.shooting.ResultActivity
import com.game.shooting.item.BossEnemy
import com.game.shooting.item.EnemyBullet
import kotlin.math.atan2
import kotlin.math.cos

/**
 * 2ステージのビュー
 */
class SecondView(context: Context) : ShootingView(context) {
    // 初期判定
    private var first = true
    // 間隔
    private var interval = 0

    constructor(context: Context, score: Int): this(context) {
        // スコア設定
        this.score = score
    }

    override fun onDraw(canvas: Canvas) {
        // 敵または機体のHPが0以下の場合
        if(!first && enemyList.size == 0 || aircraft.hp <= 0) {
            // アニメーションとタイマーをクリア
            this.clearAnimation()

            val intent = Intent(context, ResultActivity::class.java)
            // スコアを結果画面表示するために保存
            intent.putExtra("SCORE", score)
            // 結果画面に移動
            context.startActivity(intent)

        }
        else {
            super.onDraw(canvas)
            // 初回実行
            if (first) {
                // ボス敵を追加
                val bossEnemy = BossEnemy(
                    (screenWidth / 2).toFloat(),
                    (screenBottom / 5).toFloat(), 150f,
                    width, context)
                enemyList.add(bossEnemy)
            }


            // 描画の1/20の頻度で弾を追加
            if(interval % 20 == 0) {

                // 弾を追加
                val bullet = EnemyBullet((screenWidth / 2).toFloat(),
                    (screenBottom / 5).toFloat() + 150f, 20f, screenWidth, context)

                bullet.yDirection = 2f
                enemyBulletList.add(bullet)

            }
            interval++

            for(bullet in enemyBulletList) {

                // 機体と弾との差
                val xDiff = aircraft.x - bullet.x
                val yDiff = aircraft.y - bullet.y

                // 角度を求める
                val radian = atan2(yDiff, xDiff)

                // 弾を移動して描画
                bullet.move(cos(radian) * x, bullet.yDirection * y)
                canvas.drawOval(bullet, bullet.paint)
            }

            // ボス敵のHPを表示
            canvas.drawText(context.getString(R.string.enemy_hit_points,
                if(enemyList.size == 0) 0 else enemyList[0].hp), 500f, 140f, paint)
            first = false
        }
    }
}
