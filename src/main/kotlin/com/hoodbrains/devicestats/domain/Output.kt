package com.hoodbrains.devicestats.domain

interface Output {
    fun write(report: Report): Either<Exception, Unit>
}