package com.game.shooting.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.view.MotionEvent
import android.view.View
import com.game.shooting.R
import com.game.shooting.animation.MainAnimation
import com.game.shooting.detection.HitDetection
import com.game.shooting.item.Aircraft
import com.game.shooting.item.Bullet
import com.game.shooting.item.Circle
import com.game.shooting.item.EnemyBullet
import com.game.shooting.sound.SoundPlayer
import java.util.Calendar

/**
 * ステージビューのスーパークラス
 */
open class ShootingView(context: Context) : View(context) {
    //　アニメーション
    private var animation = MainAnimation(this)
    // 弾リスト
    var bulletList = mutableListOf<Bullet>()
    // 弾リスト
    var enemyBulletList = mutableListOf<EnemyBullet>()
    // 敵リスト
    var enemyList = mutableListOf<Circle>()
    // 機体
    val aircraft = Aircraft(540f, 1400f, context)
    // スコア
    var score = 0
    // クリアスコア
    var clearScore = 20
    // タイム
    private var time = 0L
    // 初期タイム
    private var initTime = 0L
    // 画面の上
    private val screenTop = 0f
    // 画面の下
    var screenBottom = 0
    // 画面の幅
    var screenWidth = 0
    // 敵の上限
    val limitEnemy = 15
    // x軸方向の移動量
    var x = 0
    // y軸方向の移動量
    var y = 0
    // グラデーション設定
    private val intArrayList = mutableListOf<IntArray>()
    // グラデーションの長さ
    private val colorLength = 20
    // 音の設定
    private val soundPlayer = SoundPlayer(context)
    // 文字のデザイン設定
    val paint = Paint()
    // 当たり判定処理クラス
    private val hitDetection = HitDetection()

    init {
        // アニメーションの起動期間を設定
        animation.duration = 2000
        this.startAnimation(animation)

        // 文字の描画設定
        Paint.Style.FILL
        paint.strokeWidth = 5f
        paint.textSize = 60f
        paint.color = ContextCompat.getColor(context, R.color.black)

        // 背景の描画設定
        for(i in 0..colorLength) {
            val intArray = IntArray(colorLength + 1)
            for (j in 0..colorLength) {
                if (i == j || (i + colorLength / 2) % (colorLength + 1) == j) {
                    intArray[j] = ContextCompat.getColor(context, R.color.purple)
                }
                else {
                    intArray[j] = ContextCompat.getColor(context, R.color.white)
                }
            }
            intArrayList.add(intArray)
        }

        // 初期時間を取得
        initTime = System.currentTimeMillis()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 画面サイズ取得
        screenWidth = width
        screenBottom = height

        // グラデーションの表示用
        val calendar = Calendar.getInstance()
        val intervalSecond = calendar.get(Calendar.MILLISECOND) / (1000 / intArrayList.size)

        // 画面の外に出た弾は取り除く
        bulletList = bulletList.filter {
            it.bottom > screenTop
        } as MutableList<Bullet>

        // 画面の外に出た敵は取り除く
        enemyList = enemyList.filter {
            it.top < screenBottom
        } as MutableList<Circle>

        // 当たり判定の処理を実行
        if((1 <= bulletList.size) && (1 <= enemyList.size)){
            hitBullet()
        }
        if(1 <= enemyList.size) {
            hitEnemy()
            hitEnemyBullet()
        }

        // 背景のグラデーション設定
        val gradient = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayList[intervalSecond % intArrayList.size])
        gradient.alpha = 100
        // 背景を設定
        this.background = gradient

        // 弾を移動して描画
        for(bullet in bulletList) {
            if(bullet.bottom > screenTop) {
                bullet.move(0f, y.toFloat())
                canvas.drawOval(bullet, bullet.paint)
            }

        }

        // 弾を移動して描画
        for(enemy in enemyList) {
            if(enemy.top < screenBottom) {
                enemy.move(x.toFloat(), y.toFloat())
                canvas.drawOval(enemy, enemy.paint)
            }
        }

