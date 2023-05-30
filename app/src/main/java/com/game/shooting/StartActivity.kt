package com.game.shooting

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

/**
 * スタート画面
 */
class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    /**
     * メイン画面に移動
     */
    fun startGame(view: View?) {
        startActivity(Intent(applicationContext, FirstActivity::class.java))
    }
}