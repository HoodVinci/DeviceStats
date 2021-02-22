package com.hoodbrains.devicestats.domain

import Device
import truncateDecimals

data class Report(
    val name : String,
    val devices: List<Device>,
    val dimensionReports: List<DimensionReport<*>>
)

data class DimensionReport<T>(
    val dimensionName: String,
    val unity : String?= null,
    private val devices: List<Device>,
    val filter : (Device) -> Boolean = {true},
    val dimensionSelector: (Device) -> T
) {

    val dimensionGroups : List<DimensionGroup<T>> = devices
        .filter(filter)
        .groupBy(dimensionSelector)
        .mapValues { entry -> entry.value.sumByDouble { it.percent }.truncateDecimals(1) }
        .map { DimensionGroup(it.key,it.value) }
        .sortedByDescending { it.percent }
        .take(10)

    val totalPercent = dimensionGroups.sumByDouble { it.percent }.truncateDecimals(1)


}

data class DimensionGroup<T>( val dimension : T, val percent : Double)

