import java.io.File
import kotlin.streams.toList

fun main() {

    val input = File("src/resources", "Day06.txt").readText()

    fun part1(input: String): Int {
        return input.lookForUniqueCharacters(4)
    }

    fun part2(input: String): Int {
        return input.lookForUniqueCharacters(14)
    }

    println(part1(input))
    println(part2(input))
}

fun String.lookForUniqueCharacters(windowSize: Int): Int =
    windowed(windowSize).indexOfFirst {it.chars().toList().distinct().count() == windowSize} + windowSize