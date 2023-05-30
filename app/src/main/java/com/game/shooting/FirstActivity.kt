package com.game.shooting
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import com.game.shooting.view.FirstView

/**
 * 1ステージ目の画面
 */
class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        val linearLayout = findViewById<LinearLayout>(R.id.linearFirst)
        linearLayout.addView(FirstView(this))
    }

    /**
     * 戻るボタンを無効
     */
    override fun onBackPressed() {}
}