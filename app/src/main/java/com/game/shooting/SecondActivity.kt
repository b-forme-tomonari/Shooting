package com.game.shooting

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import com.game.shooting.view.SecondView

/**
 * 2ステージ目の画面
 */
class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val linearLayout = findViewById<LinearLayout>(R.id.linearSecond)

        // メイン画面からスコアを取得
        val score = intent.getIntExtra("SCORE", 0)

        linearLayout.addView(SecondView(this, score))
    }

    /**
     * 戻るボタンを無効
     */
    override fun onBackPressed() {}
}