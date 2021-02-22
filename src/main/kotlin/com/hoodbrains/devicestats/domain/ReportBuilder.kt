package com.hoodbrains.devicestats.domain

import Device

class ReportBuilder {

    fun build(name: String, devices: List<Device>) =
        either { Report(name, devices, buildDimensionReports(devices)) }

    private fun buildDimensionReports(devices: List<Device>): List<DimensionReport<*>> = listOf(DimensionReport(
        dimensionName = "Type",
        devices = devices
    ) { it.type },
        DimensionReport(
            dimensionName = "Ratio Smartphone",
            devices = devices,
            filter = { it.type == DeviceType.Smartphone }) { findRatio(it.ratio) },
        DimensionReport(
            dimensionName = "Ratio Tablet",
            devices = devices,
            filter = { it.type == DeviceType.Tablet }) { findRatio(it.ratio) },
        DimensionReport(
            dimensionName = "Diagonal Tablet",
            unity = "inch",
            devices = devices,
            filter = { it.type == DeviceType.Tablet }
        ) { it.diagonalInInch },
        DimensionReport(
            dimensionName = "Diagonal Smartphone",
            unity = "inch",
            devices = devices,
            filter = { it.type == DeviceType.Smartphone }
        ) { it.diagonalInInch },
        DimensionReport(
            dimensionName = "Width-ish Smartphone",
            devices = devices,
            filter = { it.type == DeviceType.Smartphone }
        ) { it.viewPort.width.roundLastDigit() },
        DimensionReport(
            dimensionName = "Width-ish Tablet",
            devices = devices,
            filter = { it.type == DeviceType.Tablet }
        ) { it.viewPort.width.roundLastDigit() },
        DimensionReport(
            dimensionName = "Height-ish Tablet",
            devices = devices,
            filter = { it.type == DeviceType.Tablet }
        ) { it.viewPort.height.roundLastDigit() }
    ).filter { dimensionReport -> dimensionReport.dimensionGroups.isNotEmpty() }

    private fun findRatio(ratio: Double): String = when (ratio) {
        2.22 -> "20:9"
        2.17 -> "19.5:9"
        2.14 -> "19.3:9"
        2.11 -> "19:9"
        2.06 -> "18.5:9"
        1.78 -> "16:9"
        1.6 -> "16:10"
        1.33 -> "4:3"
        else -> "$ratio"
    }

    private fun Int.roundLastDigit(): Int = (this / 10) * 10
}