package com.game.shooting.detection

import android.graphics.PointF
import com.game.shooting.item.Aircraft
import com.game.shooting.item.Circle
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * 当たり判定
 */
class HitDetection {

    /**
     * 弾が敵に当たっているかを判定
     * 円と円の当たり判定
     */
    fun isHitCircle(circle1: Circle, circle2: Circle): Boolean {
        val a = circle1.centerX() - circle2.centerX() // x座標の差分
        val b = circle1.centerY() - circle2.centerY() // y座標の差分
        // 敵と弾との中心座標の距離
        val c = sqrt(a * a + b * b)

        // 距離と弾と敵の半径の和を比較
        return c <= circle1.radius + circle2.radius
    }

    /**
     * 弾が機体に当たっているかを判定
     */
    fun isHitTriangle(circle: Circle, aircraft: Aircraft) : Boolean {
        return isHitLine(circle, aircraft.p1, aircraft.p2) ||
                isHitLine(circle, aircraft.p2, aircraft.p3) ||
                isHitLine(circle, aircraft.p3, aircraft.p1)
    }

    /**
     * 線分と円の当たり判定
     */
    private fun isHitLine(circle: Circle, p1: PointF, p2: PointF) : Boolean {
        // ベクトル作成
        val startToCenter = PointF(circle.centerX() - p1.x, circle.centerY() - p1.y)
        val endToCenter = PointF(circle.centerX() - p2.x, circle.centerY() - p2.y)
        val startToEnd = PointF(p2.x - p1.x, p2.y - p1.y)
        var normalStartToEnd = PointF(0.0f, 0.0f)

        // 単位ベクトル化
        val distance = sqrt(startToEnd.x * startToEnd.x + startToEnd.y * startToEnd.y)
        if(distance > 0.0f) {
            normalStartToEnd = PointF(startToEnd.x / distance, startToEnd.y / distance)
        }

        // 射影した線分の長さ
        // 始点と円の中心で外積
        val distanceProjection = startToCenter.x * normalStartToEnd.y - normalStartToEnd.x * startToCenter.y

        // 線分と円の最短の長さが半径よりも小さい
        if(abs(distanceProjection) < circle.radius){
            // 始点 => 終点と始点 => 円の中心の内積を計算
            val dot01 = startToCenter.x * startToEnd.x + startToCenter.y * startToEnd.y
            // 始点 => 終点と終点 => 円の中心の内積を計算
            val dot02 = endToCenter.x * startToEnd.x + endToCenter.y * startToEnd.y

            // 二つの内積の掛け算結果が0以下なら当たり
            if(dot01 * dot02 <= 0.0f){
                return true
            }

            // ベクトルの長さ
            val length1 = sqrt(startToCenter.x * startToCenter.x + startToCenter.y * startToCenter.y)
            val length2 = sqrt(endToCenter.x * endToCenter.x + endToCenter.y * endToCenter.y)

            // 上の条件から漏れた場合、円は線分上にはないので、
            // 始点 => 円の中心の長さか、終点 => 円の中心の長さが
            // 円の半径よりも短かったら当たり
            if(length1 < circle.radius || length2 < circle.radius){
                return true
            }
            return false
        }
        else {
            return false
        }
    }
}