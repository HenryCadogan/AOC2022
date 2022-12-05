fun main() {

    fun part1(input: List<String>) = checkRanges(input, IntRange::isInRange)

    fun part2(input: List<String>) = checkRanges(input, IntRange::overLapsWith)

    val input = readInput("Day04")

    println(part1(input))
    println(part2(input))
}

fun checkRanges(input: List<String>, func: IntRange.(IntRange) -> Boolean): Int{
    return input.count{ ranges ->
        val (firstRange, secondRange) = ranges.split(',').map{it.toRange()}
        if (firstRange.length() > secondRange.length()){
            secondRange.func(firstRange)
        } else {
            firstRange.func(secondRange)
        }
    }
}

fun String.toRange(): IntRange{
    val bounds = split('-').map{it.toInt()}
    return bounds.first()..bounds[1]
}

fun IntRange.isInRange(otherRange: IntRange): Boolean = first in otherRange && last in otherRange

fun IntRange.overLapsWith(otherRange: IntRange): Boolean = first in otherRange || last in otherRange

fun IntRange.length() = last - first

