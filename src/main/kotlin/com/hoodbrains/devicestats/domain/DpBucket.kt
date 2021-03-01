package com.hoodbrains.devicestats.domain

import kotlin.math.abs

enum class DpBucket(val dpi : Int) {
    Ldpi(120),
    Mdpi(160),
    tvdpi(213),
    Hdpi(240),
    Xhdpi(320),
    Xxhdpi(480),
    Xxxhdpi(640)
}


fun findBucket(resolution : Int) : DpBucket =
    DpBucket.values().minByOrNull { abs(resolution - it.dpi) }!!