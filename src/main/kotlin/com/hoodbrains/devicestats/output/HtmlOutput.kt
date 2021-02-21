package com.hoodbrains.devicestats.output

import com.hoodbrains.devicestats.domain.*
import kotlinx.html.*
import java.io.FileWriter
import kotlinx.html.stream.appendHTML


class HtmlOutput(private val outputPath: String) : Output {

    override fun write(report: Report): Either<Exception, Unit> = either {
        FileWriter(outputPath).appendHTML().html {
            buildHead()
            body {
                div("container") {
                    h1 { +report.name }
                    h2 { +"devices" }
                    deviceTable(report)
                    dimensionReports(report)
                }
            }
        }.flush()
    }

    private fun DIV.dimensionReports(report: Report) {
        report.dimensionReports.forEach { dimension ->
            h2 { +dimension.dimensionName }
            h3 { +"representation ${dimension.totalPercent}" }
            dimensionReportTable(dimension)
        }
    }

    private fun DIV.dimensionReportTable(dimension: DimensionReport<*>) {
        table("table table-dark table-striped") {
            thead {
                tr {
                    th { +"value" }
                    th { +"percent" }
                }
            }
            tbody {
                dimension.dimensionGroups.forEach {
                    tr {
                        td { +"${it.dimension}" }
                        td { +"${it.percent} %" }

                    }
                }
            }
        }
    }

    private fun DIV.deviceTable(report: Report) = table("table table-dark table-striped") {
        thead {
            tr {
                th { +"name" }
                th { +"viewport" }
                th { +"diagonal" }
                th { +"type" }
            }
        }
        tbody {
            report.devices.forEach {
                tr {
                    td { +it.name }
                    td { +it.viewPort.toString() }
                    td { +"${it.diagonalInInch}\"" }
                    td { +"${it.type}" }
                }
            }
        }
    }

    private fun HTML.buildHead() = head {
        link("https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css") {
            rel = "stylesheet"
            integrity = "sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
            attributes["crossorigin"] = "anonymous"
        }
    }

}