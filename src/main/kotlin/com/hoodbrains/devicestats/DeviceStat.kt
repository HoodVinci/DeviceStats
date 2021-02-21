package com.hoodbrains.devicestats

import com.hoodbrains.devicestats.domain.*
import com.hoodbrains.devicestats.input.CsvReader
import com.hoodbrains.devicestats.output.HtmlOutput
import com.hoodbrains.devicestats.output.SimpleOutput

private lateinit var input: Input
private lateinit var output: Output
private lateinit var reportBuilder: ReportBuilder

fun main(args: Array<String>) {

    init(args)
        .chain { input.read() }
        .chain { reportBuilder.build("report", it) }
        .chain { output.write(it) }
        .fold(
            onError = { println("$it") },
            onSuccess = { println("report successfully created") }
        )
}

private fun init(args: Array<String>): Either<Exception, Unit> = either {
    require(args[0].endsWith(".csv")) { "Only csv supported for now" }
    require(args[1].endsWith(".html")) { "Only html supported for now" }
    input = CsvReader(args[0])
    output = HtmlOutput(args[1])
    reportBuilder = ReportBuilder()
}