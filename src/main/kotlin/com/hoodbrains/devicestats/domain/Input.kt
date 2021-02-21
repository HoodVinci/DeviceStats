package com.hoodbrains.devicestats.domain

import Device

interface Input {
    fun read(): Either<Exception, List<Device>>
}