        // 時間を測定
        val currentTime = System.currentTimeMillis()
        time = (currentTime - initTime) / 1000

        // スコアを描画
        canvas.drawText(context.getString(R.string.score, score), 20f, 70f, paint)
        canvas.drawText(context.getString(R.string.time, time), 500f, 70f, paint)
        canvas.drawText(context.getString(R.string.player_hit_points, aircraft.hp), 20f, 140f, paint)
        // 機体を描画
        canvas.drawPath(aircraft, aircraft.paint)
    }

    /**
     * 画面が押されたときの処理
     */

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {

        val runnable = object : Runnable {
            override fun run() {
                // 押された場所に機体を設定
                aircraft.reset()
                aircraft.move(event.x  , event.y - 50)


                // タッチまたはスライドした場合
                if(event.action == MotionEvent.ACTION_DOWN ||
                    event.action == MotionEvent.ACTION_MOVE) {
                    // 弾を追加
                    val bullet = Bullet(event.x, event.y, 20f, screenWidth, context)
                    bulletList.add(bullet)

                }
                //　タッチパネルが押された状態から、指を持ち上げた場合以外
                if(event.action != MotionEvent.ACTION_UP){

                    // 60ミリ秒で実行
                    handler.postDelayed(this, 60)
                }

            }
        }
        handler.post(runnable)

        //　再描画
        invalidate()
        return super.dispatchTouchEvent(event)
    }

    /**
     * x軸方向の移動
     */
    fun setXPosition(pos: Int) {
        x = pos
    }

    /**
     * y軸方向の移動
     */
    fun setYPosition(pos: Int) {
        y = pos
    }

    /**
     * 機体が敵の弾に当たった時の処理
     */
    private fun hitEnemyBullet() {
        // 削除する弾を保存するための削除リスト
        val deleteEnemyBullet = mutableListOf<EnemyBullet>()
        for(bullet in enemyBulletList) {
            // 弾が敵に当たった時の判定
            if(hitDetection.isHitTriangle(bullet, aircraft)){
                soundPlayer.playOverSound()
                aircraft.hp--
                // スコアが0以上の場合
                if(score > 0) {
                    score--
                }
                deleteEnemyBullet.add(bullet)
            }
        }
        // 弾の削除を実行
        enemyBulletList.removeAll(deleteEnemyBullet)
    }

    /**
     * 弾が敵に当たった時の処理
     */
    private fun hitBullet() {
        // 削除する弾と敵を保存するための削除リスト
        val deleteBullet = mutableListOf<Bullet>()
        val deleteEnemy = mutableListOf<Circle>()
        for(bullet in bulletList) {
            for(enemy in enemyList) {
                // 弾が敵に当たった時の判定
                if(hitDetection.isHitCircle(bullet, enemy)){
                    soundPlayer.playHitSound()
                    score++
                    enemy.hp--
                    deleteBullet.add(bullet)
                    // 敵のHPが0以下の場合削除
                    if(enemy.hp <= 0) {
                        deleteEnemy.add(enemy)
                    }
                }
            }
        }

        // 弾と敵の削除を実行
        bulletList.removeAll(deleteBullet)
        enemyList.removeAll(deleteEnemy)
    }

    /**
     * 敵が機体に当たった時の処理
     */
    private fun hitEnemy() {
        // 削除する敵を保存するための削除リスト
        val deleteEnemy = mutableListOf<Circle>()
        for(enemy in enemyList){
            // 弾が機体に当たったら削除リストに追加
            if(hitDetection.isHitTriangle(enemy, aircraft)) {
                soundPlayer.playOverSound()

                // 敵と機体のHPを1減少
                enemy.hp--
                aircraft.hp--

                // スコアが0以上の場合
                if(score > 0) {
                    score--
                }

                // 敵のHPが0以下の場合削除
                if(enemy.hp <= 0) {
                    deleteEnemy.add(enemy)
                }
            }
        }

        // 敵の削除を実行
        enemyList.removeAll(deleteEnemy)
    }
}
