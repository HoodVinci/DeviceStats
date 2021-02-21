import kotlin.math.*

data class Size(val width: Int, val height: Int) {
    val ratio: Double = (height.toDouble() / width).truncateDecimals(2)
    val minSide = min(width, height)
    val diagonal = sqrt(width.toDouble() * width + height * height)

    override fun toString(): String = "${width}x$height"
}

enum class DeviceType {
    Smartphone,
    Tablet
}

fun Double.truncateDecimals(decimals : Int): Double = (this * 10.0.pow(decimals)).roundToInt() /  10.0.pow(decimals)

data class Device(
    val name: String,
    val pixelSize: Size,
    val densityPpi: Int,
    val percent: Double
) {

    val viewPort: Size = pixelSize.pixelToDp(densityPpi)
    val ratio = pixelSize.ratio
    val type = if (viewPort.minSide < 600) DeviceType.Smartphone else DeviceType.Tablet
    val diagonalInInch = (pixelSize.diagonal / densityPpi).truncateDecimals(1)

}

private fun Size.pixelToDp(densityPpi: Int): Size = Size(width.pixelToDp(densityPpi), height.pixelToDp(densityPpi))

private fun Int.pixelToDp(densityPpi: Int): Int = (this * 160L / densityPpi).toInt()
