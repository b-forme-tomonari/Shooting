package com.game.shooting

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView

/**
 * 結果画面
 */
class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // スコアラベル
        val scoreLabel = findViewById<TextView>(R.id.scoreLabel)
        // ハイスコアラベル
        val highScoreLabel = findViewById<TextView>(R.id.highScoreLabel)

        // メイン画面からスコアを取得
        val score = intent.getIntExtra("SCORE", 0)
        scoreLabel.text = score.toString() + ""

        // ハイスコアを取得
        val sharedPreferences = getSharedPreferences("GAME_DATA", MODE_PRIVATE)
        val highScore = sharedPreferences.getInt("HIGH_SCORE", 0)

        // スコアがハイスコアより高い場合
        if (score > highScore) {
            // ハイスコアの表示
            highScoreLabel.text = getString(R.string.high_score, score)
            // ハイスコアを更新
            val editor = sharedPreferences.edit()
            editor.putInt("HIGH_SCORE", score)
            editor.apply()

        } else {
            // ハイスコアの表示
            highScoreLabel.text = getString(R.string.high_score, highScore)
        }

    }

    /**
     * メイン画面に移動
     */
    fun playAgain(view: View?) {
        startActivity(Intent(applicationContext, FirstActivity::class.java))
    }

    /**
     * 戻るボタンを無効
     */
    override fun onBackPressed() {}
}