package com.game.shooting.animation

import android.view.animation.Animation
import android.view.animation.Transformation
import com.game.shooting.view.ShootingView

/**
 * 等速運動するアニメーション
 */
class MainAnimation internal constructor(private val view: ShootingView) :
    Animation() {

    override fun applyTransformation(
        interpolatedTime: Float, transformation: Transformation) {
        // 矩形のx軸位置をセット
        val xPos = 10
        view.setXPosition(xPos)
        // 矩形のy軸位置をセット
        val yPos = 10
        view.setYPosition(yPos)

        view.requestLayout()
    }

}