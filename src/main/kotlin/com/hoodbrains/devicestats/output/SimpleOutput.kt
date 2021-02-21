package com.hoodbrains.devicestats.output

import com.hoodbrains.devicestats.domain.*

class SimpleOutput : Output {

    override fun write(report: Report): Either<Exception, Unit> =
        either { println(describeReport(report)) }

    private fun describeReport(report: Report)=
        "${report.name} devices=${report.devices} dimensions=${report.dimensionReports.map(::describeDimensionReport)}"

    private fun describeDimensionReport(dimensionReport: DimensionReport<*>)=
        "${dimensionReport.dimensionName} groups=${dimensionReport.dimensionGroups} "
}