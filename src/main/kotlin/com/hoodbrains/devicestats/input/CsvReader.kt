package com.hoodbrains.devicestats.input

import Device
import Size
import com.hoodbrains.devicestats.domain.*
import java.io.File

class CsvReader(private val filePath: String) : Input {

    override fun read(): Either<Exception, List<Device>> =
        either { File(filePath).readLines().mapNotNull { parseDevice(it) } }

    private fun parseDevice(line: String) = either {
        val words = line.split(",")
        Device(
            name = words[0],
            pixelSize = parseSize(words[1]),
            densityPpi = words[2].trim().toInt(),
            percent = words[3].trim().toDouble()
        )
    }.fold(
        onError = {
            println("$it")
            null
        },
        onSuccess = { it }
    )
}

private fun parseSize(serializedSize: String): Size {
    val stringSizes = serializedSize.split("x")
    return Size(stringSizes[0].trim().toInt(), stringSizes[1].trim().toInt())
}