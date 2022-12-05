fun main() {

    fun part1(input: List<String>): Int {
        return input.sumOf { backpack ->
            val midPoint = backpack.length / 2
            val firstHalf = backpack.substring(0 until midPoint)
            val secondHalf = backpack.substring(midPoint until backpack.length)
            val item = firstHalf.toSet().intersect(secondHalf.toSet())
            item.single().toPriority()
        }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3)
            .sumOf { backpacks ->
                backpacks
                    .map { it.toSet() }
                    .reduce(Set<Char>::intersect)
                    .single()
                    .toPriority()
            }
    }

    val input = readInput("Day03")

    println(part1(input))
    println(part2(input))
}

private fun Char.toPriority(): Int {
    return if (isUpperCase()) {
        code - 38
    } else {
        code - 96
    }
}

