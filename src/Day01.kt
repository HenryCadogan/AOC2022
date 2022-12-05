fun main() {
    val input = readInput("Day01")

    fun calculateTotals(input: List<String>): List<Int> {
        var currentElfTotal = 0
        val totals = mutableListOf<Int>()
        input.forEach {
            when {
                it != "" -> currentElfTotal += it.toInt()
                else -> {
                    totals.add(currentElfTotal)
                    currentElfTotal = 0
                }
            }
        }
        return totals
    }

    fun part1(input: List<String>): Int {
        return calculateTotals(input).max()
    }

    fun part2(input: List<String>): Int {
        return calculateTotals(input).sortedDescending().take(3).sum()
    }

    check(part1(readInput("Day01_test")) == 24000)

    println(part1(input))
    println(part2(input))
}