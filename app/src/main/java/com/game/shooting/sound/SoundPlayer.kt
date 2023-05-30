package com.game.shooting.sound

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import com.game.shooting.R

/**
 * 効果音を出力するためのクラス
 */
class SoundPlayer(context: Context?) {
    private var audioAttributes: AudioAttributes? = null

    companion object {
        private var soundPool: SoundPool? = null
        private var hitSound: Int = 0
        private var overSound: Int = 0
    }

    init {
        // API21以上の場合
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            soundPool = SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(2)
                .build()
        } else {
            soundPool = SoundPool(2, AudioManager.STREAM_MUSIC, 0)
        }
        hitSound = soundPool!!.load(context, R.raw.hit, 1)
        overSound = soundPool!!.load(context, R.raw.over, 1)
    }

    fun playHitSound() {
        soundPool!!.play(hitSound, 1.0f, 1.0f, 1, 0, 1.0f)
    }

    fun playOverSound() {
        soundPool!!.play(overSound, 1.0f, 1.0f, 1, 0, 1.0f)
    }
}