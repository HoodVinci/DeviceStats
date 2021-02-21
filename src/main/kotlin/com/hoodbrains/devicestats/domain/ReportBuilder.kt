package com.hoodbrains.devicestats.domain

import Device
import Size

class ReportBuilder {

    fun build(name: String, devices: List<Device>) =
        either { Report(name, devices, buildDimensionReports(devices)) }

    private fun buildDimensionReports(devices: List<Device>): List<DimensionReport<*>> {
        val ratioReport = DimensionReport(dimensionName = "Ratio", devices = devices) { findRatio(it.ratio) }
        val diagonalReport =
            DimensionReport(dimensionName = "Diagonal", unity = "inch", devices = devices) { it.diagonalInInch }
        val typeReport = DimensionReport(dimensionName = "Type", devices = devices) { it.type }
        val viewPortReport = DimensionReport(dimensionName = "Viewport", devices = devices) { it.viewPort }
        val widthIshReport = DimensionReport(dimensionName = "Widthish", devices = devices) { viewPortWidthish(it.viewPort) }

        return listOf(
            typeReport,
            ratioReport,
            diagonalReport,
            viewPortReport,
            widthIshReport
        )
    }

    private fun findRatio(ratio: Double): String = when (ratio) {
        2.22 -> "20:9"
        2.17 -> "19.5:9"
        2.14 -> "19.3:9"
        2.11 -> "19:9"
        2.06 -> "18.5:9"
        1.78 -> "16:9"
        else -> "$ratio"
    }

    private fun viewPortWidthish(viewPortSize: Size): String =
        "~ ${(viewPortSize.width / 10) * 10}"
}